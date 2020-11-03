package com.github.eltonsandre.dev.massive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.eltonsandre.dev.massive.configuration.properties.DataResouceProperties;

@Slf4j
@EnableTask
@EnableAsync
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan(basePackageClasses = DataResouceProperties.class)
public class MassiveBatchApplication implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        MassiveBatchApplication.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(MassiveBatchApplication.class, args);
    }

}
