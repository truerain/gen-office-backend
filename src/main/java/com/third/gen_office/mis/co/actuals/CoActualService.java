package com.third.gen_office.mis.co.actuals;

import com.third.gen_office.domain.co.actuals.CoActualEntity;
import com.third.gen_office.domain.co.actuals.CoActualRepository;
import com.third.gen_office.mis.co.actuals.dto.CoActualResponse;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CoActualService {
    private final CoActualRepository coActualRepository;

    public CoActualService(CoActualRepository coActualRepository) {
        this.coActualRepository = coActualRepository;
    }

    public List<CoActualResponse> list() {
        List<CoActualEntity> entities = coActualRepository.findAll(
            Sort.by(
                Sort.Order.asc("fiscalYr"),
                Sort.Order.asc("fiscalPrd"),
                Sort.Order.asc("orgCd"),
                Sort.Order.asc("acctCd")
            )
        );

        return entities.stream()
            .map(this::toResponse)
            .toList();
    }

    private CoActualResponse toResponse(CoActualEntity entity) {
        return new CoActualResponse(
            entity.getAcctCd(),
            entity.getAcctName(),
            entity.getAcctLevel(),
            entity.getParentCd(),
            entity.getDrCr(),
            entity.getFiscalYr(),
            entity.getFiscalPrd(),
            entity.getOrgCd(),
            entity.getCurrActAmt(),
            entity.getPlanAmt(),
            entity.getPrevActAmt(),
            entity.getAttribute1(),
            entity.getAttribute2(),
            entity.getAttribute3(),
            entity.getAttribute4(),
            entity.getAttribute5(),
            entity.getAttribute6(),
            entity.getAttribute7(),
            entity.getAttribute8(),
            entity.getAttribute9(),
            entity.getAttribute10(),
            entity.getCreatedBy(),
            entity.getCreationDate() == null ? null : entity.getCreationDate().toString(),
            entity.getLastUpdatedBy(),
            entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }
}
