package com.third.gen_office.mis.common.code;

import com.third.gen_office.domain.lookup.LookupDetailEntity;
import com.third.gen_office.domain.lookup.LookupDetailRepository;
import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.mis.common.code.dto.CodeDetailResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CodeService {
    private final LookupDetailRepository lookupDetailRepository;

    public CodeService(LookupDetailRepository lookupDetailRepository) {
        this.lookupDetailRepository = lookupDetailRepository;
    }

    @Transactional(readOnly = true)
    public List<CodeDetailResponse> listDetails(String lkupClssCd) {
        validateClassKey(lkupClssCd);
        return lookupDetailRepository.findByLkupClssCdAndUseYnOrderBySortOrderAscLkupCdAsc(lkupClssCd, "Y")
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private CodeDetailResponse toResponse(LookupDetailEntity entity) {
        return new CodeDetailResponse(
            entity.getLkupClssCd(),
            entity.getLkupCd(),
            entity.getLkupName(),
            entity.getSortOrder(),
            entity.getUseYn()
        );
    }

    private void validateClassKey(String lkupClssCd) {
        if (!StringUtils.hasText(lkupClssCd) || containsWhitespace(lkupClssCd)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
