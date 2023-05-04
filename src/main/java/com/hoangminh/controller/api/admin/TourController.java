package com.hoangminh.controller.api.admin;

import com.hoangminh.dto.ResponseDTO;
import com.hoangminh.dto.TourDTO;
import com.hoangminh.entity.Tour;
import com.hoangminh.service.TourService;
import com.hoangminh.utilities.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
@Slf4j
@RestController
@RequestMapping("/api/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/getAllTour")
    public ResponseDTO getAllTour(@RequestParam(value="ten_tour") String ten_tour,
                                  @RequestParam(value="gia_tour_from") Long gia_tour_from,
                                  @RequestParam(value="gia_tour_to") Long gia_tour_to,
                                  @RequestParam(value="ngay_khoi_hanh") Date ngay_khoi_hanh,
                                  @RequestParam(value="loai_tour") Integer loai_tour,
                                  @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                  @RequestParam(value = "pageIndex") Integer pageIndex
                                                                                        ) {

        Page<TourDTO> page = this.tourService.findAllTour(ten_tour,gia_tour_from,gia_tour_to,ngay_khoi_hanh,loai_tour, PageRequest.of(pageIndex,pageSize));

        return new ResponseDTO("Thành công",page.getContent());

    }

    @GetMapping("/{id}")
    public ResponseDTO getOneTour(@PathVariable("id") Long id) {
        TourDTO tour = this.tourService.findTourById(id);

        if(tour!=null) {
            return new ResponseDTO("Thành công",tour);
        }
        return new ResponseDTO("Thất bại" ,null);
    }

    @PostMapping("/add")
    public ResponseDTO createTour(@RequestBody TourDTO tourDTO, @RequestParam("image")MultipartFile image) {
        String uploadDir = "/upload";
        try {
            // Lưu ảnh vào thư mục "upload"
            String fileName = image.getOriginalFilename();
            FileUploadUtil.saveFile(uploadDir, fileName, image);

            // Lưu thông tin của tour vào cơ sở dữ liệu
            tourDTO.setAnh_tour(fileName);
            return new ResponseDTO("Thành công",this.tourService.addTour(tourDTO));

        } catch (IOException  e) {
            // Xử lý exception
            log.info("Lỗi upload file: {}",e.getMessage());
        }
        return new ResponseDTO("Thêm thất bại",null);

    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateTour(@PathVariable("id") Long id,@RequestBody TourDTO tourDTO,@RequestParam("image") MultipartFile image) {

        String uploadDir = "/upload";
        try {
            // Lưu ảnh vào thư mục "upload"
            String fileName = image.getOriginalFilename();
            FileUploadUtil.saveFile(uploadDir, fileName, image);

            // Lưu thông tin của tour vào cơ sở dữ liệu
            tourDTO.setAnh_tour(fileName);
            Tour updateTour = this.tourService.updateTour(tourDTO,id);
            if(updateTour!=null) {
                return new ResponseDTO("Thành công",updateTour);
            }

        } catch (IOException  e) {
            // Xử lý exception
            log.info("Lỗi upload file: {}",e.getMessage());
        }

        return new ResponseDTO("Update thất bại",null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteTour(@PathVariable("id") Long id) {

        if(this.tourService.findTourById(id)!=null) {
            if(this.tourService.deleteTour(id)) {
                return new ResponseDTO("Xóa thành công",null);
            }

        }
        return new ResponseDTO("Xóa thất bại",null);
    }

}
