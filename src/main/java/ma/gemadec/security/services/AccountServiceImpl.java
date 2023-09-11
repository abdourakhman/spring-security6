package ma.gemadec.security.services;

import jakarta.transaction.Transactional;
import ma.gemadec.security.entities.AppRole;
import ma.gemadec.security.entities.AppUser;
import ma.gemadec.security.repositories.AppRoleRepository;
import ma.gemadec.security.repositories.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;

    public AccountServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findUserByUsername(username);
    }

    @Override
    public AppUser addUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findUserByUsername(username);
        AppRole role = appRoleRepository.findRoleByRoleName(roleName);
        user.getRoles().add(role);
    }
}
