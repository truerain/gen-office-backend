package com.third.gen_office.mis.admin.notice.dao;

import com.third.gen_office.mis.admin.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    // 1. 제목으로 공지사항 검색 (subject 컬럼 매핑)
    List<Notice> findByTitleContaining(String title);

    // 2. 전시 여부(use_yn)가 'Y'인 공지사항만 조회
    List<Notice> findByUseYn(String useYn);

    // 3. 페이징 처리가 포함된 전체 조회 (관리자 페이지용)
    Page<Notice> findAll(Pageable pageable);

    // 4. 특정 날짜 사이에 전시되는 공지사항 조회 (예시)
    List<Notice> findByDispStartDateLessThanEqualAndDispEndDateGreaterThanEqual(String now1, String now2);

    @Modifying // 데이터 변경이 일어나는 쿼리임을 명시
    @Query("UPDATE Notice n SET n.readCount = n.readCount + 1 WHERE n.noticeId = :id")
    void incrementReadCount(@Param("id") Integer id);
}