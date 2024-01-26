package ra.rest_apidemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @Column(name = "product_id", columnDefinition = "varchar(3)", unique = true, nullable = false)
    private String id;

    @Column(name = "product_name", columnDefinition = "varchar(100)", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "title", columnDefinition = "varchar(200)", nullable = false)
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date updated;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
