package ra.rest_apidemo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.rest_apidemo.dto.request.ProductDTORequest;
import ra.rest_apidemo.dto.response.ProductDTOResponse;
import ra.rest_apidemo.dto.response.ProductDTOResponseSave;
import ra.rest_apidemo.model.Product;
import ra.rest_apidemo.repository.CategoryRepository;

@Component
public class ProductMapper implements GenericMapper<Product, ProductDTORequest, ProductDTOResponse>{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Product mapperRequestToEntity(ProductDTORequest productDTORequest) {
        return Product.builder().id(productDTORequest.getId())
                .name(productDTORequest.getName())
                .price(productDTORequest.getPrice())
                .title(productDTORequest.getTitle())
                .description(productDTORequest.getDescription())
                .category(categoryRepository.findById(productDTORequest.getCategoryId()).get())
                .status(productDTORequest.isStatus())
                .build();
    }

    @Override
    public ProductDTOResponse mapperEntityToResponse(Product product) {
        return new ProductDTOResponse(product.getId(), product.getName(), product.getPrice(), product.getTitle()
                , product.getDescription(),product.getCategory().getId(),product.getCategory().getName()
                , product.isStatus());
    }


    public ProductDTOResponseSave mapperEntityToResponseSave(Product product){
        return new ProductDTOResponseSave(product.getId(), product.getName(), product.getPrice(),
                product.getTitle(), product.getDescription(), product.getCreated(),
                product.isStatus()) ;
    }

}
