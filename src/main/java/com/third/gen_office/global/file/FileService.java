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

    private final FileRepository fileRepository; // domain???덊룷吏?좊━ ?ъ슜

    @Value("${file.upload.path}")
    private String uploadPath;

    public Integer uploadFiles(List<MultipartFile> files) {
        // 1. ?덈줈??file_set_id ?앹꽦 (?ㅻТ?먯꽑 ?쒗?ㅻ굹 UUID瑜??レ옄濡?蹂?섑븯???ъ슜)
        // ?ш린??媛꾨떒???꾩옱 ?쒓컙???쒖슜???덉떆瑜??ъ슜?⑸땲??
        Integer fileSetId = createFileSetId();

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                // 2. ?뚯씪紐?以묐났 諛⑹?瑜??꾪븳 ??μ슜 ?대쫫 ?앹꽦
                String originalName = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalName);
                String storedName = UUID.randomUUID().toString() + "." + extension;

                // 3. 臾쇰━???대뜑 ?앹꽦 諛??뚯씪 蹂듭궗
                Path root = Paths.get(uploadPath);
                if (!Files.exists(root)) Files.createDirectories(root);

                Files.copy(file.getInputStream(), root.resolve(storedName), StandardCopyOption.REPLACE_EXISTING);

                // 4. DB ?뺣낫 湲곕줉 (domain/file/FileEntity ?ъ슜)
                FileEntity fileEntity = new FileEntity(
                        fileSetId,
                        originalName,
                        storedName,
                        uploadPath + storedName,
                        extension,
                        file.getSize(),
                        "SYSTEM" // ?ㅼ젣濡??몄쬆???좎?紐?二쇱엯
                );
                fileRepository.save(fileEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException("?뚯씪 ???以??ㅻ쪟媛 諛쒖깮?덉뒿?덈떎.", e);
        }

        return fileSetId;
    }
    @Transactional
    public Integer createFileSetId() {
        FileSetEntity fileSetEntity = fileSetRepository.save(new FileSetEntity());
        return fileSetEntity.getFileSetId();
    }

    public Resource downloadFile(Integer fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("?뚯씪 ?뺣낫瑜?李얠쓣 ???놁뒿?덈떎."));

        try {
            Path file = Paths.get(fileEntity.getFilePath());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("?뚯씪???쎌쓣 ???놁뒿?덈떎.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("?뚯씪 寃쎈줈媛 ?섎せ?섏뿀?듬땲??", e);
        }
    }

    /**
     * ?뚯씪 ?곸꽭 ?뺣낫 議고쉶 (Controller?먯꽌 ?뚯씪紐?異붿텧??
     */
    public FileEntity getFileInfo(Integer fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("?뚯씪??議댁옱?섏? ?딆뒿?덈떎."));
    }}


