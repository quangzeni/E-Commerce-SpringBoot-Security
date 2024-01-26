package ra.rest_apidemo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.rest_apidemo.dto.request.CategoryDTORequest;
import ra.rest_apidemo.dto.request.CategoryDTORequestUpdateStatus;
import ra.rest_apidemo.dto.response.CategoryDTOResponse;
import ra.rest_apidemo.dto.response.CategoryDTOResponseCombobox;
import ra.rest_apidemo.dto.response.CategoryDTOResponseLite;
import ra.rest_apidemo.dto.response.Message;
import ra.rest_apidemo.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTOResponse>> findAll() {
        List<CategoryDTOResponse> listCategory = categoryService.findAll();
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CategoryDTOResponse categoryDTOResponse = categoryService.findById(id);
        if (categoryDTOResponse == null) {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryDTOResponse, HttpStatus.OK);
        }
    }


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoryDTORequest categoryDTORequest) {
        boolean existName = categoryService.existsCategoryByName(categoryDTORequest);
        if (existName) {
            return new ResponseEntity<>(new Message("Tên danh mục đã tồn tại"), HttpStatus.OK);
        } else {
            CategoryDTOResponse categoryDTOResponseNew = categoryService.save(categoryDTORequest);
            return new ResponseEntity<>(categoryDTOResponseNew, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CategoryDTORequest categoryDTORequest, @PathVariable Long id) {
        CategoryDTOResponse categoryDTOResponseUpdate = categoryService.update(categoryDTORequest, id);
        if (categoryDTOResponseUpdate == null) {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryDTOResponseUpdate, HttpStatus.OK);
        }
    }

    //    Xóa category có dữ liệu product - không cho xóa nếu có product
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable Long id) {
        boolean result = categoryService.delete(id);
        if (result) {
            return new ResponseEntity<>(new Message("deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("Id: " +id+" not found or Category has Product"), HttpStatus.NOT_FOUND);
        }
    }

    //    Cập nhật category Status
    @PatchMapping("/{id}")
    public ResponseEntity<Message> updateStatus(@PathVariable Long id, @Valid @RequestBody CategoryDTORequestUpdateStatus categoryDTORequestUpdateStatus) {
        boolean result = categoryService.updateStatus(id, categoryDTORequestUpdateStatus);
        if (result) {
            return new ResponseEntity<>(new Message("Status updated"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/find")
    public ResponseEntity<List<CategoryDTOResponseLite>> find(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy
    ) {
        List<CategoryDTOResponseLite> categoryDTOResponseList = categoryService.find(direction, orderBy, page, size);
        return new ResponseEntity<>(categoryDTOResponseList, HttpStatus.OK);
    }

    @GetMapping("/combobox")
    public ResponseEntity<List<CategoryDTOResponseCombobox>> findStatusTrue() {
        List<CategoryDTOResponseCombobox> categoryDTOResponseComboboxList = categoryService.findStatusTrue();
        return new ResponseEntity(categoryDTOResponseComboboxList, HttpStatus.OK);
    }

}
