package com.github.eltonsandre.dev.massive.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author eltonsandre
 */
@Slf4j
public class FileDownloadTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("executando a lógica de download do arquivo");

        return RepeatStatus.FINISHED;
    }

}