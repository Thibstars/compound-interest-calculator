package com.github.thibstars.cic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class DefaultCompoundInterestCompoundInterestCalculator implements CompoundInterestCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DefaultCompoundInterestCompoundInterestCalculator.class);

    @Override
    public Map<BigDecimal, List<CalculationResult>> calculate(CalculationInput calculationInput) {
        LOGGER.info("Calculating compound interest for input {}", calculationInput);


        Period investmentPeriod = calculationInput.investmentPeriod();
        BigDecimal periodContribution;

        int amountOfResults;
        switch (calculationInput.compoundFrequency()) {
            case ANNUALLY -> {
                amountOfResults = investmentPeriod.getYears();
                periodContribution = calculationInput.monthlyContribution().multiply(new BigDecimal("12"));
            }
            case SEMI_ANNUALLY -> {
                amountOfResults = investmentPeriod.getYears() * 2;
                periodContribution = calculationInput.monthlyContribution().multiply(new BigDecimal("6"));
            }
            case QUARTERLY -> {
                amountOfResults = investmentPeriod.getYears() * 4;
                periodContribution = calculationInput.monthlyContribution().multiply(new BigDecimal("3"));
            }
            case MONTHLY -> {
                amountOfResults = investmentPeriod.getYears() * 12;
                periodContribution = calculationInput.monthlyContribution();
            }
            default -> {
                LOGGER.warn("Defaulting to compound frequency: {}", Frequency.ANNUALLY.name());
                amountOfResults = investmentPeriod.getYears();
                periodContribution = calculationInput.monthlyContribution().multiply(new BigDecimal("12"));
            }
        }

        return calculateAllResults(calculationInput, periodContribution, amountOfResults);
    }

    private Map<BigDecimal, List<CalculationResult>> calculateAllResults(CalculationInput calculationInput,
            BigDecimal periodContribution, int amountOfResults) {
        BigDecimal initialInvestment = calculationInput.initialInvestment();
        CalculationResult startingPointResult = new CalculationResult(
                initialInvestment,
                BigDecimal.ZERO
        );

        Map<BigDecimal, List<CalculationResult>> allResults = Map.of(
                calculationInput.estimatedAnnualInterestRate(), new ArrayList<>(),
                calculationInput.estimatedAnnualInterestRate().subtract(calculationInput.interestRateVarianceRange()), new ArrayList<>(),
                calculationInput.estimatedAnnualInterestRate().add(calculationInput.interestRateVarianceRange()), new ArrayList<>()
        );

        allResults.forEach((rate, results) -> {
                    results.add(startingPointResult);

                    BigDecimal interestRatePercentage = rate
                            .divide(new BigDecimal("100"), MathContext.DECIMAL64);
                    calculateResults(results, interestRatePercentage, initialInvestment, BigDecimal.ZERO,
                            periodContribution, amountOfResults);
                }
        );

        return allResults;
    }

    private void calculateResults(List<CalculationResult> calculationResults, BigDecimal interestRatePercentage, BigDecimal result, BigDecimal accruedInterest, BigDecimal periodContribution, int amountOfResultsLeft) {
        BigDecimal base = result.add(periodContribution);
        result = base.multiply(interestRatePercentage).add(base).setScale(2, RoundingMode.HALF_UP);
        accruedInterest = accruedInterest.add(result.subtract(base));

        calculationResults.add(new CalculationResult(
                result,
                accruedInterest
        ));

        amountOfResultsLeft--;

        if (amountOfResultsLeft > 0) {
            calculateResults(calculationResults, interestRatePercentage, result, accruedInterest, periodContribution, amountOfResultsLeft);
        }
    }
}
