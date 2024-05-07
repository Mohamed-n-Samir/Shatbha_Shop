package com.shatbha_shop.shatbha_shop.Success;

import lombok.Data;

@Data
// @NoArgsConstructor
// @AllArgsConstructor
public class DailyIncome {
    private int day;
    private double totalIncome;

    public DailyIncome(int day, int totalIncome){
        this.day = day;
        this.totalIncome = totalIncome;
    }
}
