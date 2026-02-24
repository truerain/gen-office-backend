package com.third.gen_office.mis.common.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CommonUser", description = "Common user search API")
@RestController
@RequestMapping("/api/common/users")
public class UserSearchController {
    private final UserSearchService userSearchService;

    public UserSearchController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @GetMapping
    @Operation(summary = "Search users for popup")
    public List<UserSearchResponse> search(
        @Parameter(description = "Search text (empName)") @RequestParam(value = "q", required = false) String q
    ) {
        return userSearchService.search(q);
    }
}
