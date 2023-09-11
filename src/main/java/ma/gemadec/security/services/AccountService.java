package ma.gemadec.security.services;

import ma.gemadec.security.entities.AppRole;
import ma.gemadec.security.entities.AppUser;

import java.util.List;

public interface AccountService {
    List<AppUser> listUsers();
    AppUser loadUserByUsername(String username);
    AppUser addUser(AppUser appUser);
    AppRole addRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
}
