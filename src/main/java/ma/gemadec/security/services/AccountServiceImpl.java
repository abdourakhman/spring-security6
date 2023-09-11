package ma.gemadec.security.services;

import jakarta.transaction.Transactional;
import ma.gemadec.security.entities.AppRole;
import ma.gemadec.security.entities.AppUser;
import ma.gemadec.security.repositories.AppRoleRepository;
import ma.gemadec.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private  AppRoleRepository appRoleRepository;
    private  AppUserRepository appUserRepository;
    private  PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
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
        if(appUserRepository.findUserByUsername(appUser.getUsername()) !=null) throw new RuntimeException("User already exists");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
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
