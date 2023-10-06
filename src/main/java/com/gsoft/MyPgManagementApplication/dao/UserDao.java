package com.gsoft.MyPgManagementApplication.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gsoft.MyPgManagementApplication.dto.User;
import com.gsoft.MyPgManagementApplication.repository.UserRepository;

@Repository
public class UserDao {

	@Autowired
	UserRepository repository;

	public User saveUser(User user) {
		return repository.save(user);
	}

	public User updateUser(User user) {
		return repository.save(user);
	}

	public User findUserByOtp(int otp) {
		return repository.findByOtp(otp);
	}

	public User findUserByEmail(String email) {
		return repository.findByEmail(email);
	}

	public Optional<User> findUserById(int id) {
		return repository.findById(id);
	}

	public User findUserByEmailAndPassword(String email, String password) {
		return repository.findByEmailAndPassword(email, password);
	}

	public User findUserByPhoneAndPassword(long phone, String password) {
		return repository.findByPhoneAndPassword(phone, password);
	}
}
