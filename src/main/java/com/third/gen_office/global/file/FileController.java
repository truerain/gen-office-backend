package com.third.gen_office.global.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.third.gen_office.domain.file.FileEntity;
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

    @Operation(summary = "Get new file_set_id")
    @GetMapping("/newFileSetId")
    public ResponseEntity<Integer> newSetId() {
        return ResponseEntity.ok(fileService.createFileSetId());
    }

    @Operation(summary = "다중 파일 업로드 (성공 시 file_set_id 반환)")
    @PostMapping("/upload")
    public ResponseEntity<Integer> upload(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "fileSetId", required = false) Integer fileSetId
    ) {
        Integer savedFileSetId = fileService.uploadFiles(files, fileSetId);
        return ResponseEntity.ok(savedFileSetId);
    }

    @Operation(summary = "파일 다운로드")
    @GetMapping("/download/{fileSetId}/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Integer fileSetId, @PathVariable Integer fileId) {
        return ResponseEntity.ok(fileService.downloadFile(fileSetId, fileId));
    }

    @GetMapping("/list/{fileSetId}")
    public ResponseEntity<List<FileEntity>> list(@PathVariable Integer fileSetId) {
        return ResponseEntity.ok(fileService.listFiles(fileSetId));
    }
}
