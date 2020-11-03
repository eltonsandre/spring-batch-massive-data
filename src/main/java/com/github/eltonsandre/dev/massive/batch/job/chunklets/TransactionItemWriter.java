package com.github.eltonsandre.dev.massive.batch.job.chunklets;

import static com.github.eltonsandre.dev.massive.MassiveBatchApplication.context;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.eltonsandre.dev.massive.model.MassiveType;

/**
 * @author eltonsandre
 */
@Slf4j
public class TransactionItemWriter implements ItemWriter<MassiveType> {

    private static final String SUFIX_REPOSITORY = "Repository";

    @Override
    @Transactional
    public void write(List<? extends MassiveType> items) {
        log.info("write={}", items);

        items.stream()
                .collect(Collectors.groupingBy(type -> type.getClass().getSimpleName()))
                .forEach((repository, transactions) -> ((JpaRepository)
                        context.getBean(repository.concat(SUFIX_REPOSITORY)))
                        .saveAll(transactions));
    }

}
