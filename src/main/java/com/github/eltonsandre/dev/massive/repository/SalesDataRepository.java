package com.github.eltonsandre.dev.massive.repository;

import com.github.eltonsandre.dev.massive.model.SalesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author eltonsandre
 */
@Repository("SalesDataRepository")
public interface SalesDataRepository extends JpaRepository<SalesData, Long> {
}
