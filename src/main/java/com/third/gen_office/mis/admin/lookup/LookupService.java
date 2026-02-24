package com.third.gen_office.mis.admin.lookup;

import com.third.gen_office.domain.lookup.LookupClassEntity;
import com.third.gen_office.domain.lookup.LookupClassRepository;
import com.third.gen_office.domain.lookup.LookupDetailEntity;
import com.third.gen_office.domain.lookup.LookupDetailId;
import com.third.gen_office.domain.lookup.LookupDetailRepository;
import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.global.error.ConflictException;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassListResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassUpdateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailListResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailUpdateRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class LookupService {
    private final LookupClassRepository lookupClassRepository;
    private final LookupDetailRepository lookupDetailRepository;

    public LookupService(
        LookupClassRepository lookupClassRepository,
        LookupDetailRepository lookupDetailRepository
    ) {
        this.lookupClassRepository = lookupClassRepository;
        this.lookupDetailRepository = lookupDetailRepository;
    }

    @Transactional(readOnly = true)
    public LookupClassListResponse listClasses(
        String lkupClssCd,
        String lkupClssName,
        String useYn,
        String q,
        int page,
        int size,
        String sort
    ) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = normalizeSize(size, 20);
        validateUseYn(useYn);

        Specification<LookupClassEntity> spec = buildClassSpecification(lkupClssCd, lkupClssName, useYn, q);
        Sort sortSpec = toClassSort(sort);
        Page<LookupClassEntity> result = lookupClassRepository.findAll(
            spec,
            PageRequest.of(normalizedPage, normalizedSize, sortSpec)
        );

        List<LookupClassResponse> items = result.getContent().stream()
            .map(this::toClassResponse)
            .toList();

        return new LookupClassListResponse(items, normalizedPage, normalizedSize, result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public LookupClassResponse getClass(String lkupClssCd) {
        validateClassKey(lkupClssCd);
        LookupClassEntity entity = lookupClassRepository.findById(lkupClssCd)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));
        return toClassResponse(entity);
    }

    @Transactional
    public LookupClassResponse createClass(LookupClassCreateRequest request) {
        validateCreateClassRequest(request);
        String key = request.lkupClssCd().trim();
        if (lookupClassRepository.existsById(key)) {
            throw new ConflictException("lookup.duplicate");
        }

        LookupClassEntity entity = LookupClassEntity.builder()
            .lkupClssCd(key)
            .lkupClssName(request.lkupClssName())
            .lkupClssDesc(request.lkupClssDesc())
            .useYn(defaultUseYn(request.useYn()))
            .attribute1(request.attribute1())
            .attribute2(request.attribute2())
            .attribute3(request.attribute3())
            .attribute4(request.attribute4())
            .attribute5(request.attribute5())
            .attribute6(request.attribute6())
            .attribute7(request.attribute7())
            .attribute8(request.attribute8())
            .attribute9(request.attribute9())
            .attribute10(request.attribute10())
            .build();
        lookupClassRepository.save(entity);
        return getClass(key);
    }

    @Transactional
    public LookupClassResponse updateClass(String lkupClssCd, LookupClassUpdateRequest request) {
        validateClassKey(lkupClssCd);
        validateUpdateClassRequest(request);
        LookupClassEntity existing = lookupClassRepository.findById(lkupClssCd)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));

        String lkupClssName = request.lkupClssName() != null ? request.lkupClssName() : existing.getLkupClssName();
        String lkupClssDesc = request.lkupClssDesc() != null ? request.lkupClssDesc() : existing.getLkupClssDesc();
        String useYn = request.useYn() != null ? normalizeUseYn(request.useYn()) : existing.getUseYn();
        String attribute1 = request.attribute1() != null ? request.attribute1() : existing.getAttribute1();
        String attribute2 = request.attribute2() != null ? request.attribute2() : existing.getAttribute2();
        String attribute3 = request.attribute3() != null ? request.attribute3() : existing.getAttribute3();
        String attribute4 = request.attribute4() != null ? request.attribute4() : existing.getAttribute4();
        String attribute5 = request.attribute5() != null ? request.attribute5() : existing.getAttribute5();
        String attribute6 = request.attribute6() != null ? request.attribute6() : existing.getAttribute6();
        String attribute7 = request.attribute7() != null ? request.attribute7() : existing.getAttribute7();
        String attribute8 = request.attribute8() != null ? request.attribute8() : existing.getAttribute8();
        String attribute9 = request.attribute9() != null ? request.attribute9() : existing.getAttribute9();
        String attribute10 = request.attribute10() != null ? request.attribute10() : existing.getAttribute10();

        lookupClassRepository.updateLookupClass(
            lkupClssCd,
            lkupClssName,
            lkupClssDesc,
            useYn,
            attribute1,
            attribute2,
            attribute3,
            attribute4,
            attribute5,
            attribute6,
            attribute7,
            attribute8,
            attribute9,
            attribute10
        );
        return getClass(lkupClssCd);
    }

    @Transactional(readOnly = true)
    public LookupDetailListResponse listDetails(
        String lkupClssCd,
        String lkupCd,
        String lkupName,
        String useYn,
        String q,
        int page,
        int size,
        String sort
    ) {
        validateClassKey(lkupClssCd);
        validateUseYn(useYn);
        ensureClassExists(lkupClssCd);

        int normalizedPage = Math.max(page, 0);
        int normalizedSize = normalizeSize(size, 50);
        Specification<LookupDetailEntity> spec =
            buildDetailSpecification(lkupClssCd, lkupCd, lkupName, useYn, q);
        Sort sortSpec = toDetailSort(sort);
        Page<LookupDetailEntity> result = lookupDetailRepository.findAll(
            spec,
            PageRequest.of(normalizedPage, normalizedSize, sortSpec)
        );

        List<LookupDetailResponse> items = result.getContent().stream()
            .map(this::toDetailResponse)
            .toList();

        return new LookupDetailListResponse(items, normalizedPage, normalizedSize, result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public LookupDetailResponse getDetail(String lkupClssCd, String lkupCd) {
        validateClassKey(lkupClssCd);
        validateDetailKey(lkupCd);
        LookupDetailId id = new LookupDetailId(lkupClssCd, lkupCd);
        LookupDetailEntity entity = lookupDetailRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));
        return toDetailResponse(entity);
    }

    @Transactional
    public LookupDetailResponse createDetail(String lkupClssCd, LookupDetailCreateRequest request) {
        validateClassKey(lkupClssCd);
        validateCreateDetailRequest(request);
        ensureClassExists(lkupClssCd);

        String lkupCd = request.lkupCd().trim();
        LookupDetailId id = new LookupDetailId(lkupClssCd, lkupCd);
        if (lookupDetailRepository.existsById(id)) {
            throw new ConflictException("lookup.duplicate");
        }

        LookupDetailEntity entity = LookupDetailEntity.builder()
            .lkupClssCd(lkupClssCd)
            .lkupCd(lkupCd)
            .lkupName(request.lkupName())
            .lkupNameEng(request.lkupNameEng())
            .sortOrder(request.sortOrder())
            .useYn(defaultUseYn(request.useYn()))
            .attribute1(request.attribute1())
            .attribute2(request.attribute2())
            .attribute3(request.attribute3())
            .attribute4(request.attribute4())
            .attribute5(request.attribute5())
            .attribute6(request.attribute6())
            .attribute7(request.attribute7())
            .attribute8(request.attribute8())
            .attribute9(request.attribute9())
            .attribute10(request.attribute10())
            .attribute11(request.attribute11())
            .attribute12(request.attribute12())
            .attribute13(request.attribute13())
            .attribute14(request.attribute14())
            .attribute15(request.attribute15())
            .attribute16(request.attribute16())
            .attribute17(request.attribute17())
            .attribute18(request.attribute18())
            .attribute19(request.attribute19())
            .attribute20(request.attribute20())
            .build();
        lookupDetailRepository.save(entity);
        return getDetail(lkupClssCd, lkupCd);
    }

    @Transactional
    public LookupDetailResponse updateDetail(
        String lkupClssCd,
        String lkupCd,
        LookupDetailUpdateRequest request
    ) {
        validateClassKey(lkupClssCd);
        validateDetailKey(lkupCd);
        validateUpdateDetailRequest(request);

        LookupDetailId id = new LookupDetailId(lkupClssCd, lkupCd);
        LookupDetailEntity existing = lookupDetailRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("lookup.not_found"));

        String lkupName = request.lkupName() != null ? request.lkupName() : existing.getLkupName();
        String lkupNameEng = request.lkupNameEng() != null ? request.lkupNameEng() : existing.getLkupNameEng();
        Integer sortOrder = request.sortOrder() != null ? request.sortOrder() : existing.getSortOrder();
        String useYn = request.useYn() != null ? normalizeUseYn(request.useYn()) : existing.getUseYn();
        String attribute1 = request.attribute1() != null ? request.attribute1() : existing.getAttribute1();
        String attribute2 = request.attribute2() != null ? request.attribute2() : existing.getAttribute2();
        String attribute3 = request.attribute3() != null ? request.attribute3() : existing.getAttribute3();
        String attribute4 = request.attribute4() != null ? request.attribute4() : existing.getAttribute4();
        String attribute5 = request.attribute5() != null ? request.attribute5() : existing.getAttribute5();
        String attribute6 = request.attribute6() != null ? request.attribute6() : existing.getAttribute6();
        String attribute7 = request.attribute7() != null ? request.attribute7() : existing.getAttribute7();
        String attribute8 = request.attribute8() != null ? request.attribute8() : existing.getAttribute8();
        String attribute9 = request.attribute9() != null ? request.attribute9() : existing.getAttribute9();
        String attribute10 = request.attribute10() != null ? request.attribute10() : existing.getAttribute10();
        String attribute11 = request.attribute11() != null ? request.attribute11() : existing.getAttribute11();
        String attribute12 = request.attribute12() != null ? request.attribute12() : existing.getAttribute12();
        String attribute13 = request.attribute13() != null ? request.attribute13() : existing.getAttribute13();
        String attribute14 = request.attribute14() != null ? request.attribute14() : existing.getAttribute14();
        String attribute15 = request.attribute15() != null ? request.attribute15() : existing.getAttribute15();
        String attribute16 = request.attribute16() != null ? request.attribute16() : existing.getAttribute16();
        String attribute17 = request.attribute17() != null ? request.attribute17() : existing.getAttribute17();
        String attribute18 = request.attribute18() != null ? request.attribute18() : existing.getAttribute18();
        String attribute19 = request.attribute19() != null ? request.attribute19() : existing.getAttribute19();
        String attribute20 = request.attribute20() != null ? request.attribute20() : existing.getAttribute20();

        lookupDetailRepository.updateLookupDetail(
            lkupClssCd,
            lkupCd,
            lkupName,
            lkupNameEng,
            sortOrder,
            useYn,
            attribute1,
            attribute2,
            attribute3,
            attribute4,
            attribute5,
            attribute6,
            attribute7,
            attribute8,
            attribute9,
            attribute10,
            attribute11,
            attribute12,
            attribute13,
            attribute14,
            attribute15,
            attribute16,
            attribute17,
            attribute18,
            attribute19,
            attribute20
        );
        return getDetail(lkupClssCd, lkupCd);
    }

    private Specification<LookupClassEntity> buildClassSpecification(
        String lkupClssCd,
        String lkupClssName,
        String useYn,
        String q
    ) {
        Specification<LookupClassEntity> spec = Specification.where(null);
        if (StringUtils.hasText(lkupClssCd)) {
            String like = "%" + lkupClssCd + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupClssCd"), like));
        }
        if (StringUtils.hasText(lkupClssName)) {
            String like = "%" + lkupClssName + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupClssName"), like));
        }
        if (StringUtils.hasText(useYn)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("useYn"), useYn));
        }
        if (StringUtils.hasText(q)) {
            String like = "%" + q + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                cb.like(root.get("lkupClssName"), like),
                cb.like(root.get("lkupClssDesc"), like)
            ));
        }
        return spec;
    }

    private Specification<LookupDetailEntity> buildDetailSpecification(
        String lkupClssCd,
        String lkupCd,
        String lkupName,
        String useYn,
        String q
    ) {
        Specification<LookupDetailEntity> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("lkupClssCd"), lkupClssCd));
        if (StringUtils.hasText(lkupCd)) {
            String like = "%" + lkupCd + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupCd"), like));
        }
        if (StringUtils.hasText(lkupName)) {
            String like = "%" + lkupName + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("lkupName"), like));
        }
        if (StringUtils.hasText(useYn)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("useYn"), useYn));
        }
        if (StringUtils.hasText(q)) {
            String like = "%" + q + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                cb.like(root.get("lkupName"), like),
                cb.like(root.get("lkupNameEng"), like)
            ));
        }
        return spec;
    }

    private Sort toClassSort(String sort) {
        Map<String, String> allowed = new HashMap<>();
        allowed.put("lkup_clss_cd", "lkupClssCd");
        allowed.put("lkupClssCd", "lkupClssCd");
        allowed.put("lkup_clss_name", "lkupClssName");
        allowed.put("lkupClssName", "lkupClssName");
        allowed.put("creation_date", "creationDate");
        allowed.put("createdAt", "creationDate");
        allowed.put("last_updated_date", "lastUpdatedDate");
        allowed.put("updatedAt", "lastUpdatedDate");

        if (!StringUtils.hasText(sort)) {
            return Sort.by("lkupClssCd").ascending();
        }
        List<Sort.Order> orders = parseSort(sort, allowed);
        if (orders.isEmpty()) {
            return Sort.by("lkupClssCd").ascending();
        }
        return Sort.by(orders);
    }

    private Sort toDetailSort(String sort) {
        Map<String, String> allowed = new HashMap<>();
        allowed.put("lkup_cd", "lkupCd");
        allowed.put("lkupCd", "lkupCd");
        allowed.put("lkup_name", "lkupName");
        allowed.put("lkupName", "lkupName");
        allowed.put("lkup_name_eng", "lkupNameEng");
        allowed.put("lkupNameEng", "lkupNameEng");
        allowed.put("sort_order", "sortOrder");
        allowed.put("sortOrder", "sortOrder");
        allowed.put("creation_date", "creationDate");
        allowed.put("createdAt", "creationDate");
        allowed.put("last_updated_date", "lastUpdatedDate");
        allowed.put("updatedAt", "lastUpdatedDate");

        if (!StringUtils.hasText(sort)) {
            return Sort.by("sortOrder").ascending().and(Sort.by("lkupCd").ascending());
        }
        List<Sort.Order> orders = parseSort(sort, allowed);
        if (orders.isEmpty()) {
            return Sort.by("sortOrder").ascending().and(Sort.by("lkupCd").ascending());
        }
        return Sort.by(orders);
    }

    private List<Sort.Order> parseSort(String sort, Map<String, String> allowed) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] tokens = sort.split(",");
        for (String raw : tokens) {
            String token = raw.trim();
            if (token.isEmpty()) {
                continue;
            }
            String[] parts = token.split("\\s+");
            String property = allowed.get(parts[0]);
            if (property == null) {
                continue;
            }
            Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, property));
        }
        return orders;
    }

    private LookupClassResponse toClassResponse(LookupClassEntity entity) {
        return new LookupClassResponse(
            entity.getLkupClssCd(),
            entity.getLkupClssName(),
            entity.getLkupClssDesc(),
            entity.getUseYn(),
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
            entity.getCreationDate(),
            entity.getLastUpdatedDate()
        );
    }

    private LookupDetailResponse toDetailResponse(LookupDetailEntity entity) {
        return new LookupDetailResponse(
            entity.getLkupClssCd(),
            entity.getLkupCd(),
            entity.getLkupName(),
            entity.getLkupNameEng(),
            entity.getSortOrder(),
            entity.getUseYn(),
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
            entity.getAttribute11(),
            entity.getAttribute12(),
            entity.getAttribute13(),
            entity.getAttribute14(),
            entity.getAttribute15(),
            entity.getAttribute16(),
            entity.getAttribute17(),
            entity.getAttribute18(),
            entity.getAttribute19(),
            entity.getAttribute20(),
            entity.getCreationDate(),
            entity.getLastUpdatedDate()
        );
    }

    private void validateCreateClassRequest(LookupClassCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateClassKey(request.lkupClssCd());
        validateUseYn(request.useYn());
    }

    private void validateUpdateClassRequest(LookupClassUpdateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateUseYn(request.useYn());
    }

    private void validateCreateDetailRequest(LookupDetailCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateDetailKey(request.lkupCd());
        validateUseYn(request.useYn());
        validateSortOrder(request.sortOrder());
    }

    private void validateUpdateDetailRequest(LookupDetailUpdateRequest request) {
        if (request == null) {
            throw new BadRequestException("lookup.invalid_request");
        }
        validateUseYn(request.useYn());
        validateSortOrder(request.sortOrder());
    }

    private void validateClassKey(String lkupClssCd) {
        if (!StringUtils.hasText(lkupClssCd) || containsWhitespace(lkupClssCd)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private void validateDetailKey(String lkupCd) {
        if (!StringUtils.hasText(lkupCd) || containsWhitespace(lkupCd)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private void validateUseYn(String useYn) {
        if (!StringUtils.hasText(useYn)) {
            return;
        }
        if (!"Y".equalsIgnoreCase(useYn) && !"N".equalsIgnoreCase(useYn)) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private void validateSortOrder(Integer sortOrder) {
        if (sortOrder != null && sortOrder < 0) {
            throw new BadRequestException("lookup.invalid_request");
        }
    }

    private String defaultUseYn(String useYn) {
        return StringUtils.hasText(useYn) ? normalizeUseYn(useYn) : "Y";
    }

    private String normalizeUseYn(String useYn) {
        return useYn == null ? null : useYn.trim().toUpperCase();
    }

    private int normalizeSize(int size, int defaultSize) {
        if (size <= 0) {
            return defaultSize;
        }
        return Math.min(size, 200);
    }

    private boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void ensureClassExists(String lkupClssCd) {
        if (!lookupClassRepository.existsById(lkupClssCd)) {
            throw new NotFoundException("lookup.not_found");
        }
    }
}
