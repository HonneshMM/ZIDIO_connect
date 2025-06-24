package com.zidioAdmin.AdminService.DTO;

public class UserStatusUpdateRequest{
	
    public String email;
    public boolean isActive;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
    
}
