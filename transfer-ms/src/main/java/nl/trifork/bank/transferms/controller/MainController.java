package nl.trifork.bank.transferms.controller;

import nl.trifork.bank.transferms.model.Transfer;
import nl.trifork.bank.transferms.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RefreshScope
@RestController
@RequestMapping("/transfers")
public class MainController {
    /*Maak deze autowired en delete constructor.*/
    private TransferService transferService;


    @Autowired
    public MainController(TransferService transferService) {
        this.transferService = transferService;
    }


    /**
     * Test method for checking if the microservice works
     *
     * @return String containing hello world
     */
    @RequestMapping("/hello")
    public String helloworld() {
        return "Hello world!";
    }


    /**
     * Creates a transferms from the input of the body of the request.
     *
     * @param transfer Transfer object
     * @return The transferms that has just been saved
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createTransfer(@RequestBody Transfer transfer) {
        return transferService.createTransfer(transfer);
    }

    /**
     * Reads a transferms from the database by using the id from the request resource to find it.
     *
     * @param id Transfer id
     * @return The transfer that matches the id that has been requested
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transfer findTransferById(@PathVariable Long id) {
        return transferService.findTransfer(id);
    }

    /**
     * Reads all transactions from the database
     *
     * @return An iterable of transferms objects
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Transfer> findTransfers() {
        return transferService.findAllTransfers();
    }


}
