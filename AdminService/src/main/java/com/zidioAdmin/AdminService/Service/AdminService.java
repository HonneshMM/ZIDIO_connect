package com.zidioAdmin.AdminService.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zidioAdmin.AdminService.AdminServiceApplication;
import com.zidioAdmin.AdminService.DTO.UserInfoResponse;
import com.zidioAdmin.AdminService.DTO.UserStatusUpdateRequest;
import com.zidioAdmin.AdminService.Entites.UserInfo;
import com.zidioAdmin.AdminService.Repository.UserInfoRepository;

@Service
public class AdminService {

	@Autowired
    private RestTemplate restTemplate;

    private final String AUTH_SERVICE_BASE_URL = "http://localhost:8002/auth";

    public UserInfoResponse getpdateUserStatus(UserStatusUpdateRequest request) {
        String url = AUTH_SERVICE_BASE_URL + "/update-status";

        HttpEntity<UserStatusUpdateRequest> entity = new HttpEntity<>(request);
        ResponseEntity<UserInfoResponse> response = restTemplate.exchange(
                url, HttpMethod.PUT, entity, UserInfoResponse.class);

        return response.getBody();
    }

    public List<UserInfoResponse> getAllUsers() {
        String url = AUTH_SERVICE_BASE_URL + "/all-users";

        ResponseEntity<UserInfoResponse[]> response = restTemplate.getForEntity(url, UserInfoResponse[].class);
        return Arrays.asList(response.getBody());
    }

    public List<UserInfoResponse> getUsersByRole(String role) {
        String url = AUTH_SERVICE_BASE_URL + "/users-by-role/" + role.toUpperCase();

        ResponseEntity<UserInfoResponse[]> response = restTemplate.getForEntity(url, UserInfoResponse[].class);
        return Arrays.asList(response.getBody());
    }

	public UserInfoResponse toResponse(UserInfo user) {
		return new UserInfoResponse(user.getEmail(), user.getRole(), user.isIsactive());
	}
}