package com.github.thibstars.cic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

        BigDecimal initialInvestment = calculationInput.initialInvestment();
        CalculationResult startingPointResult = new CalculationResult(
                initialInvestment,
                BigDecimal.ZERO
        );
        calculationResults.add(startingPointResult);

        BigDecimal interestRatePercentage = BigDecimal.valueOf(calculationInput.estimatedAnnualInterestRate()).divide(new BigDecimal("100"), MathContext.DECIMAL64);
        calculateResults(calculationResults, interestRatePercentage, initialInvestment, BigDecimal.ZERO, periodContribution, amountOfResults);

        return calculationResults;
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
