package com.github.eltonsandre.dev.massive.repository;

import com.github.eltonsandre.dev.massive.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author eltonsandre
 */
@Repository("CustomerRepository")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
