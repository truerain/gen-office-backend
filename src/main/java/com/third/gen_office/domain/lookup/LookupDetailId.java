package com.third.gen_office.domain.lookup;

import java.io.Serializable;
import java.util.Objects;

public class LookupDetailId implements Serializable {
    private String lkupClssCd;
    private String lkupCd;

    public LookupDetailId() {
    }

    public LookupDetailId(String lkupClssCd, String lkupCd) {
        this.lkupClssCd = lkupClssCd;
        this.lkupCd = lkupCd;
    }

    public String getLkupClssCd() {
        return lkupClssCd;
    }

    public String getLkupCd() {
        return lkupCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LookupDetailId that = (LookupDetailId) o;
        return Objects.equals(lkupClssCd, that.lkupClssCd)
            && Objects.equals(lkupCd, that.lkupCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lkupClssCd, lkupCd);
    }
}
