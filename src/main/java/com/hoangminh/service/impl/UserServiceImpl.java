package com.hoangminh.service.impl;

import java.util.List;
import java.util.Optional;

import com.hoangminh.dto.ChangePasswordDTO;
import com.hoangminh.dto.UpdateUserDTO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hoangminh.controller.HomeController;
import com.hoangminh.dto.LoginDTO;
import com.hoangminh.dto.RegisterDTO;
import com.hoangminh.entity.User;
import com.hoangminh.repository.UserRepository;
import com.hoangminh.service.UserService;
import com.hoangminh.utilities.ConvertUserToDto;
import com.hoangminh.utilities.SessionUtilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public boolean updateUser(UpdateUserDTO updateUserDTO) {
		if(SessionUtilities.getUser()!=null) {
			Long user_id = SessionUtilities.getUser().getId();

			User user = this.userRepository.findById(user_id).get();

			user.setSdt(updateUserDTO.getSdt());
			user.setUsername(updateUserDTO.getUsername());
			user.setEmail(updateUserDTO.getEmail());
			user.setDia_chi(updateUserDTO.getDia_chi());
			user.setHo_ten(updateUserDTO.getHo_ten());
			user.setGioi_tinh(updateUserDTO.getGioi_tinh());

			this.userRepository.save(user);

			SessionUtilities.setUser(ConvertUserToDto.convertUsertoDto(user));

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

	@Override
	public boolean login(LoginDTO user) {

		User userCheck = this.findUserByUsername(user.getUsername());

		if(userCheck==null) {
			return false;
		}

		log.info("userCheck:{}",userCheck.getUsername());

		if(BCrypt.checkpw(user.getPassword(), userCheck.getPassword())) {
			SessionUtilities.setUsername(userCheck.getUsername());
			SessionUtilities.setUser(ConvertUserToDto.convertUsertoDto(userCheck));

			log.info("userCheck:{}",SessionUtilities.getUsername());
			return true;
		}


		return false;
	}

	@Override
	public boolean register(RegisterDTO newUser) {

		User userCheckByUserName = this.findUserByUsername(newUser.getUsername());
		User userCheckByEmail = this.userRepository.getUserByEmail(newUser.getEmail());
		if(userCheckByUserName!=null || userCheckByEmail!=null) {
			return false;
		}

		User user= new User();
		user.setUsername(newUser.getUsername());
		user.setHo_ten(newUser.getHo_ten());
		user.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(10)));
		user.setEmail(newUser.getEmail());
		user.setGioi_tinh(newUser.getGioi_tinh());
		user.setRole(1);
		user.setDia_chi(newUser.getDia_chi());
		user.setSdt(newUser.getSdt());


		return this.saveUser(user);
	}

	@Override
	public boolean checkLogin() {
		return SessionUtilities.getUsername()!=null;
	}

	@Override
	public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
		if(SessionUtilities.getUser()!=null) {
			Long user_id = SessionUtilities.getUser().getId();

			User user = this.userRepository.findById(user_id).get();

			if(BCrypt.checkpw(changePasswordDTO.getOldPassword(),user.getPassword()) && changePasswordDTO.getNewPassword()!=null) {
				user.setPassword(BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt(10)));
				this.userRepository.save(user);
				return true;
			}
			return false;

		}
		return false;
	}


}
