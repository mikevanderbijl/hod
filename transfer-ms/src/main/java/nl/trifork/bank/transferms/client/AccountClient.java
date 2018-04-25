package nl.trifork.bank.transferms.client;


import nl.trifork.bank.transferms.model.Account;
import nl.trifork.bank.transferms.model.Transfer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*account controller heeft nu /accounts mapping prefix.*/
@FeignClient("account-ms")
@RequestMapping("accounts")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    String getTestString();

    @RequestMapping(method = RequestMethod.GET, value = "/account/{key}")
    ResponseEntity<Account> findByKey(@PathVariable("key") String key);


    @RequestMapping(method = RequestMethod.PUT, value = "/transfer")
    ResponseEntity<?> executeTransfer(@RequestHeader("If-Match") String version,
                                      @RequestBody Transfer transfer);


}


