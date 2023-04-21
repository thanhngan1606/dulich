package com.hoangminh.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hoangminh.entity.User;
import com.hoangminh.repository.UserRepository;
import com.hoangminh.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Page<User> findAllUser(Pageable pageable) {
		
		Page<User> page = userRepository.findAll(pageable);
		
		return page;
	}
	
	@Override
	public User findUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public boolean saveUser(User user) {
		if(this.userRepository.save(user)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUser(User newUser, Long id) {
		Optional<User> user = this.userRepository.findById(id);
		if(user.isPresent()) {
			User updatedUser = newUser;
			updatedUser.setId(id);
			this.userRepository.save(updatedUser);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUserById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		if(user.isPresent()) {
			this.userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	

}
