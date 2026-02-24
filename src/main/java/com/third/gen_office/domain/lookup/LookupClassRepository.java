package com.third.gen_office.domain.lookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LookupClassRepository
    extends JpaRepository<LookupClassEntity, String>, JpaSpecificationExecutor<LookupClassEntity> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        value = """
            UPDATE tb_cm_lkup_master
            SET lkup_clss_name = :lkupClssName,
                lkup_clss_desc = :lkupClssDesc,
                use_yn = :useYn,
                attribute1 = :attribute1,
                attribute2 = :attribute2,
                attribute3 = :attribute3,
                attribute4 = :attribute4,
                attribute5 = :attribute5,
                attribute6 = :attribute6,
                attribute7 = :attribute7,
                attribute8 = :attribute8,
                attribute9 = :attribute9,
                attribute10 = :attribute10,
                last_updated_date = CURRENT_TIMESTAMP
            WHERE lkup_clss_cd = :lkupClssCd
            """,
        nativeQuery = true
    )
    int updateLookupClass(
        @Param("lkupClssCd") String lkupClssCd,
        @Param("lkupClssName") String lkupClssName,
        @Param("lkupClssDesc") String lkupClssDesc,
        @Param("useYn") String useYn,
        @Param("attribute1") String attribute1,
        @Param("attribute2") String attribute2,
        @Param("attribute3") String attribute3,
        @Param("attribute4") String attribute4,
        @Param("attribute5") String attribute5,
        @Param("attribute6") String attribute6,
        @Param("attribute7") String attribute7,
        @Param("attribute8") String attribute8,
        @Param("attribute9") String attribute9,
        @Param("attribute10") String attribute10
    );
}
