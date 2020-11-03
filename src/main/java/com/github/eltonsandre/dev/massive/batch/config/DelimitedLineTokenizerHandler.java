package com.github.eltonsandre.dev.massive.batch.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import com.github.eltonsandre.dev.massive.model.Customer;
import com.github.eltonsandre.dev.massive.model.ItemSale;
import com.github.eltonsandre.dev.massive.model.SalesData;
import com.github.eltonsandre.dev.massive.model.Salesman;

/**
 * @author eltonsandre
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DelimitedLineTokenizerHandler {

    public static final String DELIMITER_C = "ç";
    public static final String DELIMITER_HIFEN = "-";
    public static final String LINE_ID_LABEL = "lineId";

    public static final DelimitedLineTokenizer SALESMAN_DELIMITED_LINE_TOKENIZER;
    public static final DelimitedLineTokenizer CUSTOMER_DELIMITED_LINE_TOKENIZER;
    public static final DelimitedLineTokenizer SALES_DATA_DELIMITED_LINE_TOKENIZER;
    public static final DelimitedLineTokenizer itemDelimitedLineTokenizer;

    static {
        SALESMAN_DELIMITED_LINE_TOKENIZER = delimitedLineTokenizer(DELIMITER_C, LINE_ID_LABEL, Salesman.Fields.cpf, Salesman.Fields.name,
                Salesman.Fields.salary);

        CUSTOMER_DELIMITED_LINE_TOKENIZER = delimitedLineTokenizer(DELIMITER_C, LINE_ID_LABEL, Customer.Fields.cnpj, Customer.Fields.name,
                Customer.Fields.businessArea);

        SALES_DATA_DELIMITED_LINE_TOKENIZER = delimitedLineTokenizer(DELIMITER_C, LINE_ID_LABEL, SalesData.Fields.id, SalesData.Fields.items,
                SalesData.Fields.salesmanName);

        itemDelimitedLineTokenizer = delimitedLineTokenizer(DELIMITER_HIFEN, ItemSale.Fields.idSale, ItemSale.Fields.quantity, ItemSale.Fields.price);
    }

    public static DelimitedLineTokenizer delimitedLineTokenizer(String delimiter, String... names) {
        final var delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(names);
        delimitedLineTokenizer.setDelimiter(delimiter);

        return delimitedLineTokenizer;
    }

}
