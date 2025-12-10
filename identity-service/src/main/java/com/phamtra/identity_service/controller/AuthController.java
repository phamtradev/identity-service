package com.phamtra.identity_service.controller;

import com.phamtra.identity_service.dto.request.LoginDTO;
import com.phamtra.identity_service.dto.respone.LoginDTORespone;
import com.phamtra.identity_service.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          SecurityUtil securityUtil) {
        this.authenticationManager = authenticationManager;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTORespone> login(@RequestBody LoginDTO loginDTO) {
        // nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xác thực người dùng -> cần ghi đè loadUserByUsername bằng cách tạo
        // UserDetailCustom implement UserDetails
        // use AuthenticationManager so configured PasswordEncoder/UserDetailsService are applied
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //create a token
        String access_token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginDTORespone res = new LoginDTORespone();
        res.setAccessToken(access_token);
        return ResponseEntity.ok().body(res);
    }
}
