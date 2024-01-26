package ra.rest_apidemo.mapper;

import org.springframework.stereotype.Component;
import ra.rest_apidemo.dto.request.CategoryDTORequest;
import ra.rest_apidemo.dto.response.CategoryDTOResponseLite;
import ra.rest_apidemo.model.Category;

@Component
public class CategoryMapperLite implements GenericMapper<Category, CategoryDTORequest, CategoryDTOResponseLite>{
    @Override
    public Category mapperRequestToEntity(CategoryDTORequest categoryDTORequest) {
        return Category.builder()
                .id(categoryDTORequest.getId())
                .name(categoryDTORequest.getName())
                .priority(categoryDTORequest.getPriority())
                .description(categoryDTORequest.getDescription())
                .status(categoryDTORequest.isStatus())
                .build();
    }

    @Override
    public CategoryDTOResponseLite mapperEntityToResponse(Category category) {
        return new CategoryDTOResponseLite(
                category.getId(), category.getName(), category.getDescription(), category.getPriority(), category.isStatus()
        );
    }
}
