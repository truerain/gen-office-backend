package com.third.gen_office.domain.lookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface LookupMasterRepository
    extends JpaRepository<LookupMasterEntity, String>, JpaSpecificationExecutor<LookupMasterEntity> {
}
