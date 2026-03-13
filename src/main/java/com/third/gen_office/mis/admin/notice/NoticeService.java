package com.third.gen_office.mis.admin.notice;

import com.third.gen_office.domain.file.FileEntity;
import com.third.gen_office.domain.file.FileRepository;
import com.third.gen_office.domain.notice.NoticeEntity;
import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.mis.admin.notice.dto.BulkNoticeRequest;
import com.third.gen_office.mis.admin.notice.dto.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.third.gen_office.mis.admin.notice.dto.NoticeRequest;
import com.third.gen_office.domain.notice.NoticeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileRepository fileRepository;

    private String getFilenames(Integer fileSetId) {
        if (fileSetId == null) {
            return "";
        }
        List<FileEntity> files = fileRepository.findByFileSetId(fileSetId);
        if (files.isEmpty()) {
            return "";
        }
        if (files.size() == 1) {
            return files.get(0).getOriginalFileName();
        }
        return files.get(0).getOriginalFileName() + " 외 " + (files.size() - 1) + "개";
    }

    /**
     * 공지사항 등록
     */
    @Transactional
    public Integer createNotice(NoticeRequest request) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .fileSetId(request.getFileSetId())
                .popupYn(request.getPopupYn() != null ? request.getPopupYn() : "N")
                .useYn(request.getUseYn() != null ? request.getUseYn() : "Y")
                .dispStartDate(request.getDispStartDate())
                .dispEndDate(request.getDispEndDate())
                .readCount(0)
                .build();

        return noticeRepository.save(noticeEntity).getNoticeId();
    }

    @Transactional
    public Integer saveNotice(NoticeRequest request) {
        Integer noticeId = resolveNoticeId(request);
        if (noticeId != null && noticeId > 0) {
            return updateNotice(noticeId, request);
        }
        return createNotice(request);
    }

    private Integer resolveNoticeId(NoticeRequest request) {
        if (request.getNoticeId() != null) {
            return request.getNoticeId();
        }
        return request.getRequestId();
    }

    /**
     * 전체 목록 조회
     */
    public List<NoticeResponse.ListDto> findAll() {

        return noticeRepository.findAll().stream()
                .map(this::toListResponse)
                .toList();
    }

    /**
     * 상세 조회
     */
    public NoticeResponse.DetailDto findDetailById(Integer id) {
        NoticeEntity noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        return toDetailResponse(noticeEntity);
    }

    @Transactional
    public Integer updateNotice(Integer id, NoticeRequest request) {
        NoticeEntity noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        noticeEntity.setTitle(request.getTitle());
        noticeEntity.setContent(request.getContent());

        if (request.getFileSetId() != null) {
            noticeEntity.setFileSetId(request.getFileSetId());
        }

        if (request.getDispStartDate() != null) {
            noticeEntity.setDispStartDate(request.getDispStartDate());
        }
        if (request.getDispEndDate() != null) {
            noticeEntity.setDispEndDate(request.getDispEndDate());
        }
        if (request.getPopupYn() != null) {
            noticeEntity.setPopupYn(request.getPopupYn());
        }
        if (request.getUseYn() != null) {
            noticeEntity.setUseYn(request.getUseYn());
        }

        return noticeRepository.save(noticeEntity).getNoticeId();
    }

    @Transactional
    public void updateReadCount(Integer id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 공지사항 ID입니다.");
        }
        noticeRepository.incrementReadCount(id);
    }

    @Transactional
    public void deleteNotice(Integer id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("공지사항이 없습니다.");
        }
        noticeRepository.deleteById(id);
    }
    @Transactional
    public void bulkCommit(BulkNoticeRequest request) {
        if (request == null) {
            throw new BadRequestException("notice.invalid_request");
        }

        List<NoticeRequest> creates = request.creates() == null ? List.of() : request.creates();
        for (NoticeRequest item : creates) {
            if (item == null) {
                throw new BadRequestException("notice.invalid_request");
            }
            createNotice(item);
        }

        List<NoticeRequest> updates = request.updates() == null ? List.of() : request.updates();
        for (NoticeRequest item : updates) {
            if (item == null) {
                throw new BadRequestException("notice.invalid_request");
            }
            Integer noticeId = resolveNoticeId(item);
            if (noticeId == null || noticeId <= 0) {
                throw new BadRequestException("notice.invalid_request");
            }
            updateNotice(noticeId, item);
        }

        List<NoticeRequest> deletes = request.deletes() == null ? List.of() : request.deletes();
        for (NoticeRequest item : deletes) {
            if (item == null) {
                throw new BadRequestException("notice.invalid_request");
            }
            Integer noticeId = resolveNoticeId(item);
            if (noticeId == null || noticeId <= 0) {
                throw new BadRequestException("notice.invalid_request");
            }
            deleteNotice(noticeId);
        }
    }

    private NoticeResponse.ListDto toListResponse(NoticeEntity entity) {
        return new NoticeResponse.ListDto(
                entity.getNoticeId(),
                entity.getTitle(),
                entity.getFileSetId(),
                getFilenames(entity.getFileSetId()),
                entity.getDispStartDate(),
                entity.getDispEndDate(),
                entity.getPopupYn(),
                entity.getUseYn(),
                entity.getReadCount(),
                entity.getLastUpdatedBy(),
                entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }

    private NoticeResponse.DetailDto toDetailResponse(NoticeEntity entity) {
        return new NoticeResponse.DetailDto(
                entity.getNoticeId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getFileSetId(),
                entity.getDispStartDate(),
                entity.getDispEndDate(),
                entity.getPopupYn(),
                entity.getUseYn(),
                entity.getReadCount(),
                entity.getLastUpdatedBy(),
                entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }
}


