package ra.rest_apidemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.rest_apidemo.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
//    Page<User> findByEmailOrStatus(String userEmail, Pageable pageable);
    Optional<User> findByUserName(String userName);
}
