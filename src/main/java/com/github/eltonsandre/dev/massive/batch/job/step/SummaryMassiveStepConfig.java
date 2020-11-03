package com.github.eltonsandre.dev.massive.batch.job.step;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.github.eltonsandre.dev.massive.batch.MassiveJobExecution;
import com.github.eltonsandre.dev.massive.batch.config.DelimitedLineTokenizerHandler;
import com.github.eltonsandre.dev.massive.batch.job.SaleMassiveJob;
import com.github.eltonsandre.dev.massive.batch.job.mapper.SummaryDBRowMapper;
import com.github.eltonsandre.dev.massive.model.Summary;

/**
 * @author eltonsandre
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SummaryMassiveStepConfig {

    public static final String QUALIFIER = "summaryMassiveStep";

    private static final String JOB_PARAMETERS_FILE_OUTPUT = "#{jobParameters['" + MassiveJobExecution.PARAM_FILE_OUT + "']}";

    private static final String SUMMARY_QUERY = """
            select  ( select count(cnpj) from  customer ) as count_customer,
                    ( select count(cpf) from  salesman ) as count_salesman, 
                    ( select id from sales_data where total = ( select max(total) from  sales_data) ) as id_salesman_max, 
                    ( select id from sales_data where total = ( select min(total) from  sales_data) ) as id_salesman_min;  
                    """;

    public final StepBuilderFactory stepBuilderFactory;
    public final DataSource dataSource;


    @Bean
    public Step summaryMassiveStep() {
        return this.stepBuilderFactory.get(QUALIFIER)
                .<Summary, Summary>chunk(SaleMassiveJob.CHUNK_SIZE)
                .reader(this.readerDB())
                .writer(this.summaryFileWriter(null))
                .build();
    }


    @Bean
    @JobScope
    @SneakyThrows
    public JdbcCursorItemReader<Summary> readerDB() {
        JdbcCursorItemReader<Summary> reader = new JdbcCursorItemReader<>();
        reader.setName("summaryFlatFileItemReader");
        reader.setDataSource(this.dataSource);

        reader.setSql(SUMMARY_QUERY);

        reader.setRowMapper(new SummaryDBRowMapper());

        return reader;
    }


    @Bean
    @JobScope
    public ItemStreamWriter<Summary> summaryFileWriter(@Value(JOB_PARAMETERS_FILE_OUTPUT) final PathResource resource) {
        log.info("resource={}", resource);

        final var writer = new FlatFileItemWriter<Summary>();
        writer.setResource(resource);

        final var beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Summary>();
        beanWrapperFieldExtractor.setNames(new String[]{
                Summary.Fields.customerCount, Summary.Fields.salesmanCount, Summary.Fields.expensiveSaleId, Summary.Fields.salesmanWorstSale});

        final var delimitedLineAggregator = new DelimitedLineAggregator<Summary>();
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        delimitedLineAggregator.setDelimiter(DelimitedLineTokenizerHandler.DELIMITER_C);

        writer.setLineAggregator(delimitedLineAggregator);
        writer.setShouldDeleteIfExists(true);

        return writer;
    }


}
