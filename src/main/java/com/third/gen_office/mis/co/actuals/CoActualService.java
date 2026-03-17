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
            entity.getM01(),
            entity.getM02(),
            entity.getM03(),
            entity.getM04(),
            entity.getM05(),
            entity.getM06(),
            entity.getM07(),
            entity.getM08(),
            entity.getM09(),
            entity.getM10(),
            entity.getM11(),
            entity.getM12(),
            entity.getLastUpdatedBy(),
            entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }
}
