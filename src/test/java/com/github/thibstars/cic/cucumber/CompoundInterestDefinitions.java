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
import java.util.List;
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
                Double.parseDouble(line.get(3)),
                Double.parseDouble(line.get(4)),
                compoundFrequency
        );
    }

    @Then("the calculation result is:")
    public void theCalculationResultIs(DataTable dataTable) {
        DefaultCompoundInterestCompoundInterestCalculator calculator = new DefaultCompoundInterestCompoundInterestCalculator();
        List<CalculationResult> actualResults = calculator.calculate(calculationInput);
/*        actualResults = actualResults.stream()
                .map(actualResult -> new CalculationResult(
                        actualResult.result().round(MathContext.DECIMAL32),
                        actualResult.accruedInterest().round(MathContext.DECIMAL32)
                ))
                .toList();*/

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

        Assertions.assertEquals(expectedResults, actualResults);
    }
}
