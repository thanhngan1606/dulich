package com.hoangminh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoangminh.entity.User;

public interface UserService {
	public Page<User> findAllUser(Pageable pageable);
	public User findUserById(Long id);
	public User findUserByUsername(String username);
	public boolean saveUser(User user);
	public boolean updateUser(User user,Long id);
	public boolean deleteUserById(Long id);
}
