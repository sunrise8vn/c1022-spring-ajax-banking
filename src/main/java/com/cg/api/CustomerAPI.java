package com.cg.api;

import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.LocationRegion;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {

//        List<Customer> customers = customerService.findAll();

        List<CustomerDTO> customerDTOS = customerService.findAllCustomerDTO();
//
//        for (Customer customer : customers) {
//            CustomerDTO customerDTO = customer.toCustomerDTO();
//            customerDTOS.add(customerDTO);
//        }

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

        new CustomerDTO().validate(customerDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existEmail = customerService.existsByEmailEquals(customerDTO.getEmail());

        if (existEmail) {
            throw new EmailExistsException("The email is exists");
        }

        customerDTO.setId(null);
        customerDTO.setBalance(BigDecimal.ZERO);
        customerDTO.getLocationRegion().setId(null);

        Customer customer = customerDTO.toCustomer();
        customer = customerService.save(customer);

        customerDTO = customer.toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Customer> update(@PathVariable Long customerId, @RequestBody Customer customer) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customer.setId(customerId);
        customer.setLocationRegion(customerOptional.get().getLocationRegion());
        customer.setBalance(customerOptional.get().getBalance());
        customerService.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/deposit/{customerId}")
    public ResponseEntity<Customer> deposit(@PathVariable Long customerId, @RequestBody Deposit deposit) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Customer customer = customerOptional.get();

        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);
        customer.setBalance(newBalance);

        deposit.setId(null);
        deposit.setCustomer(customer);

        customerService.deposit(deposit);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
