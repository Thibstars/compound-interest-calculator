package com.github.thibstars.cic;

import java.math.BigDecimal;

/**
 * @author Thibault Helsmoortel
 */
public record CalculationResult(
        BigDecimal result,
        BigDecimal accruedInterest
) {
}
