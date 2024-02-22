Feature: Perform a full scan

  Scenario: Perform a calculation with annual frequency
    Given the calculation input is:
      | initialInvestment | monthlyContribution | investmentDuration | estimatedAnnualInterestRate | interestRateVarianceRange | compoundFrequency |
      | 2500              | 25                  | 10                 | 8                           | 2                         | ANNUALLY          |
    Then the calculation result is:
      | result                     | accruedInterest           |
      | 2500                       | 0                         |
      | 3024.00                    | 224.00                    |
      | 3589.9200                  | 489.9200                  |
      | 4201.113600                | 801.113600                |
      | 4861.20268800              | 1161.20268800             |
      | 5574.0989030400            | 1574.0989030400           |
      | 6344.026815283200          | 2044.026815283200         |
      | 7175.54896050585600        | 2575.54896050585600       |
      | 8073.5928773463244800      | 3173.5928773463244800     |
      | 9043.480307534030438400    | 3843.480307534030438400   |
      | 10090.95873213675287347200 | 4590.95873213675287347200 |