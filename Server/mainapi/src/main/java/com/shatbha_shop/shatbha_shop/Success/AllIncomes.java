package com.shatbha_shop.shatbha_shop.Success;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AllIncomes {
    private double todayPercentageChange;
    private double todayRevenue;
    private double thisYearIncreaseAmount;
    private double thisWeekIncreaseAmount;
    private double thisMonthIncreaseAmount;
}
