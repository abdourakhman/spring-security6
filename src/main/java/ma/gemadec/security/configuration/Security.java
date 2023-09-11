package ma.gemadec.security.configuration;

import ma.gemadec.security.entities.AppRole;
import ma.gemadec.security.entities.AppUser;
import ma.gemadec.security.services.AccountService;
import ma.gemadec.security.services.UserDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Security {
    private  UserDetailsServiceImpl userDetailsServiceImpl;
    public Security(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    //Provider qui se charge d'indiquer à spring où chercher les utilisateurs
    //@Bean
    /*InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        //les utilisateur sont stockés en mémoire
        return new InMemoryUserDetailsManager(
                //{noop} signifie que le mot de passe est en claire
                User.withUsername("user1").password("{noop}1234").roles("USER").build(),
                //le mot de passe doit être hashé avec un passwordEncoder, ici Bcrypt est utilisé
                User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("user3").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER","ADMIN").build()
        );
    }
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //authentification par formulaire
        http.formLogin();
        //les requêtes commençant par h2-console sont permises
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
        http.headers().frameOptions().disable();
        //les requêtes commençant par users requierent le role admin
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/users/**")).hasRole("ADMIN");
        //les autres requêtes sont authentifiées
        http.authorizeHttpRequests().anyRequest().authenticated();
        //redirection vers /notAuthorized en cas de statut forbidden (403)
        http.exceptionHandling().accessDeniedPage("/notAuthorized");
        //Provider qui se charge d'indiquer à spring où chercher les utilisateurs
        http.userDetailsService(userDetailsServiceImpl);
       return http.build();
    }
    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addUser(new AppUser(null,"user1","1234",null));
            accountService.addUser(new AppUser(null,"manager1","1234",null));
            accountService.addUser(new AppUser(null,"user2","1234",null));
            accountService.addUser(new AppUser(null,"admin1","1234",null));

            accountService.addRole(new AppRole(null,"USER"));
            accountService.addRole(new AppRole(null,"MANAGER"));
            accountService.addRole(new AppRole(null,"ADMIN"));

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("manager1","USER");
            accountService.addRoleToUser("manager1","MANAGER");
            accountService.addRoleToUser("admin1","USER");
            accountService.addRoleToUser("admin1","ADMIN");
        };
    }
}
