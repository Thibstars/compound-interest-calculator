package com.github.thibstars.cic;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class DefaultCompoundInterestCompoundInterestCalculator implements CompoundInterestCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DefaultCompoundInterestCompoundInterestCalculator.class);

    @Override
    public List<CalculationResult> calculate(CalculationInput calculationInput) {
        LOGGER.info("Calculating compound interest for input {}", calculationInput);

        List<CalculationResult> calculationResults = new ArrayList<>();

        Period investmentPeriod = calculationInput.investmentPeriod();
        BigDecimal periodContribution;

        int amountOfResults;
        switch (calculationInput.compoundFrequency()) {
            case ANNUALLY -> {
                amountOfResults = investmentPeriod.getYears();
                periodContribution = calculationInput.monthlyContribution().multiply(new BigDecimal("12"));
            }
            default -> {
                LOGGER.warn("Defaulting to compound frequency: {}", Frequency.ANNUALLY.name());
                amountOfResults = investmentPeriod.getYears();
                periodContribution = calculationInput.monthlyContribution().multiply(new BigDecimal("12"));
            }
        }

        BigDecimal initialInvestment = calculationInput.initialInvestment();
        CalculationResult startingPointResult = new CalculationResult(
                initialInvestment,
                BigDecimal.ZERO
        );
        calculationResults.add(startingPointResult);

        BigDecimal interestRatePercentage = BigDecimal.valueOf(calculationInput.estimatedAnnualInterestRate() / 100);
        BigDecimal base = initialInvestment.add(periodContribution);
        calculateResults(calculationResults, interestRatePercentage, base, initialInvestment, BigDecimal.ZERO, periodContribution, amountOfResults);

        return calculationResults;
    }

    private void calculateResults(List<CalculationResult> calculationResults, BigDecimal interestRatePercentage, BigDecimal base, BigDecimal result, BigDecimal accruedInterest, BigDecimal periodContribution, int amountOfResultsLeft) {
        base = result.add(periodContribution);
        result = base.multiply(interestRatePercentage).add(base);
        accruedInterest = accruedInterest.add(result.subtract(base));

        calculationResults.add(new CalculationResult(
                result,
                accruedInterest
        ));

        amountOfResultsLeft--;

        if (amountOfResultsLeft > 0) {
            calculateResults(calculationResults, interestRatePercentage, base, result, accruedInterest, periodContribution, amountOfResultsLeft);
        }
    }
}
