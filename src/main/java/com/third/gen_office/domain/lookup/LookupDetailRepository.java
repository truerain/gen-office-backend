package com.third.gen_office.domain.lookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LookupDetailRepository
    extends JpaRepository<LookupDetailEntity, LookupDetailId>, JpaSpecificationExecutor<LookupDetailEntity> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        value = """
            UPDATE tb_cm_lkup_detail
            SET lkup_name = :lkupName,
                lkup_name_eng = :lkupNameEng,
                sort_order = :sortOrder,
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
                attribute11 = :attribute11,
                attribute12 = :attribute12,
                attribute13 = :attribute13,
                attribute14 = :attribute14,
                attribute15 = :attribute15,
                attribute16 = :attribute16,
                attribute17 = :attribute17,
                attribute18 = :attribute18,
                attribute19 = :attribute19,
                attribute20 = :attribute20,
                last_updated_date = CURRENT_TIMESTAMP
            WHERE lkup_clss_cd = :lkupClssCd
              AND lkup_cd = :lkupCd
            """,
        nativeQuery = true
    )
    int updateLookupDetail(
        @Param("lkupClssCd") String lkupClssCd,
        @Param("lkupCd") String lkupCd,
        @Param("lkupName") String lkupName,
        @Param("lkupNameEng") String lkupNameEng,
        @Param("sortOrder") Integer sortOrder,
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
        @Param("attribute10") String attribute10,
        @Param("attribute11") String attribute11,
        @Param("attribute12") String attribute12,
        @Param("attribute13") String attribute13,
        @Param("attribute14") String attribute14,
        @Param("attribute15") String attribute15,
        @Param("attribute16") String attribute16,
        @Param("attribute17") String attribute17,
        @Param("attribute18") String attribute18,
        @Param("attribute19") String attribute19,
        @Param("attribute20") String attribute20
    );
}
