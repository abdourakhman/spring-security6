package ma.gemadec.security.controllers;

import ma.gemadec.security.entities.AppRole;
import ma.gemadec.security.services.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRoleController {
    private final AccountService accountService;

    public AppRoleController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/newRole")
    public AppRole addNewRole(@RequestBody AppRole appRole){
       return accountService.addRole(appRole);
    }
}
