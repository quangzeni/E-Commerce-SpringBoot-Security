package ra.rest_apidemo.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.rest_apidemo.dto.request.CategoryDTORequest;
import ra.rest_apidemo.dto.request.CategoryDTORequestUpdateStatus;
import ra.rest_apidemo.dto.response.CategoryDTOResponse;
import ra.rest_apidemo.dto.response.CategoryDTOResponseCombobox;
import ra.rest_apidemo.dto.response.CategoryDTOResponseLite;
import ra.rest_apidemo.mapper.CategoryMapper;
import ra.rest_apidemo.mapper.CategoryMapperCombobox;
import ra.rest_apidemo.mapper.CategoryMapperLite;
import ra.rest_apidemo.model.Category;
import ra.rest_apidemo.repository.CategoryRepository;
import ra.rest_apidemo.repository.ProductRepository;
import ra.rest_apidemo.service.CategoryService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryMapperLite categoryMapperLite;
    @Autowired
    private CategoryMapperCombobox categoryMapperCombobox;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CategoryDTOResponse> findAll() {
        List<Category> listCategory = categoryRepository.findAll();
        return listCategory.stream().map(category -> categoryMapper.mapperEntityToResponse(category))
                .collect(Collectors.toList());
    }

    //    @Override
//    public CategoryDTOResponse findById(int id) {
//        Optional<Category> optionalCategory = categoryRepository.findById(id);
//        if (optionalCategory.isPresent()){
//            return categoryMapper.mapperEntityToResponse(optionalCategory.get());
//        }
//        return null;
//    }
    @Override
    public CategoryDTOResponse findById(Long id) {
        //id không tồn tại --> catalog = null
        Optional<Category> optCatalog = categoryRepository.findById(id);
        if (optCatalog.isPresent()) {
            return categoryMapper.mapperEntityToResponse(optCatalog.get());
        }
        return null;
    }


    @Override
    public CategoryDTOResponse save(CategoryDTORequest categoryDTORequest) {
        Category category = categoryRepository.save(categoryMapper.mapperRequestToEntity(categoryDTORequest));
        return categoryMapper.mapperEntityToResponse(category);
    }

    @Override
    public CategoryDTOResponse update(CategoryDTORequest categoryDTORequest, Long id) {
        boolean checkExist = categoryRepository.existsById(id);
        if (checkExist) {
            Category category = categoryRepository.save(categoryMapper.mapperRequestToEntity(categoryDTORequest));
            return categoryMapper.mapperEntityToResponse(category);
        }
        return null;
    }

    //    Xóa category có dữ liệu product - không cho xóa nếu có product
    @Override
    public boolean delete(Long id) {
        boolean checkExist = categoryRepository.existsById(id);
        boolean existProduct = productRepository.existsProductsByCategory_Id(id);
        if (checkExist && !existProduct){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //    Cập nhật category Status
    public boolean updateStatus(Long id, CategoryDTORequestUpdateStatus categoryDTORequestUpdateStatus) {
        Optional<Category> optCatalog = categoryRepository.findById(id);
        if (optCatalog.isPresent()) {
            Category categoryUpdate = optCatalog.get();
            categoryUpdate.setStatus(categoryDTORequestUpdateStatus.isStatus());
            categoryRepository.save(categoryUpdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CategoryDTOResponseCombobox> findStatusTrue() {
        List<Category> categoryList = categoryRepository.findByStatusTrue();
        return categoryList.stream().map(category -> categoryMapperCombobox.mapperEntityToResponse(category))
                .collect(Collectors.toList());
    }


    @Override
    public List<CategoryDTOResponseLite> find(String direction, String orderBy, int page, int size) {
        Pageable pageable;
        if (direction.equals("ASC")) {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        }
        List<Category> categoryList = categoryRepository.findAll(pageable).getContent();
        return categoryList.stream().map(category ->
                categoryMapperLite.mapperEntityToResponse(category)).collect(Collectors.toList());
    }

    @Override
    public boolean existsCategoryByName(CategoryDTORequest categoryDTORequest) {
        return categoryRepository.existsCategoryByName(categoryMapper.mapperRequestToEntity(categoryDTORequest).getName());
    }
}
