package ra.rest_apidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.rest_apidemo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    boolean existsProductsByCategory_Id(Long id);
    boolean existsProductByName(String name);
}
