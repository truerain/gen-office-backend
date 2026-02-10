package com.third.gen_office.infrastructure.authentication;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String empNo,
    @NotBlank String password
) {}
