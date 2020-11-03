package com.github.eltonsandre.dev.massive.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * [Item ID-Item Quantity-Item Price]
 *
 * @author eltonsandre
 */
@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemSale {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer idSale;

    private int quantity;

    private BigDecimal price;

    public BigDecimal calculateTheTotalOftheItem() {
        return BigDecimal.valueOf(this.getQuantity()).multiply(this.getPrice());
    }
}
