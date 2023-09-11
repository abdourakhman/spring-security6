package ma.gemadec.security.repositories;

import ma.gemadec.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findUserByUsername(String username);
}
