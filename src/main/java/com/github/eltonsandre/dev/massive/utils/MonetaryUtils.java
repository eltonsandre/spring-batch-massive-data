package com.github.eltonsandre.dev.massive.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author eltonsandre
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonetaryUtils {

    private static final String ZERO_MONEY_SCALE_2 = "0.00";

    public static BigDecimal parse(final String value) {
        String monetary = value;
        if (StringUtils.isEmpty(monetary)) {
            monetary = ZERO_MONEY_SCALE_2;
        }

        return new BigDecimal(monetary)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
