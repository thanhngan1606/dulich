package com.hoangminh.controller.api.admin;

import com.hoangminh.dto.ChangePasswordDTO;
import com.hoangminh.dto.ResponseDTO;
import com.hoangminh.dto.UpdateUserDTO;
import com.hoangminh.dto.UserDTO;
import com.hoangminh.entity.User;
import com.hoangminh.service.UserService;
import com.hoangminh.utilities.ConvertUserToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseDTO<Object> getAllUser(
            @RequestParam(value = "sdt",required = false) String sdt,
            @RequestParam(value = "email",required = false) String email,
            @RequestParam(value = "ho_ten",required = false) String ho_ten,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
            @RequestParam("pageIndex") Integer pageIndex
            ) {
        if(!this.userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }

        Page<UserDTO> page = this.userService.findAllUser(sdt,email,ho_ten, PageRequest.of(pageIndex,pageSize));

        return new ResponseDTO<>(true, "Thành công", page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseDTO<Object> getOneUser(@PathVariable("id") Long id) {

        if(!this.userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }

        if(this.userService.findUserById(id)!=null) {
            return new ResponseDTO<>(true, "Thành công", ConvertUserToDto.convertUsertoDto(this.userService.findUserById(id)));
        }
        return new ResponseDTO<>(false, "Thất bại", null);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO<Object> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserDTO updateUserDTO) {

        if(!this.userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }

        User user = this.userService.findUserById(id);

        if(user!=null) {
            if(this.userService.updateUserByAdmin(updateUserDTO,id)) {
                return new ResponseDTO<>(true, "Cập nhật thành công", this.userService.findUserById(id));
            }
        }
        return new ResponseDTO<>(false, "Cập nhật thất bại", null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO<Object> deleteUser(@PathVariable("id") Long id){


        if(!this.userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }

        User user = this.userService.findUserById(id);
        if(user!=null) {

            if(this.userService.deleteUserById(id)) {
                return new ResponseDTO<>(true, "Xóa thành công", null);
            }
        }

        return new ResponseDTO<>(false, "Không thể xóa người dùng này", null);
    }

    @PutMapping("/update/resetPass/{id}")
    public ResponseDTO<Object> resetPass(@PathVariable("id") Long id) {

        if(!this.userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }

        if(this.userService.resetPass(id)) {
            return new ResponseDTO<>(true, "Khôi phục mật khẩu mặc định thành công", null);
        }
        return new ResponseDTO<>(false, "Khôi phục mật khẩu mặc định thất bại", null);

    }
}
