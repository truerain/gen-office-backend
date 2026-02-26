package com.third.gen_office.mis.admin.message;

import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.global.error.ConflictException;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.domain.message.MessageEntity;
import com.third.gen_office.domain.message.MessageId;
import com.third.gen_office.domain.message.MessageRepository;
import com.third.gen_office.global.i18n.DbMessageSource;
import com.third.gen_office.mis.admin.message.dto.BulkMessageRequest;
import com.third.gen_office.mis.admin.message.dto.BulkMessageResponse;
import com.third.gen_office.mis.admin.message.dto.MessageCreateRequest;
import com.third.gen_office.mis.admin.message.dto.MessageListResponse;
import com.third.gen_office.mis.admin.message.dto.MessageResponse;
import com.third.gen_office.mis.admin.message.dto.MessageUpdateRequest;
import com.third.gen_office.mis.admin.message.dto.MissingMessageItem;
import com.third.gen_office.mis.admin.message.dto.MissingMessageResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final DbMessageSource messageSource;

    public MessageService(MessageRepository messageRepository, DbMessageSource messageSource) {
        this.messageRepository = messageRepository;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public MessageListResponse list(
        String namespace,
        String langCd,
        String messageCd,
        String q,
        int page,
        int size,
        String sort
    ) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = normalizeSize(size);
        Specification<MessageEntity> spec = buildSpecification(namespace, langCd, messageCd, q);
        Sort sortSpec = toSort(sort);
        Page<MessageEntity> result = messageRepository.findAll(
            spec,
            PageRequest.of(normalizedPage, normalizedSize, sortSpec)
        );

        List<MessageResponse> items = result.getContent().stream()
            .map(this::toResponse)
            .toList();

        return new MessageListResponse(items, normalizedPage, normalizedSize, result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public MessageResponse get(String namespace, String messageCd, String langCd) {
        validateKey(namespace, messageCd, langCd);
        MessageId id = new MessageId(messageCd, langCd, namespace);
        MessageEntity entity = messageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("message.not_found"));
        return toResponse(entity);
    }

    @Transactional
    public MessageResponse create(MessageCreateRequest request) {
        validateCreateRequest(request);
        MessageId id = new MessageId(request.messageCd(), request.langCd(), request.namespace());
        if (messageRepository.existsById(id)) {
            throw new ConflictException("message.duplicate");
        }
        MessageEntity entity = MessageEntity.builder()
            .messageCd(request.messageCd())
            .langCd(request.langCd())
            .namespace(request.namespace())
            .messageTxt(request.messageTxt())
            .build();
        messageRepository.save(entity);
        messageSource.evict(request.namespace(), request.messageCd(), request.langCd());
        return get(request.namespace(), request.messageCd(), request.langCd());
    }

    @Transactional
    public MessageResponse update(String namespace, String messageCd, String langCd, MessageUpdateRequest request) {
        validateKey(namespace, messageCd, langCd);
        validateUpdateRequest(request);
        MessageId id = new MessageId(messageCd, langCd, namespace);
        if (!messageRepository.existsById(id)) {
            throw new NotFoundException("message.not_found");
        }
        messageRepository.updateMessageText(namespace, messageCd, langCd, request.messageTxt());
        messageSource.evict(namespace, messageCd, langCd);
        return get(namespace, messageCd, langCd);
    }

    @Transactional
    public void delete(String namespace, String messageCd, String langCd) {
        validateKey(namespace, messageCd, langCd);
        MessageId id = new MessageId(messageCd, langCd, namespace);
        if (!messageRepository.existsById(id)) {
            throw new NotFoundException("message.not_found");
        }
        messageRepository.deleteById(id);
        messageSource.evict(namespace, messageCd, langCd);
    }

    @Transactional
    public BulkMessageResponse bulkUpsert(BulkMessageRequest request) {
        if (request == null || request.items() == null || request.items().isEmpty()) {
            throw new BadRequestException("message.invalid_request");
        }
        int inserted = 0;
        int updated = 0;
        int skipped = 0;

        for (MessageCreateRequest item : request.items()) {
            validateCreateRequest(item);
            MessageId id = new MessageId(item.messageCd(), item.langCd(), item.namespace());
            MessageEntity entity = messageRepository.findById(id).orElse(null);
            if (entity == null) {
                MessageEntity created = MessageEntity.builder()
                    .messageCd(item.messageCd())
                    .langCd(item.langCd())
                    .namespace(item.namespace())
                    .messageTxt(item.messageTxt())
                    .build();
                messageRepository.save(created);
                inserted++;
                messageSource.evict(item.namespace(), item.messageCd(), item.langCd());
                continue;
            }
            if (item.messageTxt().equals(entity.getMessageTxt())) {
                skipped++;
                continue;
            }
            messageRepository.updateMessageText(
                item.namespace(),
                item.messageCd(),
                item.langCd(),
                item.messageTxt()
            );
            updated++;
            messageSource.evict(item.namespace(), item.messageCd(), item.langCd());
        }

        return new BulkMessageResponse(inserted, updated, skipped);
    }

    @Transactional(readOnly = true)
    public MissingMessageResponse missing(String namespace, String baseLang, String targetLang) {
        if (!StringUtils.hasText(targetLang)) {
            throw new BadRequestException("message.invalid_request");
        }
        validateLangCd(baseLang);
        validateLangCd(targetLang);

        List<MissingMessageItem> items = messageRepository.findMissingMessages(namespace, baseLang, targetLang);
        return new MissingMessageResponse(items);
    }

    private Specification<MessageEntity> buildSpecification(
        String namespace,
        String langCd,
        String messageCd,
        String q
    ) {
        Specification<MessageEntity> spec = Specification.where(null);
        if (StringUtils.hasText(namespace)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("namespace"), namespace));
        }
        if (StringUtils.hasText(langCd)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("langCd"), langCd));
        }
        if (StringUtils.hasText(messageCd)) {
            String like = "%" + messageCd + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("messageCd"), like));
        }
        if (StringUtils.hasText(q)) {
            String like = "%" + q + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("messageTxt"), like));
        }
        return spec;
    }

    private Sort toSort(String sort) {
        Map<String, String> allowed = new HashMap<>();
        allowed.put("namespace", "namespace");
        allowed.put("message_cd", "messageCd");
        allowed.put("messageCd", "messageCd");
        allowed.put("lang_cd", "langCd");
        allowed.put("langCd", "langCd");
        allowed.put("creation_date", "creationDate");
        allowed.put("createdAt", "creationDate");
        allowed.put("last_updated_date", "lastUpdatedDate");
        allowed.put("updatedAt", "lastUpdatedDate");

        if (!StringUtils.hasText(sort)) {
            return Sort.by("namespace").ascending()
                .and(Sort.by("messageCd").ascending())
                .and(Sort.by("langCd").ascending());
        }

        List<Sort.Order> orders = new ArrayList<>();
        String[] tokens = sort.split(",");
        for (String raw : tokens) {
            String token = raw.trim();
            if (token.isEmpty()) {
                continue;
            }
            String[] parts = token.split("\\s+");
            String property = allowed.get(parts[0]);
            if (property == null) {
                continue;
            }
            Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, property));
        }
        if (orders.isEmpty()) {
            return Sort.by("namespace").ascending()
                .and(Sort.by("messageCd").ascending())
                .and(Sort.by("langCd").ascending());
        }
        return Sort.by(orders);
    }

    private MessageResponse toResponse(MessageEntity entity) {
        return new MessageResponse(
            entity.getNamespace(),
            entity.getMessageCd(),
            entity.getLangCd(),
            entity.getMessageTxt(),
            entity.getCreationDate() == null ? null : entity.getCreationDate().toString(),
            entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }

    private int normalizeSize(int size) {
        if (size <= 0) {
            return 20;
        }
        return Math.min(size, 200);
    }

    private void validateCreateRequest(MessageCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("message.invalid_request");
        }
        validateKey(request.namespace(), request.messageCd(), request.langCd());
        if (!StringUtils.hasText(request.messageTxt())) {
            throw new BadRequestException("message.invalid_request");
        }
    }

    private void validateUpdateRequest(MessageUpdateRequest request) {
        if (request == null || !StringUtils.hasText(request.messageTxt())) {
            throw new BadRequestException("message.invalid_request");
        }
    }

    private void validateKey(String namespace, String messageCd, String langCd) {
        if (!StringUtils.hasText(namespace) || containsWhitespace(namespace)) {
            throw new BadRequestException("message.invalid_request");
        }
        if (!StringUtils.hasText(messageCd) || containsWhitespace(messageCd)) {
            throw new BadRequestException("message.invalid_request");
        }
        validateLangCd(langCd);
    }

    private void validateLangCd(String langCd) {
        if (!StringUtils.hasText(langCd)) {
            throw new BadRequestException("message.invalid_request");
        }
        Locale locale = Locale.forLanguageTag(langCd.trim());
        if (!StringUtils.hasText(locale.getLanguage()) || "und".equalsIgnoreCase(locale.getLanguage())) {
            throw new BadRequestException("message.invalid_request");
        }
    }

    private boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
