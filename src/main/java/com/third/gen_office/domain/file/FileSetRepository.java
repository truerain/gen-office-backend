package com.third.gen_office.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSetRepository extends JpaRepository<FileSetEntity, Integer> {
}
