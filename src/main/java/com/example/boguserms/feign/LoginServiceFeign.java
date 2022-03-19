package com.example.boguserms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "login-service")
public interface LoginServiceFeign {

    @GetMapping("/api/validateToken")
    String validateToken(@RequestParam(name = "token") String token);
}
