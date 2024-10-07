package com.mucanyu.brokage.auth.controller;

import com.mucanyu.brokage.auth.dto.JwtAuthDto;
import com.mucanyu.brokage.auth.dto.CustomerDetailDto;
import com.mucanyu.brokage.auth.dto.AuthenticationDto;
import com.mucanyu.brokage.auth.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AccountAuthController {

  private final CustomerService customerService;

  @PostMapping("/register")
  public ResponseEntity<CustomerDetailDto> register(@RequestBody AuthenticationDto customerAuthDto) {

    return new ResponseEntity<>(customerService.register(customerAuthDto), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtAuthDto> login(@RequestBody AuthenticationDto customerAuthDto) {

    return new ResponseEntity<>(customerService.login(customerAuthDto), HttpStatus.OK);
  }
}
