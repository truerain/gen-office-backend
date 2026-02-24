package com.third.gen_office.mis.admin.lookup;

import com.third.gen_office.mis.admin.lookup.dto.LookupClassCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassListResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupClassUpdateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailListResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Lookup", description = "Lookup code admin API")
@RestController
@RequestMapping("/api/mis/admin/lookups")
public class LookupController {
    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/classes")
    @Operation(summary = "List lookup classes")
    public LookupClassListResponse listClasses(
        @RequestParam(value = "lkupClssCd", required = false) String lkupClssCd,
        @RequestParam(value = "lkupClssName", required = false) String lkupClssName,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "20") int size,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return lookupService.listClasses(lkupClssCd, lkupClssName, useYn, q, page, size, sort);
    }

    @GetMapping("/classes/{lkupClssCd}")
    @Operation(summary = "Get lookup class")
    public LookupClassResponse getClass(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd
    ) {
        return lookupService.getClass(lkupClssCd);
    }

    @PostMapping("/classes")
    @Operation(summary = "Create lookup class")
    public ResponseEntity<LookupClassResponse> createClass(@RequestBody LookupClassCreateRequest request) {
        LookupClassResponse response = lookupService.createClass(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/classes/{lkupClssCd}")
    @Operation(summary = "Update lookup class")
    public LookupClassResponse updateClass(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestBody LookupClassUpdateRequest request
    ) {
        return lookupService.updateClass(lkupClssCd, request);
    }

    @GetMapping("/{lkupClssCd}/items")
    @Operation(summary = "List lookup details")
    public LookupDetailListResponse listDetails(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestParam(value = "lkupCd", required = false) String lkupCd,
        @RequestParam(value = "lkupName", required = false) String lkupName,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "50") int size,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return lookupService.listDetails(lkupClssCd, lkupCd, lkupName, useYn, q, page, size, sort);
    }

    @GetMapping("/{lkupClssCd}/items/{lkupCd}")
    @Operation(summary = "Get lookup detail")
    public LookupDetailResponse getDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @Parameter(description = "lookup code") @PathVariable String lkupCd
    ) {
        return lookupService.getDetail(lkupClssCd, lkupCd);
    }

    @PostMapping("/{lkupClssCd}/items")
    @Operation(summary = "Create lookup detail")
    public ResponseEntity<LookupDetailResponse> createDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestBody LookupDetailCreateRequest request
    ) {
        LookupDetailResponse response = lookupService.createDetail(lkupClssCd, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{lkupClssCd}/items/{lkupCd}")
    @Operation(summary = "Update lookup detail")
    public LookupDetailResponse updateDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @Parameter(description = "lookup code") @PathVariable String lkupCd,
        @RequestBody LookupDetailUpdateRequest request
    ) {
        return lookupService.updateDetail(lkupClssCd, lkupCd, request);
    }
}
