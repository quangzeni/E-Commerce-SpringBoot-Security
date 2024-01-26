package ra.rest_apidemo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Data
public class CategoryDTORequest {
    private Long id;
    @NotNull(message = "Tên danh mục không được để trống")
    @Length(max = 100, message = "Độ dài tên danh mục tối đa 100 ký tự")
    private String name;
    private String description;
    private boolean status;
    private int priority;
}
