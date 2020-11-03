package com.github.eltonsandre.dev.massive.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.eltonsandre.dev.massive.batch.config.FileDownloadTasklet;
import com.github.eltonsandre.dev.massive.batch.job.step.SummaryMassiveStepConfig;
import com.github.eltonsandre.dev.massive.batch.job.step.TransctionMassiveStepConfig;

/**
 * @author eltonsandre
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SaleMassiveJob {

    public static final int CHUNK_SIZE = 2500;

    public final StepBuilderFactory stepBuilderFactory;

    @Qualifier(TransctionMassiveStepConfig.QUALIFIER)
    private final Step transactionMassiveStep;

    @Qualifier(SummaryMassiveStepConfig.QUALIFIER)
    private final Step summaryMassiveStep;


    @Bean
    public Job jobSale(@Autowired JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory
                .get("jobSale")
                .incrementer(new RunIdIncrementer())
                .start(this.taskletStep())
                .next(this.transactionMassiveStep)
                .next(this.summaryMassiveStep)
                .build();
    }


    @Bean
    public Step taskletStep() {
        return this.stepBuilderFactory
                .get("fileDownloadingStep")
                .tasklet(new FileDownloadTasklet()).build();
    }

}
