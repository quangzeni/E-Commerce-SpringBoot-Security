package ra.rest_apidemo.service;

import ra.rest_apidemo.dto.request.CategoryDTORequest;
import ra.rest_apidemo.dto.request.CategoryDTORequestUpdateStatus;
import ra.rest_apidemo.dto.response.CategoryDTOResponse;
import ra.rest_apidemo.dto.response.CategoryDTOResponseCombobox;
import ra.rest_apidemo.dto.response.CategoryDTOResponseLite;

import java.util.List;

public interface CategoryService {
    List<CategoryDTOResponse> findAll();

    CategoryDTOResponse findById(Long id);

    CategoryDTOResponse save(CategoryDTORequest categoryDTORequest);

    CategoryDTOResponse update(CategoryDTORequest categoryDTORequest, Long id);

    boolean delete(Long id);

    boolean updateStatus(Long id, CategoryDTORequestUpdateStatus categoryDTORequestUpdateStatus);

    List<CategoryDTOResponseCombobox> findStatusTrue();

    List<CategoryDTOResponseLite> find(String direction, String orderBy, int page, int size);
    boolean existsCategoryByName (CategoryDTORequest categoryDTORequest);
}
