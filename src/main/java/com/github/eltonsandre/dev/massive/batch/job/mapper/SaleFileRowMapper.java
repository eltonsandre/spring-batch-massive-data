package com.github.eltonsandre.dev.massive.batch.job.mapper;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;

import com.github.eltonsandre.dev.massive.model.ItemSale;
import com.github.eltonsandre.dev.massive.model.MassiveType;
import com.github.eltonsandre.dev.massive.model.SalesData;

/**
 * Classe responsavel por mapear dados da venda
 *
 * @author eltonsandre
 */
@Slf4j
public class SaleFileRowMapper implements FieldSetMapper<MassiveType> {

    private static final String UNWRAPPER_REGEX = "[\\[\\]\"]";
    private static final ListItemsSaleFileRowMapper itemsFileRowMapper = new ListItemsSaleFileRowMapper();

    @Override
    public SalesData mapFieldSet(final FieldSet fieldSet) {
        var build = SalesData.builder()
                .id(fieldSet.readInt(SalesData.Fields.id))
                .salesmanName(fieldSet.readString(SalesData.Fields.salesmanName))
                .items(this.itemsFieldSet(fieldSet.readString(SalesData.Fields.items)))
                .build();

        log.info("Sales={}", build);
        return build;
    }


    private List<ItemSale> itemsFieldSet(final String items) {
        final String replace = items.replaceAll(UNWRAPPER_REGEX, "");
        final FieldSet fieldSet = new DelimitedLineTokenizer().tokenize(replace);

        return itemsFileRowMapper.mapFieldSet(fieldSet);
    }

}
