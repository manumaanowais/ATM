package com.atm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
