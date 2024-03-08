package com.github.thibstars.cic;

import java.math.BigDecimal;
import java.time.Period;

/**
 * @author Thibault Helsmoortel
 */
public record CalculationInput(
        // Amount of money that you have available to invest initially
        BigDecimal initialInvestment,

        // Amount that you plan to add to the principal every month, or a negative number for the amount that you plan to withdraw every month
        BigDecimal monthlyContribution,

        // Period you plan to keep the investment
        Period investmentPeriod,

        // The estimated annual interest rate
        BigDecimal estimatedAnnualInterestRate,

        // Range of interest rates (above and below the rate set in the estimatedAnnualInterestRate) for which to calculate results for
        BigDecimal interestRateVarianceRange,

        // Frequency at which to compound interest
        Frequency compoundFrequency
) {

    @Override
    public String toString() {
        return "CalculationInput{" +
                "initialInvestment=" + initialInvestment +
                ", monthlyContribution=" + monthlyContribution +
                ", investmentPeriod=" + investmentPeriod +
                ", estimatedAnnualInterestRate=" + estimatedAnnualInterestRate +
                ", interestRateVarianceRange=" + interestRateVarianceRange +
                ", compoundFrequency=" + compoundFrequency +
                '}';
    }
}
