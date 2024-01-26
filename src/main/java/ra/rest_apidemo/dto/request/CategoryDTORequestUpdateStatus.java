package ra.rest_apidemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryDTORequestUpdateStatus {
    private Long id;
    private boolean status;
}
