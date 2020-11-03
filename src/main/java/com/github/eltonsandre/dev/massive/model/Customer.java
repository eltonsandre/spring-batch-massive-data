package com.github.eltonsandre.dev.massive.model;

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
 * @author eltonsandre
 * 001çCPFçNameçSalary
 */
@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Customer implements MassiveType {

    @Id
    @EqualsAndHashCode.Include
    private String cnpj;

    private String name;

    private String businessArea;

    @Override
    public String getIdLine() {
        return "001";
    }
}
