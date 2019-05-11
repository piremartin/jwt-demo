package com.chj.demo.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.chj.demo.config.constant.SecurityConstant;
import com.chj.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author chehaojie
 * @date 2019/05/11 16:56
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final JwtService jwtService;

    @GetMapping("/register")
    public ResponseEntity<JSONObject> register(@RequestParam String username, HttpServletResponse response) {
        String jwt = jwtService.generateToken(username);
        response.setHeader(SecurityConstant.AUTHORIZATION_HEADER, jwt);

        JSONObject returnObj = new JSONObject();
        returnObj.put("id_token", jwt);
        return ResponseEntity.ok(returnObj);
    }
}
