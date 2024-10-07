package com.mucanyu.brokage.auth.service;

import com.mucanyu.brokage.auth.constant.Role;
import com.mucanyu.brokage.auth.dto.AuthenticationDto;
import com.mucanyu.brokage.auth.dto.CustomerDetailDto;
import com.mucanyu.brokage.auth.dto.JwtAuthDto;
import com.mucanyu.brokage.auth.model.Customer;
import com.mucanyu.brokage.auth.repository.CustomerRepository;
import com.mucanyu.brokage.auth.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtHelper jwtHelper;

  @Override
  @Transactional
  public CustomerDetailDto register(AuthenticationDto customerAuthDto) {
    customerRepository.findByUsername(customerAuthDto.getUsername())
        .ifPresent(customer -> {
          throw new RuntimeException("TODO : Customer has already been registered");
        });


    var customer = Customer.builder()
        .username(customerAuthDto.getUsername())
        .password(passwordEncoder.encode(customerAuthDto.getPassword()))
        .role(Role.USER)
        .build();

    customerRepository.save(customer);

    return new CustomerDetailDto(customerAuthDto.getUsername());
  }

  @Override
  @Transactional(readOnly = true)
  public JwtAuthDto login(AuthenticationDto customerAuthDto) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            customerAuthDto.getUsername(), customerAuthDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    var jwt = jwtHelper.generateToken((UserDetails) authentication.getPrincipal());

    return new JwtAuthDto(jwt);
  }

  @Override
  public boolean existsById(Long customerId) {
    return customerRepository.existsById(customerId);
  }
}
