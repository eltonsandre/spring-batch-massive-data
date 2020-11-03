package com.github.eltonsandre.dev.massive;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.eltonsandre.dev.massive.batch.MassiveJobExecution;
import com.github.eltonsandre.dev.massive.batch.job.step.SummaryMassiveStepConfig;
import com.github.eltonsandre.dev.massive.batch.job.step.TransctionMassiveStepConfig;
import com.github.eltonsandre.dev.massive.configuration.properties.DataResouceProperties;
import com.github.eltonsandre.dev.massive.model.MassiveType;

@SpringBatchTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(DataResouceProperties.class)
@ContextConfiguration(classes = MassiveBatchApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
class MassiveBatchApplicationTests {

    private static final String TEST_INPUT_ONE = "src/test/resources/input/massive.dat";
    private static final String TEST_OUTPUT = "src/test/resources/output/massive.done.dat";
    private static final String EXPECTED_OUTPUT = "src/test/resources/output/expected-massive.done.dat";
    private static final int ROWS_INPUT = 6;

    @Autowired
    private FlatFileItemReader<MassiveType> itemReader;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @After
    public void cleanUp() {
        this.jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters defaultJobParameters() {
        return new JobParametersBuilder()
                .addString("JobTestID", String.valueOf(Instant.now().toEpochMilli()))
                .addString(MassiveJobExecution.PARAM_FILE_IN, TEST_INPUT_ONE)
                .addString(MassiveJobExecution.PARAM_FILE_OUT, TEST_OUTPUT)
                .toJobParameters();
    }

    @Test
    void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);
        // when
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(this.defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        // then
        assertEquals(actualJobInstance.getJobName(), "jobSale");
        assertEquals(actualJobExitStatus.getExitCode(), ExitStatus.COMPLETED.getExitCode());
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }


    @Test
    void when_TransctionMassiveStepExecuted_thenSuccess() {
        // when
        final var jobExecution = this.jobLauncherTestUtils.launchStep(TransctionMassiveStepConfig.QUALIFIER, this.defaultJobParameters());
        final Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        final var exitStatus = jobExecution.getExitStatus();
        // then
        assertEquals(actualStepExecutions.size(), 1);
        assertEquals(exitStatus.getExitCode(), ExitStatus.COMPLETED.getExitCode());
        actualStepExecutions.forEach(stepExecution -> assertEquals(stepExecution.getWriteCount(), ROWS_INPUT));
    }


    @Test
    void givenReferenceOutput_whenSummaryMassiveStepExecuted_thenSuccess() throws Exception {
        // given
        final var expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        final var actualResult = new FileSystemResource(TEST_OUTPUT);
        // when
        final var jobExecution = this.jobLauncherTestUtils.launchStep(SummaryMassiveStepConfig.QUALIFIER, this.defaultJobParameters());
        final Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        final ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        // then
        assertEquals(actualStepExecutions.size(), 1);
        assertEquals(actualJobExitStatus.getExitCode(), ExitStatus.COMPLETED.getExitCode());
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

}
