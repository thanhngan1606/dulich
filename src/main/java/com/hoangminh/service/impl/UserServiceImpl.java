package com.hoangminh.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hoangminh.dto.*;
import com.hoangminh.repository.BookingRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.hoangminh.controller.HomeController;
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

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public Page<UserDTO> findAllUser(String sdt,String email,String ho_ten,Pageable pageable) {

		Page<User> page = userRepository.findAll(sdt,email,ho_ten,pageable);

		Page<UserDTO> pageUserDTO = new PageImpl<>(
			page.getContent().stream().map(user ->  {

				UserDTO userDTO = ConvertUserToDto.convertUsertoDto(user);
				return userDTO;
			}).collect(Collectors.toList()),
				page.getPageable(),
				page.getTotalElements()
		);

		return pageUserDTO;
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
		User user = userRepository.findByUsername(username);
		if(user!=null) {
			return user;
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
			if(this.bookingRepository.findBookingByUserId(id)==null || this.bookingRepository.findBookingByUserId(id).size()==0) {
				this.userRepository.deleteById(id);
				return true;
			}
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

		// Kiểm tra password không null trước khi sử dụng BCrypt
		if(userCheck.getPass() == null || userCheck.getPass().isEmpty()) {
			log.error("Password của user {} bị null hoặc empty", userCheck.getUsername());
			return false;
		}

		if(BCrypt.checkpw(user.getPassword(), userCheck.getPass())) {
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
		// Gán id thủ công
		Long maxId = userRepository.findMaxId();
		if (maxId == null) maxId = 0L;
		user.setId(maxId + 1);

		user.setUsername(newUser.getUsername());
		user.setHo_ten(newUser.getHo_ten());
		user.setPass(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(10)));
		user.setEmail(newUser.getEmail());
		user.setGioi_tinh(newUser.getGioi_tinh());
		user.setRole_id(2); // 2 = USER (khách - mặc định cho user mới)
		user.setDia_chi(newUser.getDia_chi());
		user.setSdt(newUser.getSdt());

		return this.saveUser(user);
	}

	@Override
	public boolean checkLogin() {
		return SessionUtilities.getUsername() != null && SessionUtilities.getUser() != null;
	}

	@Override
	public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
		if(SessionUtilities.getUser()!=null) {
			Long user_id = SessionUtilities.getUser().getId();

			User user = this.userRepository.findById(user_id).get();

			// Kiểm tra password không null trước khi sử dụng BCrypt
			if(user.getPass() == null || user.getPass().isEmpty()) {
				log.error("Password của user {} bị null hoặc empty", user.getUsername());
				return false;
			}

			if(BCrypt.checkpw(changePasswordDTO.getOldPassword(),user.getPass()) && changePasswordDTO.getNewPassword()!=null) {
				user.setPass(BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt(10)));
				this.userRepository.save(user);
				return true;
			}
			return false;

		}
		return false;
	}

	@Override
	public boolean updateUserByAdmin(UpdateUserDTO updateUserDTO,Long id) {

		User user = this.userRepository.findById(id).get();
		if(user!=null) {
			user.setSdt(updateUserDTO.getSdt());
			user.setUsername(updateUserDTO.getUsername());
			user.setEmail(updateUserDTO.getEmail());
			user.setDia_chi(updateUserDTO.getDia_chi());
			user.setHo_ten(updateUserDTO.getHo_ten());
			user.setGioi_tinh(updateUserDTO.getGioi_tinh());

			this.userRepository.save(user);

			return true;
		}

		return false;
	}

	@Override
	public boolean adminLogin(LoginDTO user) {
		User userCheck = this.findUserByUsername(user.getUsername());

		if(userCheck==null) {
			return false;
		}

		log.info("userCheck:{}",userCheck.getUsername());

		// Kiểm tra password không null trước khi sử dụng BCrypt
		if(userCheck.getPass() == null || userCheck.getPass().isEmpty()) {
			log.error("Password của admin {} bị null hoặc empty", userCheck.getUsername());
			return false;
		}

		if(BCrypt.checkpw(user.getPassword(), userCheck.getPass()) && (userCheck.getRole_id()==1 || userCheck.getRole_id()==3)) { // 1 = ADMIN, 3 = MANAGER

			SessionUtilities.setAdmin(ConvertUserToDto.convertUsertoDto(userCheck));

			log.info("userCheck:{}",SessionUtilities.getAdmin().getUsername());

			return true;
		}


		return false;
	}

	@Override
	public boolean checkAdminLogin() {
		return SessionUtilities.getAdmin()!=null;
	}

	@Override
	public void adminLogout() {
		SessionUtilities.setAdmin(null);
	}

	@Override
	public boolean resetPass(Long id) {
		User user = this.userRepository.findById(id).get();

		user.setPass(BCrypt.hashpw("123@123a", BCrypt.gensalt(10)));

		if(this.userRepository.save(user)!=null) {
			return true;
		}

		return false;
	}

	@Override
	public Page<User> searchUsers(String username, String email, String phone, String role, String status, LocalDate createdFrom, LocalDate createdTo, Pageable pageable) {
		Specification<User> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (username != null && !username.isEmpty())
				predicates.add(cb.like(root.get("username"), "%" + username + "%"));
			if (email != null && !email.isEmpty())
				predicates.add(cb.like(root.get("email"), "%" + email + "%"));
			if (phone != null && !phone.isEmpty())
				predicates.add(cb.like(root.get("sdt"), "%" + phone + "%"));
			if (role != null && !role.isEmpty())
				predicates.add(cb.equal(root.get("role"), role));
			if (status != null && !status.isEmpty())
				predicates.add(cb.equal(root.get("status"), status));
			if (createdFrom != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdFrom.atStartOfDay()));
			if (createdTo != null)
				predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), createdTo.atTime(23,59,59)));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return userRepository.findAll(spec, pageable);
	}

	@Override
	public long countAllUsers() {
		return userRepository.count();
	}


}
