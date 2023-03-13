package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> findAllByIdNot(Long id);

    Boolean existsByEmailEquals(String email);

    Deposit deposit(Deposit deposit);

    Transfer transfer(Transfer transfer);
}
