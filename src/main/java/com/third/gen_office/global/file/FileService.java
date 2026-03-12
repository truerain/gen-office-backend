package com.third.gen_office.global.file;

import com.third.gen_office.domain.file.FileEntity;
import com.third.gen_office.domain.file.FileRepository;
import com.third.gen_office.domain.file.FileSetEntity;
import com.third.gen_office.domain.file.FileSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final FileSetRepository fileSetRepository;
    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    public Integer uploadFiles(List<MultipartFile> files, Integer fileSetId) {
        Integer resolvedFileSetId = resolveFileSetId(fileSetId);

        try {
            Path root = Paths.get(uploadPath).toAbsolutePath().normalize();
            if (!Files.exists(root)) Files.createDirectories(root);

            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String originalName = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalName);
                String storedName = UUID.randomUUID().toString() + "." + extension;

                Path storedPath = root.resolve(storedName);
                Files.copy(file.getInputStream(), storedPath, StandardCopyOption.REPLACE_EXISTING);

                FileEntity fileEntity = new FileEntity(
                        resolvedFileSetId,
                        originalName,
                        storedName,
                        storedPath.toString(),
                        extension,
                        file.getSize(),
                        "SYSTEM"
                );
                fileEntity.setUseYn("Y");
                fileRepository.save(fileEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException("File upload failed.", e);
        }

        return resolvedFileSetId;
    }

    @Transactional
    public Integer createFileSetId() {
        FileSetEntity fileSetEntity = fileSetRepository.save(new FileSetEntity());
        return fileSetEntity.getFileSetId();
    }

    public Resource downloadFile(Integer fileSetId, Integer fileId) {
        FileEntity fileEntity = fileRepository.findByFileSetIdAndFileIdAndUseYn(fileSetId, fileId, "Y")
                .orElseThrow(() -> new RuntimeException("File not found."));

        try {
            Path file = Paths.get(fileEntity.getFilePath());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File is not readable.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid file path.", e);
        }
    }

    public List<FileEntity> listFiles(Integer fileSetId) {
        return fileRepository.findByFileSetIdAndUseYn(fileSetId, "Y");
    }

    @Transactional
    public void deleteFile(Integer fileSetId, Integer fileId) {
        FileEntity fileEntity = fileRepository.findByFileSetIdAndFileIdAndUseYn(fileSetId, fileId, "Y")
                .orElseThrow(() -> new RuntimeException("File not found."));
        fileEntity.setUseYn("N");
        fileRepository.save(fileEntity);
    }

    public FileEntity getFileInfo(Integer fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found."));
    }

    private Integer resolveFileSetId(Integer fileSetId) {
        if (fileSetId == null) {
            return createFileSetId();
        }
        if (!fileSetRepository.existsById(fileSetId)) {
            throw new RuntimeException("File set not found.");
        }
        return fileSetId;
    }
}
