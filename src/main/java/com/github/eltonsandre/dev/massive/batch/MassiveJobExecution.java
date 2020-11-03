package com.github.eltonsandre.dev.massive.batch;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author eltonsandre
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MassiveJobExecution {

    public static final String PARAM_FILE_IN = "file.input";
    public static final String PARAM_FILE_OUT = "file.output";

    private final Job job;
    private final JobLauncher jobLauncher;

    @Async
    @SneakyThrows
    // @Scheduled(cron = "0 */1 * * * ?")
    public void perform(final String resourceInput, final String resourceOutput) {
        log.info("jobLauncher time={}", LocalDateTime.now());

        this.jobLauncher.run(this.job, new JobParametersBuilder()
                .addString("JobID", String.valueOf(Instant.now().toEpochMilli()))
                .addString(PARAM_FILE_IN, resourceInput)
                .addString(PARAM_FILE_OUT, resourceOutput)
                .toJobParameters());
    }

}
