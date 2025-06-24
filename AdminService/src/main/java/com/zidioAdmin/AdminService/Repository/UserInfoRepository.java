package com.zidioAdmin.AdminService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zidioAdmin.AdminService.Entites.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByEmail(String email);
	List<UserInfo> findByRole(String role);
}
