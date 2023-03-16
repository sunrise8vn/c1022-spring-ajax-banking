package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.dto.CustomerDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<CustomerDTO> findAllByDeletedIsFalse();

    List<CustomerDTO> findAllCustomerDTO();

    List<Customer> findAllByIdNot(Long id);

    Boolean existsByEmailEquals(String email);

    Deposit deposit(Deposit deposit);

    void incrementBalance(@Param("transactionAmount") BigDecimal transactionAmount, @Param("customer") Customer customer);

    Transfer transfer(Transfer transfer);
}
