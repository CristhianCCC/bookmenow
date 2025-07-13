package com.businessdomain.catalog.userclient;

import com.businessdomain.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//this class is to request access directly to user through gateway
@FeignClient(name = "gateway", path = "/users", configuration = com.businessdomain.catalog.config.FeignClientInterceptorConfig.class)
public interface UserClient {
    @GetMapping("/email/{email}")
    UserDTO getUserByEmail(@PathVariable("email") String email);
}