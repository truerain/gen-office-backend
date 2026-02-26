package com.third.gen_office.mis.admin.lookup;

import com.third.gen_office.domain.lookup.LookupMasterEntity;
import com.third.gen_office.domain.lookup.LookupMasterRepository;
import com.third.gen_office.domain.lookup.LookupDetailEntity;
import com.third.gen_office.domain.lookup.LookupDetailId;
import com.third.gen_office.domain.lookup.LookupDetailRepository;
import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.global.error.ConflictException;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterUpdateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailUpdateRequest;
import com.third.gen_office.mis.common.util.LastUpdatedByResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class LookupService {
    private final LookupMasterRepository lookupMasterRepository;
    private final LookupDetailRepository lookupDetailRepository;
    private final LastUpdatedByResolver lastUpdatedByResolver;

    public LookupService(
        LookupMasterRepository lookupMasterRepository,
        LookupDetailRepository lookupDetailRepository,
        LastUpdatedByResolver lastUpdatedByResolver
    ) {
        this.lookupMasterRepository = lookupMasterRepository;
        this.lookupDetailRepository = lookupDetailRepository;
        this.lastUpdatedByResolver = lastUpdatedByResolver;
    }

    @Transactional(readOnly = true)
    public List<LookupMasterResponse> listMasters(
        String lkupClssCd,
        String lkupClssName,
        String useYn,
        String q,
        String sort
    ) {
        validateUseYn(useYn);

        Specification<LookupMasterEntity> spec = buildMasterSpecification(lkupClssCd, lkupClssName, useYn, q);
        Sort sortSpec = toMasterSort(sort);
        List<LookupMasterEntity> entities = lookupMasterRepository.findAll(spec, sortSpec);
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);
        return entities.stream()
                .map(entity -> toMasterResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())))
                .toList();
    }

    @Transactional(readOnly = true)
    public LookupMasterResponse getMaster(String lkupClssCd) {
        validateClassKey(lkupClssCd);
        LookupMasterEntity entity = lookupMasterRepository.findById(lkupClssCd)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));
        String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(entity.getLastUpdatedBy());
        return toMasterResponse(entity, updatedByName);
    }

    @Transactional
    public LookupMasterResponse createMaster(LookupMasterCreateRequest request) {
        validateCreateClassRequest(request);
        String key = request.lkupClssCd().trim();
        if (lookupMasterRepository.existsById(key)) {
            throw new ConflictException("lookup.duplicate");
        }

        LookupMasterEntity entity = LookupMasterEntity.builder()
            .lkupClssCd(key)
            .lkupClssName(request.lkupClssName())
            .lkupClssDesc(request.lkupClssDesc())
            .useYn(defaultUseYn(request.useYn()))
            .build();
        entity.setAttribute1(request.attribute1());
        entity.setAttribute2(request.attribute2());
        entity.setAttribute3(request.attribute3());
        entity.setAttribute4(request.attribute4());
        entity.setAttribute5(request.attribute5());
        entity.setAttribute6(request.attribute6());
        entity.setAttribute7(request.attribute7());
        entity.setAttribute8(request.attribute8());
        entity.setAttribute9(request.attribute9());
        entity.setAttribute10(request.attribute10());
        lookupMasterRepository.save(entity);
        return getMaster(key);
    }

    @Transactional
    public LookupMasterResponse updateMaster(String lkupClssCd, LookupMasterUpdateRequest request) {
        validateClassKey(lkupClssCd);
        validateUpdateClassRequest(request);
        LookupMasterEntity existing = lookupMasterRepository.findById(lkupClssCd)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));

        existing.setLkupClssName(request.lkupClssName());
        existing.setLkupClssDesc(request.lkupClssDesc());
        existing.setUseYn(normalizeUseYn(request.useYn()));
        existing.setAttribute1(request.attribute1());
        existing.setAttribute2(request.attribute2());
        existing.setAttribute3(request.attribute3());
        existing.setAttribute4(request.attribute4());
        existing.setAttribute5(request.attribute5());
        existing.setAttribute6(request.attribute6());
        existing.setAttribute7(request.attribute7());
        existing.setAttribute8(request.attribute8());
        existing.setAttribute9(request.attribute9());
        existing.setAttribute10(request.attribute10());
        lookupMasterRepository.save(existing);
        return getMaster(lkupClssCd);
    }

    @Transactional(readOnly = true)
    public List<LookupDetailResponse> listDetails(
        String lkupClssCd,
        String lkupCd,
        String lkupName,
        String useYn,
        String q,
        String sort
    ) {
        validateClassKey(lkupClssCd);
        validateUseYn(useYn);
        ensureClassExists(lkupClssCd);

        Specification<LookupDetailEntity> spec =
            buildDetailSpecification(lkupClssCd, lkupCd, lkupName, useYn, q);
        Sort sortSpec = toDetailSort(sort);

        List<LookupDetailEntity> entities = lookupDetailRepository.findAll(spec, sortSpec);
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);
        return entities.stream()
                .map(entity -> toDetailResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())))
                .toList();
    }

    @Transactional(readOnly = true)
    public LookupDetailResponse getDetail(String lkupClssCd, String lkupCd) {
        validateClassKey(lkupClssCd);
        validateDetailKey(lkupCd);
        LookupDetailId id = new LookupDetailId(lkupClssCd, lkupCd);
        LookupDetailEntity entity = lookupDetailRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));
        String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(entity.getLastUpdatedBy());
        return toDetailResponse(entity, updatedByName);
    }

    @Transactional
    public LookupDetailResponse createDetail(String lkupClssCd, LookupDetailCreateRequest request) {
        validateClassKey(lkupClssCd);
        validateCreateDetailRequest(request);
        ensureClassExists(lkupClssCd);

        String lkupCd = request.lkupCd().trim();
        LookupDetailId id = new LookupDetailId(lkupClssCd, lkupCd);
        if (lookupDetailRepository.existsById(id)) {
            throw new ConflictException("lookup.duplicate");
        }

        LookupDetailEntity entity = LookupDetailEntity.builder()
            .lkupClssCd(lkupClssCd)
            .lkupCd(lkupCd)
            .lkupName(request.lkupName())
            .lkupNameEng(request.lkupNameEng())
            .sortOrder(request.sortOrder())
            .useYn(defaultUseYn(request.useYn()))
            .build();
        entity.setAttribute1(request.attribute1());
        entity.setAttribute2(request.attribute2());
        entity.setAttribute3(request.attribute3());
        entity.setAttribute4(request.attribute4());
        entity.setAttribute5(request.attribute5());
        entity.setAttribute6(request.attribute6());
        entity.setAttribute7(request.attribute7());
        entity.setAttribute8(request.attribute8());
        entity.setAttribute9(request.attribute9());
        entity.setAttribute10(request.attribute10());
        lookupDetailRepository.save(entity);
        return getDetail(lkupClssCd, lkupCd);
    }

    @Transactional
    public LookupDetailResponse updateDetail(
        String lkupClssCd,
        String lkupCd,
        LookupDetailUpdateRequest request
    ) {
        validateClassKey(lkupClssCd);
        validateDetailKey(lkupCd);
        validateUpdateDetailRequest(request);

        LookupDetailId id = new LookupDetailId(lkupClssCd, lkupCd);
        LookupDetailEntity existing = lookupDetailRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));

        existing.setLkupName(request.lkupName());
        existing.setLkupNameEng(request.lkupNameEng());
        existing.setSortOrder(request.sortOrder());
        existing.setUseYn(request.useYn());

        existing.setAttribute1(request.attribute1());
        existing.setAttribute2(request.attribute2());
        existing.setAttribute3(request.attribute3());
        existing.setAttribute4(request.attribute4());
        existing.setAttribute5(request.attribute5());
        existing.setAttribute6(request.attribute6());
        existing.setAttribute7(request.attribute7());
        existing.setAttribute8(request.attribute8());
        existing.setAttribute9(request.attribute9());
        existing.setAttribute10(request.attribute10());
        lookupDetailRepository.save(existing);

        return getDetail(lkupClssCd, lkupCd);
    }

    private Specification<LookupMasterEntity> buildMasterSpecification(
        String lkupClssCd,
        String lkupClssName,
        String useYn,
        String q
    ) {
        Specification<LookupMasterEntity> spec = Specification.where(null);
        if (StringUtils.hasText(lkupClssCd)) {
            String like = "%" + lkupClssCd + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupClssCd"), like));
        }
        if (StringUtils.hasText(lkupClssName)) {
            String like = "%" + lkupClssName + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupClssName"), like));
        }
        if (StringUtils.hasText(useYn)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("useYn"), useYn));
        }
        if (StringUtils.hasText(q)) {
            String like = "%" + q + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                cb.like(root.get("lkupClssName"), like),
                cb.like(root.get("lkupClssDesc"), like)
            ));
        }
        return spec;
    }

    private Specification<LookupDetailEntity> buildDetailSpecification(
        String lkupClssCd,
        String lkupCd,
        String lkupName,
        String useYn,
        String q
    ) {
        Specification<LookupDetailEntity> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("lkupClssCd"), lkupClssCd));
        if (StringUtils.hasText(lkupCd)) {
            String like = "%" + lkupCd + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupCd"), like));
        }
        if (StringUtils.hasText(lkupName)) {
            String like = "%" + lkupName + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupName"), like));
        }
        if (StringUtils.hasText(useYn)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("useYn"), useYn));
        }
        if (StringUtils.hasText(q)) {
            String like = "%" + q + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                cb.like(root.get("lkupName"), like),
                cb.like(root.get("lkupNameEng"), like)
            ));
        }
        return spec;
    }

    private Sort toMasterSort(String sort) {
        Map<String, String> allowed = new HashMap<>();
        allowed.put("lkup_clss_cd", "lkupClssCd");
        allowed.put("lkupClssCd", "lkupClssCd");
        allowed.put("lkup_clss_name", "lkupClssName");
        allowed.put("lkupClssName", "lkupClssName");
        allowed.put("creation_date", "creationDate");
        allowed.put("createdAt", "creationDate");
        allowed.put("last_updated_date", "lastUpdatedDate");
        allowed.put("updatedAt", "lastUpdatedDate");

        if (!StringUtils.hasText(sort)) {
            return Sort.by("lkupClssCd").ascending();
        }
        List<Sort.Order> orders = parseSort(sort, allowed);
        if (orders.isEmpty()) {
            return Sort.by("lkupClssCd").ascending();
        }
        return Sort.by(orders);
    }

    private Sort toDetailSort(String sort) {
        Map<String, String> allowed = new HashMap<>();
        allowed.put("lkup_cd", "lkupCd");
        allowed.put("lkupCd", "lkupCd");
        allowed.put("lkup_name", "lkupName");
        allowed.put("lkupName", "lkupName");
        allowed.put("lkup_name_eng", "lkupNameEng");
        allowed.put("lkupNameEng", "lkupNameEng");
        allowed.put("sort_order", "sortOrder");
        allowed.put("sortOrder", "sortOrder");
        allowed.put("creation_date", "creationDate");
        allowed.put("createdAt", "creationDate");
        allowed.put("last_updated_date", "lastUpdatedDate");
        allowed.put("updatedAt", "lastUpdatedDate");

        if (!StringUtils.hasText(sort)) {
            return Sort.by("sortOrder").ascending().and(Sort.by("lkupCd").ascending());
        }
        List<Sort.Order> orders = parseSort(sort, allowed);
        if (orders.isEmpty()) {
            return Sort.by("sortOrder").ascending().and(Sort.by("lkupCd").ascending());
        }
        return Sort.by(orders);
    }

    private List<Sort.Order> parseSort(String sort, Map<String, String> allowed) {
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
        return orders;
    }

    private LookupMasterResponse toMasterResponse(LookupMasterEntity entity, String updatedByName) {
        return new LookupMasterResponse(
            entity.getLkupClssCd(),
            entity.getLkupClssName(),
            entity.getLkupClssDesc(),
            entity.getUseYn(),
            entity.getAttribute1(),
            entity.getAttribute2(),
            entity.getAttribute3(),
            entity.getAttribute4(),
            entity.getAttribute5(),
            entity.getAttribute6(),
            entity.getAttribute7(),
            entity.getAttribute8(),
            entity.getAttribute9(),
            entity.getAttribute10(),
            entity.getLastUpdatedBy(),
            updatedByName,
            entity.getLastUpdatedDate()
        );
    }

    private LookupDetailResponse toDetailResponse(LookupDetailEntity entity, String updatedByName) {
        return new LookupDetailResponse(
            entity.getLkupClssCd(),
            entity.getLkupCd(),
            entity.getLkupName(),
            entity.getLkupNameEng(),
            entity.getSortOrder(),
            entity.getUseYn(),
            entity.getAttribute1(),
            entity.getAttribute2(),
            entity.getAttribute3(),
            entity.getAttribute4(),
            entity.getAttribute5(),
            entity.getAttribute6(),
            entity.getAttribute7(),
            entity.getAttribute8(),
            entity.getAttribute9(),
            entity.getAttribute10(),
            entity.getLastUpdatedBy(),
            updatedByName,
            entity.getLastUpdatedDate()
        );
    }

    private void validateCreateClassRequest(LookupMasterCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateClassKey(request.lkupClssCd());
        validateUseYn(request.useYn());
    }

    private void validateUpdateClassRequest(LookupMasterUpdateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateUseYn(request.useYn());
    }

    private void validateCreateDetailRequest(LookupDetailCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateDetailKey(request.lkupCd());
        validateUseYn(request.useYn());
        validateSortOrder(request.sortOrder());
    }

    private void validateUpdateDetailRequest(LookupDetailUpdateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateUseYn(request.useYn());
        validateSortOrder(request.sortOrder());
    }

    private void validateClassKey(String lkupClssCd) {
        if (!StringUtils.hasText(lkupClssCd) || containsWhitespace(lkupClssCd)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private void validateDetailKey(String lkupCd) {
        if (!StringUtils.hasText(lkupCd) || containsWhitespace(lkupCd)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private void validateUseYn(String useYn) {
        if (!StringUtils.hasText(useYn)) {
            return;
        }
        if (!"Y".equalsIgnoreCase(useYn) && !"N".equalsIgnoreCase(useYn)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private void validateSortOrder(Integer sortOrder) {
        if (sortOrder != null && sortOrder < 0) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private String defaultUseYn(String useYn) {
        return StringUtils.hasText(useYn) ? normalizeUseYn(useYn) : "Y";
    }

    private String normalizeUseYn(String useYn) {
        return useYn == null ? null : useYn.trim().toUpperCase();
    }

    private int normalizeSize(int size, int defaultSize) {
        if (size <= 0) {
            return defaultSize;
        }
        return Math.min(size, 200);
    }

    private boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void ensureClassExists(String lkupClssCd) {
        if (!lookupMasterRepository.existsById(lkupClssCd)) {
            throw new NotFoundException("lookup.not_found");
        }
    }

}
