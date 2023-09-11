package ma.gemadec.security.configuration;

import ma.gemadec.security.entities.AppRole;
import ma.gemadec.security.entities.AppUser;
import ma.gemadec.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.BeanProperty;

@Configuration
public class initConf {
    private  final AccountService accountService;

    public initConf(AccountService accountService) {
        this.accountService = accountService;
    }
    @Bean
    CommandLineRunner init(){
        return args -> {
            accountService.addUser(new AppUser(null,"user1","1234",null));
            accountService.addUser(new AppUser(null,"manager1","1234",null));
            accountService.addUser(new AppUser(null,"user2","1234",null));
            accountService.addUser(new AppUser(null,"admin1","1234",null));

            accountService.addRole(new AppRole(null,"user"));
            accountService.addRole(new AppRole(null,"manager"));
            accountService.addRole(new AppRole(null,"admin"));

            accountService.addRoleToUser("user1","user");
            accountService.addRoleToUser("user2","user");
            accountService.addRoleToUser("manager1","user");
            accountService.addRoleToUser("manager1","manger");
            accountService.addRoleToUser("admin1","user");
            accountService.addRoleToUser("admin1","admin");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
