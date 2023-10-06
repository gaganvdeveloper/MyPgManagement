package com.gsoft.MyPgManagementApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsoft.MyPgManagementApplication.dto.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByOtp(int otp);
	User findByEmail(String email);
	User findByPhoneAndPassword(long phone, String password);
	User findByEmailAndPassword(String email, String password);
}
