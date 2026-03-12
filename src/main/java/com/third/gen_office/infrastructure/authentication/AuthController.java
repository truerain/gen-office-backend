package com.third.gen_office.infrastructure.authentication;

import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.third.gen_office.infrastructure.authorization.UserPrincipal;
import com.third.gen_office.global.api.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthUserResponse>> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletRequest httpRequest
    ) {
        log.info("try to login");
        return authenticateAndCreateSession(request.empNo(), request.password(), httpRequest);
    }

    @GetMapping("/tmp-login")
    public ResponseEntity<ApiResponse<AuthUserResponse>> tmpLogin(
        @RequestParam("empNo") String empNo,
        @RequestParam("pass") String pass,
        HttpServletRequest httpRequest
    ) {
        return authenticateAndCreateSession(empNo, pass, httpRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Map<String, String>>> logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return ResponseEntity.ok(ApiResponse.ok(Map.of("message", "logout")));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthUserResponse>> me(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(AuthUserResponse.from(principal)));
    }

    @GetMapping("/csrf")
    public ResponseEntity<ApiResponse<Map<String, String>>> csrf(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        log.info("try to csrf: " + token.getToken());
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
            "token", token.getToken(),
            "headerName", token.getHeaderName(),
            "parameterName", token.getParameterName()
        )));
    }

    private ResponseEntity<ApiResponse<AuthUserResponse>> authenticateAndCreateSession(
        String empNo,
        String password,
        HttpServletRequest httpRequest
    ) {
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(empNo, password);
            authentication = authenticationManager.authenticate(token);
        } catch (AuthenticationException ex) {
            log.warn("login_failed empNo={} ip={}", empNo, httpRequest.getRemoteAddr());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.failure("auth.invalid_credentials", "invalid_credentials"));
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        session.setAttribute("userId", principal.getUserId());
        session.setAttribute("roles", principal.getRoles());
        session.setAttribute("dept", principal.getOrgName());
        session.setAttribute("loginTime", System.currentTimeMillis());

        log.info("login_success empNo={} userId={} ip={}",
            principal.getEmpNo(),
            principal.getUserId(),
            httpRequest.getRemoteAddr());
        return ResponseEntity.ok(ApiResponse.ok(AuthUserResponse.from(principal)));
    }
}
