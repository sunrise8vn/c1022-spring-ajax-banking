package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByIdNot(Long id);

    Boolean existsByEmailEquals(String email);


    @Query("SELECT NEW com.cg.model.dto.CustomerDTO (" +
                "cus.id, " +
                "cus.fullName, " +
                "cus.email, " +
                "cus.phone, " +
                "cus.balance, " +
                "cus.locationRegion" +
            ") " +
            "FROM Customer AS cus"
    )
    List<CustomerDTO> findAllCustomerDTO();
}
