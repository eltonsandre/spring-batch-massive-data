package com.github.eltonsandre.dev.massive.batch.job.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.github.eltonsandre.dev.massive.batch.config.DelimitedLineTokenizerHandler;
import com.github.eltonsandre.dev.massive.model.ItemSale;

/**
 * Classe responsavel por mapear a lista de item na linha de dados da venda
 *
 * @author eltonsandre
 */
public class ListItemsSaleFileRowMapper implements FieldSetMapper<List<ItemSale>> {

    private static final ItemSaleFileRowMapper itemSaleFileRowMapper = new ItemSaleFileRowMapper();

    @Override
    public List<ItemSale> mapFieldSet(final FieldSet fieldSet) {
        return Arrays.stream(fieldSet.getValues())
                .map(DelimitedLineTokenizerHandler.itemDelimitedLineTokenizer::tokenize)
                .map(itemSaleFileRowMapper::mapFieldSet)
                .collect(Collectors.toList());
    }

}
