package nl.trifork.bank.personms.client;
import nl.trifork.bank.personms.model.Transfer;
import nl.trifork.bank.personms.model.Account;
import org.springframework.cloud.netflix.feign.*;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@FeignClient("transfer-ms")
@RibbonClient(name="transfer-ms")
public interface TransferClient {
    @RequestMapping(method = RequestMethod.POST, value = "transfers/")
    ResponseEntity<String> createTransfer(@RequestBody Transfer transfer);
}
