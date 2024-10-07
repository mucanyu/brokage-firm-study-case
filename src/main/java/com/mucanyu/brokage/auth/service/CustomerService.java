package com.mucanyu.brokage.auth.service;

import com.mucanyu.brokage.auth.dto.AuthenticationDto;
import com.mucanyu.brokage.auth.dto.CustomerDetailDto;
import com.mucanyu.brokage.auth.dto.JwtAuthDto;


public interface CustomerService {

  CustomerDetailDto register(AuthenticationDto customerAuthDto);

  JwtAuthDto login(AuthenticationDto customerAuthDto);

  boolean existsById(Long customerId);
}
