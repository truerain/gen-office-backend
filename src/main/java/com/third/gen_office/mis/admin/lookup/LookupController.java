package com.third.gen_office.mis.admin.lookup;

import com.third.gen_office.mis.admin.lookup.dto.BulkLookupDetailRequest;
import com.third.gen_office.mis.admin.lookup.dto.BulkLookupMasterRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupDetailResponse;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterRequest;
import com.third.gen_office.mis.admin.lookup.dto.LookupMasterResponse;
import com.third.gen_office.global.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ApiResponse<List<LookupMasterResponse>>> listLkupMaster(
        @RequestParam(value = "lkupClssCd", required = false) String lkupClssCd,
        @RequestParam(value = "lkupClssName", required = false) String lkupClssName,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok(
            ApiResponse.ok(lookupService.listMasters(lkupClssCd, lkupClssName, useYn, q, sort))
        );
    }

    @GetMapping("/masters/{lkupClssCd}")
    @Operation(summary = "Get lookup master")
    public ResponseEntity<ApiResponse<LookupMasterResponse>> getClass(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd
    ) {
        return ResponseEntity.ok(ApiResponse.ok(lookupService.getMaster(lkupClssCd)));
    }

    @PostMapping(value = "/masters", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        "application/json;charset=UTF-8"
    })
    @Operation(summary = "Create lookup master")
    public ResponseEntity<ApiResponse<Void>> createNaster(@RequestBody LookupMasterRequest request) {
        lookupService.createMaster(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/masters/bulk")
    @Operation(summary = "Bulk commit lookup masters")
    public ResponseEntity<ApiResponse<Void>> bulkCommitMasters(@RequestBody BulkLookupMasterRequest request) {
        lookupService.bulkCommitMasters(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping(value = "/masters/{lkupClssCd}", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        "application/json;charset=UTF-8"
    })
    @Operation(summary = "Update lookup master")
    public ResponseEntity<ApiResponse<Void>> updateLkupMaster(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestBody LookupMasterRequest request
    ) {
        lookupService.updateMaster(lkupClssCd, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/{lkupClssCd}/details")
    @Operation(summary = "List lookup details")
    public ResponseEntity<ApiResponse<List<LookupDetailResponse>>> listDetails(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestParam(value = "lkupCd", required = false) String lkupCd,
        @RequestParam(value = "lkupName", required = false) String lkupName,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok(
            ApiResponse.ok(lookupService.listDetails(lkupClssCd, lkupCd, lkupName, useYn, q, sort))
        );
    }

    @GetMapping("/{lkupClssCd}/details/{lkupCd}")
    @Operation(summary = "Get lookup detail")
    public ResponseEntity<ApiResponse<LookupDetailResponse>> getDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @Parameter(description = "lookup code") @PathVariable String lkupCd
    ) {
        return ResponseEntity.ok(ApiResponse.ok(lookupService.getDetail(lkupClssCd, lkupCd)));
    }

    @PostMapping("/{lkupClssCd}/details")
    @Operation(summary = "Create lookup detail")
    public ResponseEntity<ApiResponse<Void>> createDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @RequestBody LookupDetailRequest request
    ) {
        lookupService.createDetail(lkupClssCd, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/details/bulk")
    @Operation(summary = "Bulk commit lookup details")
    public ResponseEntity<ApiResponse<Void>> bulkCommitDetails(@RequestBody BulkLookupDetailRequest request) {
        lookupService.bulkCommitDetails(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{lkupClssCd}/details/{lkupCd}")
    @Operation(summary = "Update lookup detail")
    public ResponseEntity<ApiResponse<Void>> updateDetail(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd,
        @Parameter(description = "lookup code") @PathVariable String lkupCd,
        @RequestBody LookupDetailRequest request
    ) {
        lookupService.updateDetail(lkupClssCd, lkupCd, request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
