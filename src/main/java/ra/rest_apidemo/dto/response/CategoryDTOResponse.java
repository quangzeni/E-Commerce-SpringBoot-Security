package ra.rest_apidemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ra.rest_apidemo.model.Product;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDTOResponse {
    private Long id;
    private String name;
    private String description;
    private int priority;
    private boolean status;
    private List<String> productName;
}
