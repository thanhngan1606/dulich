package com.hoangminh.controller.api.admin;

import com.hoangminh.dto.ResponseDTO;
import com.hoangminh.service.BookingService;
import com.hoangminh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllBooking")
    public ResponseDTO<Object> getAllBooking(
            @RequestParam(value = "trang_thai", required = false) String trang_thai,
            @RequestParam(value = "ten_tour", required = false) String ten_tour,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageIndex") Integer pageIndex
    ) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        var page = bookingService.findAllBooking(trang_thai, ten_tour, PageRequest.of(pageIndex, pageSize));
        return new ResponseDTO<>(true, "Thành công", page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseDTO<Object> getOneBooking(@PathVariable("id") Long id) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        var booking = bookingService.getBookingById(id);
        if (booking != null) {
            return new ResponseDTO<>(true, "Thành công", booking);
        }
        return new ResponseDTO<>(false, "Không tìm thấy booking", null);
    }

    @PostMapping("")
    public ResponseDTO<Object> addBooking(@RequestBody com.hoangminh.dto.BookingDTO bookingDTO) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        boolean result = bookingService.addNewBooking(bookingDTO);
        if (result) {
            return new ResponseDTO<>(true, "Thêm booking thành công", null);
        }
        return new ResponseDTO<>(false, "Thêm booking thất bại", null);
    }

    @PutMapping("/approve/{id}")
    public ResponseDTO<Object> approveBooking(@PathVariable("id") Long id, @RequestParam("trang_thai") String trang_thai) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        boolean result = bookingService.approveBooking(id, trang_thai);
        return new ResponseDTO<>(result, result ? "Cập nhật thành công" : "Cập nhật thất bại", null);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO<Object> updateBooking(@PathVariable("id") Long id, @RequestBody com.hoangminh.dto.BookingDTO bookingDTO) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        boolean result = bookingService.updateBooking(id, bookingDTO);
        if (result) {
            return new ResponseDTO<>(true, "Cập nhật booking thành công", null);
        }
        return new ResponseDTO<>(false, "Cập nhật booking thất bại", null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO<Object> deleteBooking(@PathVariable("id") Long id) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        boolean result = bookingService.deleteBooking(id);
        return new ResponseDTO<>(result, result ? "Xóa thành công" : "Chỉ xóa được booking đã hoàn thành!", null);
    }

    @GetMapping("/detail/{id}")
    public ResponseDTO<Object> getBookingDetail(@PathVariable("id") Long id) {
        if (!userService.checkAdminLogin()) {
            return new ResponseDTO<>(false, "Không có quyền truy cập", null);
        }
        var detail = bookingService.getBookingDetailById(id);
        if (detail != null) {
            return new ResponseDTO<>(true, "Thành công", detail);
        }
        return new ResponseDTO<>(false, "Không tìm thấy booking", null);
    }
} 