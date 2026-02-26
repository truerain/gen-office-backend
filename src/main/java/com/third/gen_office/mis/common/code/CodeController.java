package com.third.gen_office.mis.common.code;

import com.third.gen_office.mis.common.code.dto.CodeDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CommonCode", description = "Common code lookup API")
@RestController
@RequestMapping("/api/common/codes")
public class CodeController {
    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/{lkupClssCd}")
    @Operation(summary = "List lookup details by class code")
    public List<CodeDetailResponse> listDetails(
        @Parameter(description = "lookup class code") @PathVariable String lkupClssCd
    ) {
        return codeService.listDetails(lkupClssCd);
    }
}
