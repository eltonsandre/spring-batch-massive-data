package com.github.eltonsandre.dev.massive.batch.job.step;

import static com.github.eltonsandre.dev.massive.batch.config.DelimitedLineTokenizerHandler.CUSTOMER_DELIMITED_LINE_TOKENIZER;
import static com.github.eltonsandre.dev.massive.batch.config.DelimitedLineTokenizerHandler.SALESMAN_DELIMITED_LINE_TOKENIZER;
import static com.github.eltonsandre.dev.massive.batch.config.DelimitedLineTokenizerHandler.SALES_DATA_DELIMITED_LINE_TOKENIZER;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.github.eltonsandre.dev.massive.batch.MassiveJobExecution;
import com.github.eltonsandre.dev.massive.batch.job.SaleMassiveJob;
import com.github.eltonsandre.dev.massive.batch.job.chunklets.TransactionItemProcessor;
import com.github.eltonsandre.dev.massive.batch.job.chunklets.TransactionItemWriter;
import com.github.eltonsandre.dev.massive.batch.job.mapper.CustomerFileRowMapper;
import com.github.eltonsandre.dev.massive.batch.job.mapper.SaleFileRowMapper;
import com.github.eltonsandre.dev.massive.batch.job.mapper.SalesmanFileRowMapper;
import com.github.eltonsandre.dev.massive.model.MassiveType;

/**
 * @author eltonsandre
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TransctionMassiveStepConfig {

    public static final String QUALIFIER = "transctionMassiveStep";

    private static final String JOB_PARAMETERS_FILE_INPUT = "#{jobParameters['" + MassiveJobExecution.PARAM_FILE_IN + "']}";

    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step transctionMassiveStep() {
        return this.stepBuilderFactory.get(QUALIFIER)
                .<MassiveType, MassiveType>chunk(SaleMassiveJob.CHUNK_SIZE)
                .reader(this.transactionFileItemReader(null))
                .processor(this.transactionProcessor())
                .writer(this.writer())
                .build();
    }


    @Bean
    @JobScope
    @SneakyThrows
    public FlatFileItemReader<MassiveType> transactionFileItemReader(@Value(JOB_PARAMETERS_FILE_INPUT) final PathResource resource) {
        log.info("resource={}", resource);

        return new FlatFileItemReaderBuilder<MassiveType>()
                .name("transactionFlatFileItemReader")
                .resource(resource)
                .lineMapper(this.orderFileLineMapper())
                .skippedLinesCallback(log::info)
                .build();
    }

    @Bean
    public TransactionItemProcessor transactionProcessor() {
        return new TransactionItemProcessor();
    }


    @Bean
    public TransactionItemWriter writer() {
        return new TransactionItemWriter();
    }


    @Bean
    public PatternMatchingCompositeLineMapper<MassiveType> orderFileLineMapper() {
        final var lineMapper = new PatternMatchingCompositeLineMapper<MassiveType>();

        lineMapper.setTokenizers(Map.of(
                "001*", SALESMAN_DELIMITED_LINE_TOKENIZER,
                "002*", CUSTOMER_DELIMITED_LINE_TOKENIZER,
                "003*", SALES_DATA_DELIMITED_LINE_TOKENIZER));

        lineMapper.setFieldSetMappers(Map.of(
                "001*", new SalesmanFileRowMapper(),
                "002*", new CustomerFileRowMapper(),
                "003*", new SaleFileRowMapper()));

        return lineMapper;
    }

}
