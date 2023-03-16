package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT NEW com.cg.model.dto.CustomerDTO (" +
                "cus.id, " +
                "cus.fullName, " +
                "cus.email, " +
                "cus.phone, " +
                "cus.balance, " +
                "cus.locationRegion" +
            ") " +
            "FROM Customer AS cus " +
            "WHERE cus.deleted = false "
    )
    List<CustomerDTO> findAllByDeletedIsFalse();

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


    @Modifying
    @Query("UPDATE Customer AS cus " +
            "SET cus.balance = cus.balance + :transactionAmount " +
            "WHERE cus = :customer"
    )
    void incrementBalance(@Param("transactionAmount") BigDecimal transactionAmount, @Param("customer") Customer customer);
}
