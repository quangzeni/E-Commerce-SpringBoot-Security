package ra.rest_apidemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ProductDTOResponseSave {
    private String id;
    private String name;
    private Float price;
    private String title;
    private String description;
    private Date created;
    private boolean status;
}
