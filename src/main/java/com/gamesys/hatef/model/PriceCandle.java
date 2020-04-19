package com.gamesys.hatef.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceCandle {
    private String datetime;
    private double open;
    private double high;
    private double low;
    private double close;
}