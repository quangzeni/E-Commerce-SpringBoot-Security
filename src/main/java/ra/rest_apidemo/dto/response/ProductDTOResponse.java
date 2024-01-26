package ra.rest_apidemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ra.rest_apidemo.model.Category;

@AllArgsConstructor
@Data
public class ProductDTOResponse {
    private String id;
    private String name;
    private Float price;
    private String title;
    private String description;
    private Long categoryId;
    private String categoryName;
    private boolean status;
}
