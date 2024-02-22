package com.github.thibstars.cic;

import java.util.List;

/**
 * @author Thibault Helsmoortel
 */
public interface CompoundInterestCalculator {

    List<CalculationResult> calculate(CalculationInput calculationInput);

}
