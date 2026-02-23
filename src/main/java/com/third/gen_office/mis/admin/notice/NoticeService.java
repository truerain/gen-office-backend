package com.third.gen_office.mis.admin.notice;

import com.third.gen_office.domain.notice.NoticeEntity;
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

    /**
     * 공지사항 등록
     */
    @Transactional
    public Integer createNotice(NoticeRequest request) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
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
                .map(NoticeResponse.ListDto::from)
                .toList();
    }

    /**
     * 상세 조회
     */
    public NoticeResponse.DetailDto findDetailById(Integer id) {
        NoticeEntity noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        return NoticeResponse.DetailDto.from(noticeEntity);
    }

    @Transactional
    public Integer updateNotice(Integer id, NoticeRequest request) {
        NoticeEntity noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        noticeEntity.setTitle(request.getTitle());
        noticeEntity.setContent(request.getContent());

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
}


