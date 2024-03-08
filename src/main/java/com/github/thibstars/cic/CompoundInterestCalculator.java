package com.github.thibstars.cic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Thibault Helsmoortel
 */
public interface CompoundInterestCalculator {

    Map<BigDecimal, List<CalculationResult>> calculate(CalculationInput calculationInput);

}
