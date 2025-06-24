package com.zidioAdmin.AdminService.DTO;

public class UserInfoResponse{
	
    public String email;
    public String role;
    public boolean isActive;

    public UserInfoResponse(String email, String role, Boolean isActive) {
    	this.email = email;
    	this.role = role;
    	this.isActive = isActive;
    }
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}  
    
    
}