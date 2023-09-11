package ma.gemadec.security.services;

import jakarta.transaction.Transactional;
import ma.gemadec.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private   AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user= accountService.loadUserByUsername(username);
        if(user ==null) throw  new UsernameNotFoundException("user not found");
        String[] roles = user.getRoles().stream().map(role -> role.getRoleName()).toArray(String[]::new);
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).roles(roles).build();
        return userDetails;
    }
}
