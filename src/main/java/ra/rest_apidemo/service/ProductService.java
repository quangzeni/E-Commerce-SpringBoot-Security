package ra.rest_apidemo.service;

import ra.rest_apidemo.dto.request.ProductDTORequest;
import ra.rest_apidemo.dto.response.ProductDTOResponse;
import ra.rest_apidemo.dto.response.ProductDTOResponseSave;

import java.util.List;

public interface ProductService {
    List<ProductDTOResponse> findAll();
    ProductDTOResponse findById(String id);
    ProductDTOResponseSave save(ProductDTORequest productDTORequest);
    ProductDTOResponse update(ProductDTORequest productDTORequest, String id);
    boolean delete(String id);
    List<ProductDTOResponse> find(String direction, String orderBy, int page, int size);
    boolean existsProductByName(ProductDTORequest productDTORequest);
}
