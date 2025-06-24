package com.zidioAdmin.AdminService.Controller;

import com.zidioAdmin.AdminService.DTO.UserInfoResponse;
import com.zidioAdmin.AdminService.DTO.UserStatusUpdateRequest;
import com.zidioAdmin.AdminService.Service.AdminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


	@Autowired
	private AdminService adminService;

	//update the user status
	@PutMapping("/user/status")
	public UserInfoResponse updateUserStatus(@RequestBody UserStatusUpdateRequest request) {
		return adminService.getpdateUserStatus(request);
	}

	//get all the registered users
	@GetMapping("/users")
	public List<UserInfoResponse> getAllUsers() {
		return adminService.getAllUsers();
	}

	//get the users by roles
	@GetMapping("/users/{role}")
	public List<UserInfoResponse> getUserByRole(@PathVariable String role) {
		return adminService.getUsersByRole(role);
	}
}