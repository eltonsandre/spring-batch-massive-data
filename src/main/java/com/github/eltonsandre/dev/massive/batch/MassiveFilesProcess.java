package com.github.eltonsandre.dev.massive.batch;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.github.eltonsandre.dev.massive.configuration.properties.DataResouceProperties;

/**
 * @author eltonsandre
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MassiveFilesProcess {

    private final MassiveJobExecution jobExecution;
    private final DataResouceProperties resouceProperties;


    @SneakyThrows
    public void perform(final String filename) {
        if (!filename.endsWith(".dat")) {
            log.info("skipping files={}", filename);
            return;
        }

        log.info("processing file={}", filename);
        this.jobExecution.perform(
                this.resouceProperties.getInput().concat("/").concat(filename),
                this.resouceProperties.getOutput().concat("/").concat(filename
                        .replace(this.resouceProperties.getFileType(), this.resouceProperties.getSufixResultDone())
                        .concat(this.resouceProperties.getFileType())));

        log.info("end of processing");
    }
}
