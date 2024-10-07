package com.mucanyu.brokage.auth.model;

import com.mucanyu.brokage.auth.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMERS")
public class Customer implements Serializable {

  private static final long serialVersionUID = -3180455600514579090L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USERNAME", nullable = false)
  private String username;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "ROLE", nullable = false)
  private Role role;
}
