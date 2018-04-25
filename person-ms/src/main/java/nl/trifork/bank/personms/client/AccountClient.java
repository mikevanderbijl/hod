package nl.trifork.bank.personms.client;


import nl.trifork.bank.personms.model.Account;
import org.springframework.cloud.netflix.feign.*;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*account controller heeft nu /accounts mapping prefix.*/
@FeignClient("account-ms")
@RibbonClient(name="account-ms")
public interface AccountClient {
    @RequestMapping(method = RequestMethod.POST, value = "/accounts/create")
    ResponseEntity<?> create(@RequestHeader("userId") String userId, @RequestBody Account account);

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/user/{userId}")
    ResponseEntity <List<Account>> findByUser(@PathVariable("userId") Long userId);
}