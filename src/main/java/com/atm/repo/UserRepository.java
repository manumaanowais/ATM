package com.atm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUsername(String username);
	public User findByEmail(String email);
	public User findByPhone(String phone);
}
