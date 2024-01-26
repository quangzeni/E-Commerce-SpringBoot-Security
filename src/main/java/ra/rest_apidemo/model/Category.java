package ra.rest_apidemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "category_name",columnDefinition = "varchar(100)",unique = true,nullable = false)
    private String name;
    @Column(name = "priority")
    private int priority;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "category_status", columnDefinition = "boolean default true")
    private boolean status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date created;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;
}
