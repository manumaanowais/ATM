package com.atm.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.atm.model.User;
import com.atm.service.PhotoService;
import com.atm.service.StatementService;
import com.atm.service.UserService;
import com.atm.model.Photo;
import com.atm.model.Role;
import com.atm.model.Statement;
import com.atm.model.UserRole;

import jakarta.validation.Valid;
@Controller
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StatementService statementService;
	
	@Autowired
	private PhotoService photoService;
	
	@PostMapping("/addUser")
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return "redirect:/signup";
		} else if (userService.getUser(user.getUsername()) != null) {
			return "usererror";
		} else {
			// Encrypting Password
			user.setPin(userService.encrypt(user.getPin()));
			// End of Encrypting Password

			Set<UserRole> roles = new HashSet<>();
			Role role = new Role();
			role.setRoleId(45L);
			role.setRoleName("NORMAL");

			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);

			roles.add(userRole);
			userService.createUser(user, roles);
			return "accountcreated";
		}
	}
	
	@DeleteMapping("/delete/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
	}

	@GetMapping("/signup")
	public String getFormData(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@GetMapping("/signin")
	public String getLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "signin";
	}
	
	@GetMapping("/roles")
	public String getRole(Model model, @ModelAttribute("user") User user) {
		Set<UserRole> roles = userService.getUser(user.getUsername()).getUserRoles();
		System.out.println(roles);
		for (UserRole r : roles) {
			System.out.println(r);
		}
		model.addAttribute("roles", roles);
		return "uroles";
	}
	
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return userService.getUser(username);
	}
	
	@PostMapping("/dashboard")
	public String login(@ModelAttribute("user") User user, Model model) throws Exception {
		User auser = userService.getUser(user.getUsername());
		if (auser == null || auser.equals(null) || auser.getUsername() == "" || userService.decrypt(auser.getPin()) == "" || auser.getUsername().length() == 0
				|| userService.decrypt(auser.getPin()).length() == 0) {
			return "loginerror";
		} else {
			System.out.println("Decrypted Password = "+userService.decrypt(auser.getPin()));
			if ((user.getUsername().equals(auser.getUsername())) && (user.getPin().equals(userService.decrypt(auser.getPin()))
					&& (user.getUsername().length() == auser.getUsername().length())
					&& (user.getPin().length() == userService.decrypt(auser.getPin()).length()))) {
				model.addAttribute("id", userService.getUser(user.getUsername()).getId());
				model.addAttribute("firstname", userService.getUser(user.getUsername()).getFirstName());
				model.addAttribute("lastname", userService.getUser(user.getUsername()).getLastName());
				model.addAttribute("username", userService.getUser(user.getUsername()).getUsername());
				model.addAttribute("email", userService.getUser(user.getUsername()).getEmail());
				model.addAttribute("phone", userService.getUser(user.getUsername()).getPhone());
				model.addAttribute("deposit", userService.getUser(user.getUsername()).getDeposit());
				String encryptedpin = userService.getUser(user.getUsername()).getPin();
				String decryptedpin = userService.decrypt(encryptedpin);
				model.addAttribute("pin", decryptedpin);
				model.addAttribute("agreed", userService.getUser(user.getUsername()).isAgreed());
				
				Set<UserRole> roles = userService.getUser(user.getUsername()).getUserRoles();
				for (UserRole r : roles) {
					model.addAttribute("rname", r.getRole().getRoleName());
				}
				
				Set<User> users = userService.getUsers();
				for(User u: users) {
					System.out.println("Users : " + u.getId());
				}
				model.addAttribute("user",users);
				
				Long uid = userService.getUser(user.getUsername()).getId();
				//Set<Statement> state = statementService.getStatements();
				Set<Statement> state = statementService.getByUid(uid);
				
				for(Statement s : state) {
					System.out.println("Statement UID "+s.getUid());
					System.out.println("User user ID "+uid);
				}
				model.addAttribute("state1", state);
				
				return "dashboard";
			} else if ((user.getPin().equals(userService.decrypt(auser.getPin())) == false)
					|| (user.getPin().length() != userService.decrypt(auser.getPin()).length())) {
				return "passworderror";
			} else {
				return "loginerror";
			}
		}
	}	
		
	@PostMapping("/forgotPassword")
	public String forgotPassword(@ModelAttribute("user") User user, Model model) throws Exception {
		User auser = userService.getByEmail(user.getEmail());
		if (auser == null || auser.equals(null) || auser.getEmail() == "" || auser.getEmail().length() == 0) {
			return "loginerror";
		} else {
			if ((user.getEmail().equals(auser.getEmail())) && (user.getEmail().length() == auser.getEmail().length())) {
				auser.setPin(userService.encrypt(user.getPin()));
				
				Set<UserRole> roles = new HashSet<>();
				Role role = new Role();
				role.setRoleId(45L);
				role.setRoleName("NORMAL");

				UserRole userRole = new UserRole();
				userRole.setUser(auser);
				userRole.setRole(role);

				roles.add(userRole);
				userService.updateUser(auser, roles);
				
				model.addAttribute("fname", auser.getFirstName());
				model.addAttribute("lname", auser.getLastName());
				model.addAttribute("uname", auser.getUsername());
				model.addAttribute("email", auser.getEmail());
				return "passwordchanged";
			} else {
				return "loginerror";
			}
		}
	}
	
	@PostMapping("/update")
	public String withdraw(String uname,int amountToWithdraw) throws Exception {
		User user1 = userService.getUser(uname);
		int deposit1 = user1.getDeposit();
		int balance = deposit1 - amountToWithdraw;
		System.out.println("Balance : "+balance);
		user1.setDeposit(balance);
		Set<UserRole> roles1 = user1.getUserRoles();
		userService.updateUser(user1, roles1);
		Statement state = new Statement();
		state.setBalance(balance);
		statementService.addWithdrawStatement(state, user1, amountToWithdraw);
		return "withdrawn";
	}
	
	@PostMapping("/update/deposit")
	public String deposit(String uname,int amountToDeposit) throws Exception {
		User user1 = userService.getUser(uname);
		int deposit1 = user1.getDeposit();
		int balance = deposit1 + amountToDeposit;
		System.out.println("Balance : "+balance);
		user1.setDeposit(balance);
		Set<UserRole> roles1 = user1.getUserRoles();
		userService.updateUser(user1, roles1);
		Statement state = new Statement();
		state.setBalance(balance);
		statementService.addDepositStatement(state, user1, amountToDeposit);
		return "withdrawn";
	}
	
	@GetMapping("/mobile/getname/{phoneno}")
	public ResponseEntity<String> getNameOfUser(@PathVariable("phoneno") String phone) {
		User reciever = userService.getByPhone(phone);
		if(reciever != null) {
			String name = reciever.getFirstName()+" "+reciever.getLastName();
			return ResponseEntity.ok(name);
		}
		return null;
		
	}
	
	@PostMapping("/update/mobile/transfer")
	public String mTransfer(String uname, int amountToTransfer,String phoneNumber) throws Exception {
		User sender = userService.getUser(uname);
		User reciever = userService.getByPhone(phoneNumber);
		int senderBalance = sender.getDeposit();
		int recieverBalance= reciever.getDeposit();
		System.out.println("Sender Balance : "+senderBalance);
		System.out.println("Reciever Balance : "+recieverBalance);
		Set<UserRole> senderRole = sender.getUserRoles();
		Set<UserRole> recieverRole = reciever.getUserRoles();
		int updatedSenderBalance = senderBalance - amountToTransfer;
		System.out.println("Updated Sender Balance : "+updatedSenderBalance);
		int updatedRecieverBalance = recieverBalance + amountToTransfer;
		System.out.println("Updated Reciever Balance : "+updatedRecieverBalance);
		sender.setDeposit(updatedSenderBalance);
		reciever.setDeposit(updatedRecieverBalance);
		userService.updateUser(sender, senderRole);
		userService.updateUser(reciever, recieverRole);
		Statement senderState = new Statement();
		Statement recieverState = new Statement();
		senderState.setBalance(updatedSenderBalance);
		recieverState.setBalance(updatedRecieverBalance);
		statementService.addTransferStatement(senderState, recieverState, sender, reciever, amountToTransfer);
		return "withdrawn";
	}
	
	@GetMapping("/user/getname/{name}")
	public ResponseEntity<String> getNameOfUser1(@PathVariable("name") String name) {
		User reciever = userService.getUser(name);
		if(reciever != null) {
			String name1 = reciever.getFirstName()+" "+reciever.getLastName();
			return ResponseEntity.ok(name1);
		}
		return null;
		
	}
	
	@PostMapping("/update/user/transfer")
	public String uTransfer(String uname, int amountToTransfer,String usenam) throws Exception {
		User sender = userService.getUser(uname);
		User reciever = userService.getUser(usenam);
		int senderBalance = sender.getDeposit();
		int recieverBalance= reciever.getDeposit();
		System.out.println("Sender Balance : "+senderBalance);
		System.out.println("Reciever Balance : "+recieverBalance);
		Set<UserRole> senderRole = sender.getUserRoles();
		Set<UserRole> recieverRole = reciever.getUserRoles();
		int updatedSenderBalance = senderBalance - amountToTransfer;
		System.out.println("Updated Sender Balance : "+updatedSenderBalance);
		int updatedRecieverBalance = recieverBalance + amountToTransfer;
		System.out.println("Updated Reciever Balance : "+updatedRecieverBalance);
		sender.setDeposit(updatedSenderBalance);
		reciever.setDeposit(updatedRecieverBalance);
		userService.updateUser(sender, senderRole);
		userService.updateUser(reciever, recieverRole);
		Statement senderState = new Statement();
		Statement recieverState = new Statement();
		senderState.setBalance(updatedSenderBalance);
		recieverState.setBalance(updatedRecieverBalance);
		statementService.addTransferStatement(senderState, recieverState, sender, reciever, amountToTransfer);
		return "withdrawn";
	}
	
	//get All Users
		@GetMapping("/")
		public ResponseEntity<?> getUsers(){
			return ResponseEntity.ok(userService.getUsers());
		}
		
	//Update User
		@PostMapping("/user/update")
		public String updateUser(@ModelAttribute("user") User user) throws Exception {
			Set<UserRole> roles = user.getUserRoles();
			String encryPass = userService.encrypt(user.getPin());
			user.setPin(encryPass); 
			userService.updateUser(user, roles);
			return "userupdated";
		}
		
	//Add Photo
		
		@PostMapping("/update/photo")
		public Photo addPhoto(String imageName,Long uid) {
			return photoService.addPhoto(imageName,uid);
		}
}
