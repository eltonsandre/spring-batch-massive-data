package com.github.eltonsandre.dev.massive.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * 002çCNPJçNameçBusiness Area
 *
 * @author eltonsandre
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
public class Salesman implements MassiveType {

    @Id
    @EqualsAndHashCode.Include
    private String cpf;

    private String name;

    private BigDecimal salary;

    @Override
    public String getIdLine() {
        return "002";
    }

}
