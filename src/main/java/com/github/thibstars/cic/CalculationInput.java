package com.github.thibstars.cic;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author Thibault Helsmoortel
 */
public record CalculationInput(
        // Amount of money that you have available to invest initially
        BigDecimal initialInvestment,

        // Amount that you plan to add to the principal every month, or a negative number for the amount that you plan to withdraw every month
        BigDecimal monthlyContribution,

        // Duration you plan to keep the investment
        Duration investmentDuration,

        // The estimated annual interest rate
        double estimatedAnnualInterestRate,

        // Range of interest rates (above and below the rate set in the estimatedAnnualInterestRate) for which to calculate results for
        double interestRateVarianceRange,

        // Frequency at which to compound interest
        ChronoUnit compoundFrequency
) {

}
