package com.third.gen_office.domain.file;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    // 특정 file_set_id에 속한 모든 파일 리스트를 가져옴
    // 공지사항 상세 조회 시 이 메서드를 사용해 파일 목록을 불러옵니다.
    List<FileEntity> findByFileSetId(Integer fileSetId);

    // 필요 시 세트에 속한 파일들을 한꺼번에 삭제 (게시글 삭제 시)
    void deleteByFileSetId(Integer fileSetId);
}