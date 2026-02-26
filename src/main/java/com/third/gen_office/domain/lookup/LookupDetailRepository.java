package com.third.gen_office.domain.lookup;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LookupDetailRepository
    extends JpaRepository<LookupDetailEntity, LookupDetailId>, JpaSpecificationExecutor<LookupDetailEntity> {
    List<LookupDetailEntity> findByLkupClssCdAndUseYnOrderBySortOrderAscLkupCdAsc(
        String lkupClssCd,
        String useYn
    );
}
