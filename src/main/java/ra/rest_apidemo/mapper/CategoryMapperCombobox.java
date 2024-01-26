package ra.rest_apidemo.mapper;

import org.springframework.stereotype.Component;
import ra.rest_apidemo.dto.request.CategoryDTORequest;
import ra.rest_apidemo.dto.response.CategoryDTOResponseCombobox;
import ra.rest_apidemo.model.Category;

@Component
public class CategoryMapperCombobox implements GenericMapper<Category, CategoryDTORequest, CategoryDTOResponseCombobox>{
    @Override
    public Category mapperRequestToEntity(CategoryDTORequest categoryDTORequest) {
        return Category.builder()
                .id(categoryDTORequest.getId())
                .name(categoryDTORequest.getName())
                .description(categoryDTORequest.getDescription())
                .priority(categoryDTORequest.getPriority())
                .status(categoryDTORequest.isStatus())
                .build();
    }

    @Override
    public CategoryDTOResponseCombobox mapperEntityToResponse(Category category) {
        return new CategoryDTOResponseCombobox(
                category.getId(), category.getName()
        );
    }
}
