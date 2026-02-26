package com.third.gen_office.mis.admin.lookup;

import com.third.gen_office.mis.admin.lookup.dto.LookupMasterCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterUpdateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailCreateRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Lookup", description = "Lookup code admin API")
@RestController
@RequestMapping("/api/mis/admin/lookups")
public class LookupController {
    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/masters")
    @Operation(summary = "List lookup masters")
    public List<LookupMasterResponse> listLkupMaster(
        @RequestParam(value = "lkupClssCd", required = false) String lkupClssCd,
        @RequestParam(value = "lkupClssName", required = false) String lkupClssName,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return lookupService.listMasters(lkupClssCd, lkupClssName, useYn, q, sort);
    }

    @GetMapping("/masters/{lkupClssCd}")
    @Operation(summary = "Get lookup master")
    public LookupMasterResponse getClass(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd
    ) {
        return lookupService.getMaster(lkupClssCd);
    }

    @PostMapping(value = "/masters", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        "application/json;charset=UTF-8"
    })
    @Operation(summary = "Create lookup master")
    public ResponseEntity<LookupMasterResponse> createNaster(@RequestBody LookupMasterCreateRequest request) {
        LookupMasterResponse response = lookupService.createMaster(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/masters/{lkupClssCd}", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        "application/json;charset=UTF-8"
    })
    @Operation(summary = "Update lookup master")
    public LookupMasterResponse updateLkupMaster(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestBody LookupMasterUpdateRequest request
    ) {
        return lookupService.updateMaster(lkupClssCd, request);
    }

    @GetMapping("/{lkupClssCd}/details")
    @Operation(summary = "List lookup details")
    public List<LookupDetailResponse> listDetails(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestParam(value = "lkupCd", required = false) String lkupCd,
        @RequestParam(value = "lkupName", required = false) String lkupName,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return lookupService.listDetails(lkupClssCd, lkupCd, lkupName, useYn, q, sort);
    }

    @GetMapping("/{lkupClssCd}/details/{lkupCd}")
    @Operation(summary = "Get lookup detail")
    public LookupDetailResponse getDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @Parameter(description = "lookup code") @PathVariable String lkupCd
    ) {
        return lookupService.getDetail(lkupClssCd, lkupCd);
    }

    @PostMapping("/{lkupClssCd}/details")
    @Operation(summary = "Create lookup detail")
    public ResponseEntity<LookupDetailResponse> createDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestBody LookupDetailCreateRequest request
    ) {
        LookupDetailResponse response = lookupService.createDetail(lkupClssCd, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{lkupClssCd}/details/{lkupCd}")
    @Operation(summary = "Update lookup detail")
    public LookupDetailResponse updateDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @Parameter(description = "lookup code") @PathVariable String lkupCd,
        @RequestBody LookupDetailUpdateRequest request
    ) {
        return lookupService.updateDetail(lkupClssCd, lkupCd, request);
    }
}
