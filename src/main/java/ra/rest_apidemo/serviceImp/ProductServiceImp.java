package ra.rest_apidemo.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.rest_apidemo.dto.request.ProductDTORequest;
import ra.rest_apidemo.dto.response.ProductDTOResponse;
import ra.rest_apidemo.dto.response.ProductDTOResponseSave;
import ra.rest_apidemo.mapper.ProductMapper;
import ra.rest_apidemo.model.Product;
import ra.rest_apidemo.repository.ProductRepository;
import ra.rest_apidemo.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductDTOResponse> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> productMapper.mapperEntityToResponse(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTOResponse findById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            return productMapper.mapperEntityToResponse(productOptional.get());
        }
        return null;
    }

    @Override
    public ProductDTOResponseSave save(ProductDTORequest productDTORequest) {
        Product product = productRepository.save(productMapper.mapperRequestToEntity(productDTORequest));
        return productMapper.mapperEntityToResponseSave(product);
    }

    @Override
    public ProductDTOResponse update(ProductDTORequest productDTORequest, String id) {
        boolean checkExist = productRepository.existsById(id);
        if (checkExist){
            Product productUpdate = productRepository.save(productMapper.mapperRequestToEntity(productDTORequest));
            return productMapper.mapperEntityToResponse(productUpdate);
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        boolean checkExist = productRepository.existsById(id);
        if (checkExist){
            productRepository.deleteById(id);
        }
        return checkExist;
    }

    @Override
    public List<ProductDTOResponse> find(String direction, String orderBy, int page, int size) {
        Pageable pageable;
        if (direction.equals("ASC")){
            pageable = PageRequest.of(page,size, Sort.by(orderBy).ascending());
        }else {
            pageable = PageRequest.of(page,size,Sort.by(orderBy).descending());
        }
        List<Product> productList = productRepository.findAll(pageable).getContent();
        return productList.stream().map(product ->
                productMapper.mapperEntityToResponse(product)).collect(Collectors.toList());
    }

    @Override
    public boolean existsProductByName(ProductDTORequest productDTORequest) {
        return productRepository.existsProductByName(productMapper.mapperRequestToEntity(productDTORequest).getName());
    }

}
