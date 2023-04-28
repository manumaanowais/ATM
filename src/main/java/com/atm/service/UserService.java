package com.atm.service;

import java.util.Set;

import com.atm.model.User;
import com.atm.model.UserRole;

public interface UserService {
		// Create User
		public User createUser(User user, Set<UserRole> userRoles) throws Exception;

		// Delete User by userid
		public void deleteUser(Long userId);

		//Update User
		public void updateUser(User user, Set<UserRole> userRoles) throws Exception;
		
		// Get User by username
		public User getUser(String username);
		
		//Get All Users
		public Set<User> getUsers();
		
		//Get User by email
		public User getByEmail(String email);
		
		//Get User by phone
		public User getByPhone(String phone);
		
		//Get User by id
		public User getUser(Long id);
		
		//Encryption
		public String encrypt(String password) throws Exception;
		
		//Decryption
		public String decrypt(String password) throws Exception;
}
