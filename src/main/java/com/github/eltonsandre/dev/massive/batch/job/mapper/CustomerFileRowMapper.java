package com.github.eltonsandre.dev.massive.batch.job.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.github.eltonsandre.dev.massive.model.Customer;
import com.github.eltonsandre.dev.massive.model.MassiveType;

/**
 * Classe responsavel por mapear a linha de cliente (002)
 *
 * @author s2it_eandre
 */
@Slf4j
public class CustomerFileRowMapper implements FieldSetMapper<MassiveType> {

    @Override
    public Customer mapFieldSet(FieldSet fieldSet) {
        var build = Customer.builder()
                .cnpj(fieldSet.readString(Customer.Fields.cnpj))
                .name(fieldSet.readString(Customer.Fields.name))
                .businessArea(fieldSet.readString(Customer.Fields.businessArea))
                .build();

        log.info("Customer={}", build);
        return build;
    }

}
