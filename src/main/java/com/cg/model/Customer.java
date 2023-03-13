package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal balance;

    @OneToMany(targetEntity = Deposit.class)
    private List<Deposit> deposits;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> senders;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> recipients;

    @ManyToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;


    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Customer customer = (Customer) target;

        String fullName = customer.getFullName();
        String email = customer.getEmail();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "fullName.null");
        }

        if (!email.matches("^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
            errors.rejectValue("email", "email.matches");
        }

    }
}
