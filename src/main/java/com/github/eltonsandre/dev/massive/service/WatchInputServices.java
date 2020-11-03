package com.github.eltonsandre.dev.massive.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.eltonsandre.dev.massive.batch.MassiveFilesProcess;
import com.github.eltonsandre.dev.massive.configuration.properties.DataResouceProperties;

/**
 * Serviço ficará olhando a inclusão de novos arquivos para serem processados
 *
 * @author eltonsandre
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnExpression("${massive.data.watch-dir-input.enabled:false}")
public class WatchInputServices {

    private final MassiveFilesProcess massiveFilesProcess;
    private final DataResouceProperties resouceProperties;

    private WatchService watchServices;

    @PostConstruct
    private void createWatchServices() throws IOException {
        final var fileSystem = FileSystems.getDefault();
        this.watchServices = fileSystem.newWatchService();

        final Path path = Paths.get(this.resouceProperties.getInput());
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectories(path);
        }

        path.register(this.watchServices, StandardWatchEventKinds.ENTRY_CREATE);
    }

    @Scheduled(initialDelayString = "${massive.data.initial-delay-string}", fixedDelayString = "${massive.data.fixed-delay-string}")
    private void pollDirChange() throws InterruptedException {
        log.info("checking files...");

        final var watchKey = this.watchServices.take();
        watchKey.pollEvents().stream()
                .map(WatchEvent::context)
                .map(Object::toString)
                .forEach(this.massiveFilesProcess::perform);

        watchKey.reset();
    }


}
