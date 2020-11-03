package com.github.eltonsandre.dev.massive.batch.job.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.github.eltonsandre.dev.massive.model.MassiveType;
import com.github.eltonsandre.dev.massive.model.Salesman;
import com.github.eltonsandre.dev.massive.utils.MonetaryUtils;

/**
 * Classe responsavel por mapear a linha de vendedor (001)
 *
 * @author eltonsandre
 */
@Slf4j
public class SalesmanFileRowMapper implements FieldSetMapper<MassiveType> {

    @Override
    public Salesman mapFieldSet(final FieldSet fieldSet) {
        var line = Salesman.builder()
                .name(fieldSet.readString(Salesman.Fields.name))
                .cpf(fieldSet.readString(Salesman.Fields.cpf))
                .salary(MonetaryUtils.parse(fieldSet.readString(Salesman.Fields.salary)))
                .build();

        log.info("Salesman={}", line);
        return line;
    }
}
