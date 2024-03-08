package com.github.thibstars.cic.cucumber;

import com.github.thibstars.cic.CalculationInput;
import com.github.thibstars.cic.CalculationResult;
import com.github.thibstars.cic.DefaultCompoundInterestCompoundInterestCalculator;
import com.github.thibstars.cic.Frequency;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;

/**
 * @author Thibault Helsmoortel
 */
public class CompoundInterestDefinitions {

    private static final String RESULT_HEADER = "result";

    private CalculationInput calculationInput;

    @Given("the calculation input is:")
    public void theCalculationInputIs(DataTable dataTable) {
        List<String> line = dataTable.asLists().get(1);
        Frequency compoundFrequency = Frequency.valueOf(line.get(5));
        this.calculationInput = new CalculationInput(
                new BigDecimal(line.get(0)),
                new BigDecimal(line.get(1)),
                Period.ofYears(Integer.parseInt(line.get(2))),
                new BigDecimal(line.get(3)),
                new BigDecimal(line.get(4)),
                compoundFrequency
        );
    }

    @Then("the calculation result for estimatedAnnualInterestRate {bigdecimal} and interestRateVarianceRange {bigdecimal} is:")
    public void theCalculationResultIs(BigDecimal estimatedAnnualInterestRate, BigDecimal interestRateVarianceRange,
            DataTable dataTable) {
        DefaultCompoundInterestCompoundInterestCalculator calculator = new DefaultCompoundInterestCompoundInterestCalculator();
        Map<BigDecimal, List<CalculationResult>> actualResults = calculator.calculate(calculationInput);

        List<List<String>> lines = dataTable.asLists();

        BigDecimal lowerPercentage = estimatedAnnualInterestRate.subtract(interestRateVarianceRange);
        BigDecimal upperPercentage = estimatedAnnualInterestRate.add(interestRateVarianceRange);
        Map<BigDecimal, List<CalculationResult>> expectedResults = Map.of(
                estimatedAnnualInterestRate, new ArrayList<>(),
                lowerPercentage, new ArrayList<>(),
                upperPercentage, new ArrayList<>()
        );

        lines.forEach(line -> {
            if (RESULT_HEADER.equals(line.get(0))) {
                return;
            }

            expectedResults.get(estimatedAnnualInterestRate).add(
                    new CalculationResult(
                            new BigDecimal(line.get(0)),
                            new BigDecimal(line.get(1))
                    )
            );

            expectedResults.get(lowerPercentage).add(
                    new CalculationResult(
                            new BigDecimal(line.get(2)),
                            new BigDecimal(line.get(3))
                    )
            );

            expectedResults.get(upperPercentage).add(
                    new CalculationResult(
                            new BigDecimal(line.get(4)),
                            new BigDecimal(line.get(5))
                    )
            );
        });

        Assertions.assertEquals(expectedResults, actualResults);
    }

    @Then("the calculation result is for estimatedAnnualInterestRate {bigdecimal}:")
    public void theCalculationResultIs(BigDecimal estimatedAnnualInterestRate, DataTable dataTable) {
        DefaultCompoundInterestCompoundInterestCalculator calculator = new DefaultCompoundInterestCompoundInterestCalculator();
        Map<BigDecimal, List<CalculationResult>> actualResults = calculator.calculate(calculationInput);

        List<List<String>> lines = dataTable.asLists();

        List<CalculationResult> expectedResults = lines.stream()
                .map(line -> {
                    if (RESULT_HEADER.equals(line.get(0))) {
                        return null;
                    }

                    return new CalculationResult(
                            new BigDecimal(line.get(0)),
                            new BigDecimal(line.get(1))
                    );
                })
                .filter(Objects::nonNull)
                .toList();

        Assertions.assertEquals(expectedResults, actualResults.get(estimatedAnnualInterestRate));
    }
}
