package com.github.eltonsandre.dev.massive.batch.job.chunklets;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import com.github.eltonsandre.dev.massive.model.ItemSale;
import com.github.eltonsandre.dev.massive.model.MassiveType;
import com.github.eltonsandre.dev.massive.model.SalesData;

/**
 * @author eltonsandre
 */
@Slf4j
public class TransactionItemProcessor implements ItemProcessor<MassiveType, MassiveType> {

    @Override
    public MassiveType process(MassiveType item) {
        log.debug(item.getIdLine());

        if (item instanceof SalesData salesData) {
            salesData.setTotal(salesData.getItems().stream()
                    .map(ItemSale::calculateTheTotalOftheItem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            return salesData;
        }

        log.debug(item.toString());
        return item;
    }

}