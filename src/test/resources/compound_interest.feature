Feature: Compound Interest

  Scenario: Perform a calculation with annual frequency
    Given the calculation input is:
      | initialInvestment | monthlyContribution | investmentDuration | estimatedAnnualInterestRate | interestRateVarianceRange | compoundFrequency |
      | 2500              | 25                  | 10                 | 8                           | 2                         | ANNUALLY          |
    Then the calculation result is:
      | result   | accruedInterest |
      | 2500     | 0               |
      | 3024.00  | 224.00          |
      | 3589.92  | 489.92          |
      | 4201.11  | 801.11          |
      | 4861.20  | 1161.20         |
      | 5574.10  | 1574.10         |
      | 6344.03  | 2044.03         |
      | 7175.55  | 2575.55         |
      | 8073.59  | 3173.59         |
      | 9043.48  | 3843.48         |
      | 10090.96 | 4590.96         |

  Scenario: Perform a calculation with semi-annual frequency
    Given the calculation input is:
      | initialInvestment | monthlyContribution | investmentDuration | estimatedAnnualInterestRate | interestRateVarianceRange | compoundFrequency |
      | 2500              | 25                  | 2                  | 8                           | 2                         | SEMI_ANNUALLY     |
    Then the calculation result is:
      | result  | accruedInterest |
      | 2500    | 0               |
      | 2862.00 | 212.00          |
      | 3252.96 | 452.96          |
      | 3675.20 | 725.20          |
      | 4131.22 | 1031.22         |

  Scenario: Perform a calculation with quarterly frequency
    Given the calculation input is:
      | initialInvestment | monthlyContribution | investmentDuration | estimatedAnnualInterestRate | interestRateVarianceRange | compoundFrequency |
      | 2500              | 25                  | 2                  | 8                           | 2                         | QUARTERLY         |
    Then the calculation result is:
      | result  | accruedInterest |
      | 2500    | 0               |
      | 2781.00 | 206.00          |
      | 3084.48 | 434.48          |
      | 3412.24 | 687.24          |
      | 3766.22 | 966.22          |
      | 4148.52 | 1273.52         |
      | 4561.40 | 1611.40         |
      | 5007.31 | 1982.31         |
      | 5488.89 | 2388.89         |

  Scenario: Perform a calculation with monthly frequency
    Given the calculation input is:
      | initialInvestment | monthlyContribution | investmentDuration | estimatedAnnualInterestRate | interestRateVarianceRange | compoundFrequency |
      | 2500              | 25                  | 2                  | 8                           | 2                         | MONTHLY           |
    Then the calculation result is:
      | result   | accruedInterest |
      | 2500     | 0               |
      | 2727.00  | 202.00          |
      | 2972.16  | 422.16          |
      | 3236.93  | 661.93          |
      | 3522.88  | 922.88          |
      | 3831.71  | 1206.71         |
      | 4165.25  | 1515.25         |
      | 4525.47  | 1850.47         |
      | 4914.51  | 2214.51         |
      | 5334.67  | 2609.67         |
      | 5788.44  | 3038.44         |
      | 6278.52  | 3503.52         |
      | 6807.80  | 4007.80         |
      | 7379.42  | 4554.42         |
      | 7996.77  | 5146.77         |
      | 8663.51  | 5788.51         |
      | 9383.59  | 6483.59         |
      | 10161.28 | 7236.28         |
      | 11001.18 | 8051.18         |
      | 11908.27 | 8933.27         |
      | 12887.93 | 9887.93         |
      | 13945.96 | 10920.96        |
      | 15088.64 | 12038.64        |
      | 16322.73 | 13247.73        |
      | 17655.55 | 14555.55        |
