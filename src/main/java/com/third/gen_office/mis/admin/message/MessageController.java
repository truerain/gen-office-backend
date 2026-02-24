package com.third.gen_office.mis.admin.message;

import com.third.gen_office.mis.admin.message.dto.BulkMessageRequest;
import com.third.gen_office.mis.admin.message.dto.BulkMessageResponse;
import com.third.gen_office.mis.admin.message.dto.MessageCreateRequest;
import com.third.gen_office.mis.admin.message.dto.MessageListResponse;
import com.third.gen_office.mis.admin.message.dto.MessageResponse;
import com.third.gen_office.mis.admin.message.dto.MessageUpdateRequest;
import com.third.gen_office.mis.admin.message.dto.MissingMessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Message", description = "I18n message admin API")
@RestController
@RequestMapping("/api/mis/admin/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @Operation(summary = "List messages")
    public MessageListResponse list(
        @RequestParam(value = "namespace", required = false) String namespace,
        @RequestParam(value = "langCd", required = false) String langCd,
        @RequestParam(value = "messageCd", required = false) String messageCd,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "20") int size,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return messageService.list(namespace, langCd, messageCd, q, page, size, sort);
    }

    @GetMapping("/{namespace}/{messageCd}/{langCd}")
    @Operation(summary = "Get message")
    public MessageResponse get(
        @Parameter(description = "namespace") @PathVariable String namespace,
        @Parameter(description = "message code") @PathVariable String messageCd,
        @Parameter(description = "language code") @PathVariable String langCd
    ) {
        return messageService.get(namespace, messageCd, langCd);
    }

    @PostMapping
    @Operation(summary = "Create message")
    public ResponseEntity<MessageResponse> create(@RequestBody MessageCreateRequest request) {
        MessageResponse response = messageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{namespace}/{messageCd}/{langCd}")
    @Operation(summary = "Update message")
    public MessageResponse update(
        @Parameter(description = "namespace") @PathVariable String namespace,
        @Parameter(description = "message code") @PathVariable String messageCd,
        @Parameter(description = "language code") @PathVariable String langCd,
        @RequestBody MessageUpdateRequest request
    ) {
        return messageService.update(namespace, messageCd, langCd, request);
    }

    @DeleteMapping("/{namespace}/{messageCd}/{langCd}")
    @Operation(summary = "Delete message")
    public ResponseEntity<Void> delete(
        @Parameter(description = "namespace") @PathVariable String namespace,
        @Parameter(description = "message code") @PathVariable String messageCd,
        @Parameter(description = "language code") @PathVariable String langCd
    ) {
        messageService.delete(namespace, messageCd, langCd);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk upsert messages")
    public BulkMessageResponse bulkUpsert(@RequestBody BulkMessageRequest request) {
        return messageService.bulkUpsert(request);
    }

    @GetMapping("/missing")
    @Operation(summary = "List missing translations")
    public MissingMessageResponse missing(
        @RequestParam(value = "namespace", required = false) String namespace,
        @RequestParam(value = "baseLang", required = false, defaultValue = "ko") String baseLang,
        @RequestParam(value = "targetLang") String targetLang
    ) {
        return messageService.missing(namespace, baseLang, targetLang);
    }
}
