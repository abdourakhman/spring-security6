package ma.gemadec.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Security {
    private final PasswordEncoder passwordEncoder;

    public Security(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Provider qui se charge d'indiquer à spring où chercher les utilisateurs
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        //les utilisateur sont stockés en mémoire
        return new InMemoryUserDetailsManager(
                //{noop} signifie que le mot de passe est en claire
                User.withUsername("user1").password("{noop}1234").roles("USER").build(),
                //le mot de passe doit être hashé avec un passwordEncoder, ici Bcrypt est utilisé
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user3").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
        );
    }
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
       return http.build();
    }

}
