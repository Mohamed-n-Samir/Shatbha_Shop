package com.shatbha_shop.shatbha_shop.Success;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {
    
    private String monthName;
    private List<DailyIncome> dailyIncome;
    private int year;
    private int month;
}
