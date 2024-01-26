package ra.rest_apidemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryDTOResponseLite {
    private Long id;
    private String name;
    private String description;
    private int priority;
    private boolean status;
}
