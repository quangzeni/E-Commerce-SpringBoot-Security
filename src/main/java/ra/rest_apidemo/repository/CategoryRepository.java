package ra.rest_apidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.rest_apidemo.dto.response.CategoryDTOResponseCombobox;
import ra.rest_apidemo.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsProductsById(Long id);
    List<Category> findByStatusTrue();
    boolean existsCategoryByName(String name);
}
