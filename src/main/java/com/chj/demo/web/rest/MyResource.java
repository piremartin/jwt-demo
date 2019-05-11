package com.chj.demo.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author chehaojie
 * @date 2019/05/11 20:49
 */
@RestController
@RequestMapping("/api/my")
public class MyResource {

    @GetMapping("/hello")
    public ResponseEntity hello(){
        return ResponseEntity.ok(LocalDateTime.now());
    }
}
