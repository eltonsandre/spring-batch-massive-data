package com.github.eltonsandre.dev.massive.batch.job.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.eltonsandre.dev.massive.model.Summary;

/**
 * @author eltonsandre
 */
public class SummaryDBRowMapper implements RowMapper<Summary> {

    private static final String COUNT_CUSTOMER = "count_customer";
    private static final String COUNT_SALESMAN = "count_salesman";
    private static final String ID_SALESMAN_MAX = "id_salesman_max";
    private static final String ID_SALESMAN_MIN = "id_salesman_min";

    @Override
    public Summary mapRow(ResultSet resultSet, int i) throws SQLException {
        return Summary.builder()
                .customerCount(resultSet.getInt(COUNT_CUSTOMER))
                .salesmanCount(resultSet.getInt(COUNT_SALESMAN))
                .expensiveSaleId(resultSet.getInt(ID_SALESMAN_MAX))
                .salesmanWorstSale(resultSet.getInt(ID_SALESMAN_MIN))
                .build();
    }
}
