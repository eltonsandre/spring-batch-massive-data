package com.github.eltonsandre.dev.massive.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author eltonsandre
 */
@Data
@ConfigurationProperties("massive.data")
public class DataResouceProperties {

    String input;
    String output;

    String fileType;
    String sufixResultDone;
    String delimiterReaderDefault;
    String delimiterWriterDefault;

    String initialDelayString;
    String fixedDelayString;


}
