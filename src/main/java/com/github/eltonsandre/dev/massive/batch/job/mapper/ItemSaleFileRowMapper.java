package com.github.eltonsandre.dev.massive.batch.job.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.github.eltonsandre.dev.massive.model.ItemSale;
import com.github.eltonsandre.dev.massive.utils.MonetaryUtils;

/**
 * Classe responsavel por mapear a lista de item na linha de dados da venda
 *
 * @author eltonsandre
 */
public class ItemSaleFileRowMapper implements FieldSetMapper<ItemSale> {

    @Override
    public ItemSale mapFieldSet(final FieldSet fieldSet) {
        return ItemSale.builder()
                .idSale(fieldSet.readInt(ItemSale.Fields.idSale))
                .price(MonetaryUtils.parse(fieldSet.readString(ItemSale.Fields.price)))
                .quantity(fieldSet.readInt(ItemSale.Fields.quantity))
                .build();
    }

}
