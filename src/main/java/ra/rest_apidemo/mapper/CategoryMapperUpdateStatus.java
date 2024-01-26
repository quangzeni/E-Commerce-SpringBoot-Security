package ra.rest_apidemo.mapper;

import ra.rest_apidemo.dto.request.CategoryDTORequestUpdateStatus;
import ra.rest_apidemo.dto.response.CategoryDTOResponseLite;
import ra.rest_apidemo.model.Category;

public class CategoryMapperUpdateStatus implements GenericMapper<Category, CategoryDTORequestUpdateStatus, CategoryDTOResponseLite>{
    @Override
    public Category mapperRequestToEntity(CategoryDTORequestUpdateStatus categoryMapperRequestUpdateStatus) {
        return Category.builder()
                .id(categoryMapperRequestUpdateStatus.getId())
                .status(categoryMapperRequestUpdateStatus.isStatus())
                .build();
    }

    @Override
    public CategoryDTOResponseLite mapperEntityToResponse(Category category) {
        return new CategoryDTOResponseLite(
                category.getId(), category.getName(), category.getDescription(), category.getPriority(),
                category.isStatus()
        );
    }
}
