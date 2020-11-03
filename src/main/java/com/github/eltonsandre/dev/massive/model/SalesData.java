package com.github.eltonsandre.dev.massive.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * @author eltonsandre
 * 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name
 */
@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SalesData implements MassiveType {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    private String salesmanName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemSale> items;

    private BigDecimal total;

    @Override
    public String getIdLine() {
        return "003";
    }

}
