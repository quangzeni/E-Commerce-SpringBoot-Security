package ra.rest_apidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.rest_apidemo.model.ERoles;
import ra.rest_apidemo.model.Roles;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(ERoles eRoles);
}
