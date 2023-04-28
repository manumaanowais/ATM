package com.atm.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Username cannot be blank!")
	private String username;
	@NotBlank(message = "Pin cannot be blank!")
	private String pin;
	@NotBlank(message = "Firstname cannot be blank!")
	private String firstName;
	private String lastName;
	@NotBlank(message = "Email cannot be blank!")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a valid email!")
	private String email;
	@NotBlank(message = "Phone number cannot be blank!")
	private String phone;
	@NotNull(message = "You must deposit some money!")
	private int deposit;
	@AssertTrue(message = "Must agree the terms and conditions!")
	private boolean agreed;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRoles = new HashSet<>(); // A user can have many user roles
	public User() {}
	public User(Long id, @NotBlank(message = "Username cannot be blank!") String username,
			@NotBlank(message = "Pin cannot be blank!") String pin,
			@NotBlank(message = "Firstname cannot be blank!") String firstName, String lastName,
			@NotBlank(message = "Email cannot be blank!") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a valid email!") String email,
			@NotBlank(message = "Phone number cannot be blank!") String phone,
			@NotBlank(message = "You must deposit some money!") int deposit,
			@AssertTrue(message = "Must agree the terms and conditions!") boolean agreed, Set<UserRole> userRoles) {
		super();
		this.id = id;
		this.username = username;
		this.pin = pin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.deposit = deposit;
		this.agreed = agreed;
		this.userRoles = userRoles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	public boolean isAgreed() {
		return agreed;
	}
	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", pin=" + pin + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", deposit=" + deposit + ", agreed=" + agreed
				+ "]";
	}
}
