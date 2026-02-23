package com.third.gen_office.mis.admin.notice;

import com.third.gen_office.mis.admin.notice.dto.NoticeRequest;
import com.third.gen_office.mis.admin.notice.dto.NoticeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notice", description = "Notice management API")
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 저장")
    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody @Valid NoticeRequest request) {
        Integer id = noticeService.saveNotice(request);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "공지사항 리스트 조회")
    @GetMapping
    public ResponseEntity<List<NoticeResponse.ListDto>> list() {
        return ResponseEntity.ok(noticeService.findAll());
    }

    @Operation(summary = "공지사항 단건 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse.DetailDto> detail(@PathVariable Integer id) {
        return ResponseEntity.ok(noticeService.findDetailById(id));
    }

    @Operation(summary = "공지사항 조회수 증가")
    @PatchMapping("/{id}/read-count")
    public ResponseEntity<Void> incrementReadCount(@PathVariable Integer id) {
        noticeService.updateReadCount(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete notice")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}
