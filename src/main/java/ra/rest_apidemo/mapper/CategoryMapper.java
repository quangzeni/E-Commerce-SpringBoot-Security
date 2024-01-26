package ra.rest_apidemo.mapper;

import org.springframework.stereotype.Component;
import ra.rest_apidemo.dto.request.CategoryDTORequest;
import ra.rest_apidemo.dto.response.CategoryDTOResponse;
import ra.rest_apidemo.dto.response.CategoryDTOResponseCombobox;
import ra.rest_apidemo.model.Category;
import ra.rest_apidemo.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper implements GenericMapper<Category,CategoryDTORequest,CategoryDTOResponse>{
    @Override
    public Category mapperRequestToEntity(CategoryDTORequest categoryDTORequest) {
        return Category.builder().id(categoryDTORequest.getId())
                .name(categoryDTORequest.getName())
                .description(categoryDTORequest.getDescription())
                .priority(categoryDTORequest.getPriority())
                .status(categoryDTORequest.isStatus()).build();
    }

    @Override
    public CategoryDTOResponse mapperEntityToResponse(Category category) {
        List<String> productNames = new ArrayList<>();
        if (category.getProducts() != null){
            productNames = category.getProducts().stream().map(Product::getName).collect(Collectors.toList());
        }
        return new CategoryDTOResponse(
                category.getId(),category.getName(),
                category.getDescription(),category.getPriority(),
                category.isStatus(),productNames
        );
    }

}