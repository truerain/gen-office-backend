package com.third.gen_office.global.file;

import com.third.gen_office.domain.file.FileEntity;
import com.third.gen_office.domain.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class FileService {

    private final FileRepository fileRepository; // domain의 레포지토리 사용

    @Value("${file.upload.path}")
    private String uploadPath;

    public Integer uploadFiles(List<MultipartFile> files) {
        // 1. 새로운 file_set_id 생성 (실무에선 시퀀스나 UUID를 숫자로 변환하여 사용)
        // 여기선 간단히 현재 시간을 활용한 예시를 사용합니다.
        Integer fileSetId = (int) (System.currentTimeMillis() / 1000);

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                // 2. 파일명 중복 방지를 위한 저장용 이름 생성
                String originalName = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalName);
                String storedName = UUID.randomUUID().toString() + "." + extension;

                // 3. 물리적 폴더 생성 및 파일 복사
                Path root = Paths.get(uploadPath);
                if (!Files.exists(root)) Files.createDirectories(root);

                Files.copy(file.getInputStream(), root.resolve(storedName), StandardCopyOption.REPLACE_EXISTING);

                // 4. DB 정보 기록 (domain/file/FileEntity 사용)
                FileEntity fileEntity = new FileEntity(
                        fileSetId,
                        originalName,
                        storedName,
                        uploadPath + storedName,
                        extension,
                        file.getSize(),
                        "SYSTEM" // 실제론 인증된 유저명 주입
                );
                fileRepository.save(fileEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }

        return fileSetId;
    }

    public Resource downloadFile(Integer fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일 정보를 찾을 수 없습니다."));

        try {
            Path file = Paths.get(fileEntity.getFilePath());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일 경로가 잘못되었습니다.", e);
        }
    }

    /**
     * 파일 상세 정보 조회 (Controller에서 파일명 추출용)
     */
    public FileEntity getFileInfo(Integer fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일이 존재하지 않습니다."));
    }}
