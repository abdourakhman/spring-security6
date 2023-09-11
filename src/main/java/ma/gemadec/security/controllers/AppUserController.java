package ma.gemadec.security.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.gemadec.security.entities.AppUser;
import ma.gemadec.security.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController {
    private final AccountService accountService;

    public AppUserController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/user/{username}")
    public AppUser getUser(@PathVariable String username){
        return accountService.loadUserByUsername(username);
    }

    @GetMapping("/users")
    public List<AppUser> getAllUsers(){
       return accountService.listUsers();
    }

    @PostMapping("/user")
    public AppUser createNewUser(@RequestBody AppUser appUser){
        return accountService.addUser(appUser);
    }
    
    @PostMapping("/attributeRole")
    public void addRoleToUser(@RequestBody RoleToUserForm roleToUserForm){
        accountService.addRoleToUser(roleToUserForm.getUsername(), roleToUserForm.getRoleName());
    }
}
@Data @AllArgsConstructor
@NoArgsConstructor
class RoleToUserForm{
    private String username;
    private String roleName;
}