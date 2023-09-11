package ma.gemadec.security.repositories;

import ma.gemadec.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findRoleByRoleName(String roleName);
}
