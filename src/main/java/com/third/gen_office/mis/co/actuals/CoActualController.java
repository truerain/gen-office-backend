package com.third.gen_office.mis.co.actuals;

import com.third.gen_office.global.api.ApiResponse;
import com.third.gen_office.mis.co.actuals.dto.CoActualResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cost Accounting", description = "Management accounting actuals API")
@RestController
@RequestMapping("/api/co/actuals")
public class CoActualController {
    private final CoActualService coActualService;

    public CoActualController(CoActualService coActualService) {
        this.coActualService = coActualService;
    }

    @GetMapping
    @Operation(summary = "List management accounting actuals")
    public ResponseEntity<ApiResponse<List<CoActualResponse>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(coActualService.list()));
    }
}
