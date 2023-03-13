package com.cg.api;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;


    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {

        Boolean existEmail = customerService.existsByEmailEquals(customer.getEmail());

        if (existEmail) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegion.setId(null);

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customer.setLocationRegion(locationRegion);
        customer = customerService.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}
