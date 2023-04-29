package com.hoangminh.service;

import java.util.List;
import java.util.Optional;

import com.hoangminh.dto.ChangePasswordDTO;
import com.hoangminh.dto.UpdateUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoangminh.dto.LoginDTO;
import com.hoangminh.dto.RegisterDTO;
import com.hoangminh.entity.User;

public interface UserService {
	public Page<User> findAllUser(Pageable pageable);
	public User findUserById(Long id);
	public User findUserByUsername(String username);
	public boolean saveUser(User user);
	public boolean login(LoginDTO user);
	public boolean register(RegisterDTO user);
	public boolean updateUser(UpdateUserDTO updateUserDTO);
	public boolean deleteUserById(Long id);
	public boolean checkLogin();
	public boolean changePassword(ChangePasswordDTO changePasswordDTO);
}
