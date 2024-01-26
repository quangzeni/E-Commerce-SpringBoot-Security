package ra.rest_apidemo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ra.rest_apidemo.model.Category;

@AllArgsConstructor
@Data
public class ProductDTORequest {
    @Length(max = 3,message = "productId không quá 3 ký tự")
    private String id;
    @Length(max = 100, message = "Tên không dài quá 100 ký tự")
    @NotNull(message = "Không được để trống")
    private String name;
    @NotNull(message = "Không được để trống")
    private Float price;
    @NotNull(message = "Không được để trống")
    private String title;
    @NotNull(message = "Không được để trống")
    private String description;
    private Long categoryId;
    private boolean status;
}
