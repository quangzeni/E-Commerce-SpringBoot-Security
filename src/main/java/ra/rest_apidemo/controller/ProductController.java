package ra.rest_apidemo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ra.rest_apidemo.dto.request.ProductDTORequest;
import ra.rest_apidemo.dto.response.Message;
import ra.rest_apidemo.dto.response.ProductDTOResponse;
import ra.rest_apidemo.dto.response.ProductDTOResponseSave;
import ra.rest_apidemo.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTOResponse>> findAll() {
        List<ProductDTOResponse> listProduct = productService.findAll();
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ProductDTOResponse productDTOResponse = productService.findById(id);
        if (productDTOResponse == null) {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productDTOResponse, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProductDTORequest productDTORequest) {
        boolean existName = productService.existsProductByName(productDTORequest);
        if (existName) {
            return new ResponseEntity<>(new Message("Tên sản phẩm đã tồn tại"), HttpStatus.OK);
        } else {
            ProductDTOResponseSave productDTOResponseSave = productService.save(productDTORequest);
            return new ResponseEntity<>(productDTOResponseSave, HttpStatus.CREATED);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductDTORequest productDTORequest, @PathVariable String id) {
        ProductDTOResponse productDTOResponse = productService.update(productDTORequest, id);
        if (productDTOResponse == null) {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productDTOResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable String id) {
        boolean result = productService.delete(id);
        if (result) {
            return new ResponseEntity<>(new Message("deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductDTOResponse>> find(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction
    ) {
        List<ProductDTOResponse> productDTOResponsesList = productService.find(direction, orderBy, page, size);
        return new ResponseEntity<>(productDTOResponsesList, HttpStatus.OK);
    }

}
