package com.third.gen_office.mis.admin.notice;

import com.third.gen_office.mis.admin.notice.dto.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.third.gen_office.mis.admin.notice.dto.NoticeRequest;
import com.third.gen_office.mis.admin.notice.dao.NoticeRepository;


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
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .popupYn(request.getPopupYn() != null ? request.getPopupYn() : "N")
                .useYn(request.getUseYn() != null ? request.getUseYn() : "Y")
                .dispStartDate(request.getDispStartDate())
                .dispEndDate(request.getDispEndDate())
                .readCount(0)
                .build();

        return noticeRepository.save(notice).getNoticeId();
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
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 없습니다."));

        return NoticeResponse.DetailDto.from(notice);
    }

    @Transactional
    public void updateReadCount(Integer id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 Notice ID입니다.");
        }
        noticeRepository.incrementReadCount(id);
    }
}