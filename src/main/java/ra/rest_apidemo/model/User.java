package ra.rest_apidemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "varchar(100)")
    private String id;
    @Column(name = "user_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String userName;
    @Column(name = "email", columnDefinition = "varchar(100)",unique = true,nullable = false)
    private String email;
    @Column(name = "password",columnDefinition = "varchar(100)", nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(100)")
    private String fullName;
    private boolean sex;
    @Column(columnDefinition = "varchar(50)")
    private String phone;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "user_status")
    private boolean status;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> listRoles;
}
