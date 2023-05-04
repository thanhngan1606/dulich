package com.hoangminh.controller.api.admin;

import com.hoangminh.dto.BookingDTO;
import com.hoangminh.dto.ResponseDTO;
import com.hoangminh.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAllBooking")
    public ResponseDTO getAllBooking(@RequestParam(value="trang_thai",required = false) Integer trang_thai,
                                     @RequestParam(value = "trang_thai",required = false) String ten_tour,
                                     @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,
                                     @RequestParam("pageIndex") Integer pageIndex){

        Page<BookingDTO> page = this.bookingService.findAllBooking(trang_thai,ten_tour, PageRequest.of(pageIndex,pageSize));

        return new ResponseDTO("Thành công",page.getContent());

    }

    @GetMapping("/{id}")
    public ResponseDTO getOneBooking(@PathVariable("id") Long id) {
        return new ResponseDTO("Thành công",this.bookingService.getBookingById(id));
    }

    @PutMapping("/approve/{id}")
    public ResponseDTO changeStatus(@PathVariable("id") Long id,@RequestParam("trang_thai") Integer trang_thai) {
        if(this.bookingService.approveBooking(id,trang_thai)) {
            return new ResponseDTO("Cập nhật thành công",null);
        }
        return new ResponseDTO("Cập nhật thất bại",null);
    }

}
