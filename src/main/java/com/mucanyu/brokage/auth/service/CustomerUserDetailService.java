package com.mucanyu.brokage.auth.service;

import com.mucanyu.brokage.auth.model.CustomerUserDetails;
import com.mucanyu.brokage.auth.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var customer = customerRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

    return new CustomerUserDetails(customer);
  }
}
