package com.third.gen_office.global.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File", description = "File Upload/Download API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/files") // 공통 기능을 뜻하는 경로
class FileController {

    private final FileService fileService;

    @Operation(summary = "다중 파일 업로드 (성공 시 file_set_id 반환)")
    @PostMapping("/upload")
    public ResponseEntity<Integer> upload(@RequestParam("files") List<MultipartFile> files) {
        Integer fileSetId = fileService.uploadFiles(files);
        return ResponseEntity.ok(fileSetId);
    }

    @Operation(summary = "파일 다운로드")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Integer fileId) {
        return ResponseEntity.ok(fileService.downloadFile(fileId));
    }
}
