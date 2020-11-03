package com.github.eltonsandre.dev.massive.repository;

import com.github.eltonsandre.dev.massive.model.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author eltonsandre
 */
@Repository("SalesmanRepository")
public interface SalesmanRepository extends JpaRepository<Salesman, Long> {
}
