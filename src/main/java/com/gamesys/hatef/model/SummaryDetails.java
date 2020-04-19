package com.gamesys.hatef.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDetails {
    private double avg;
    private double peak;
    private double valley;
    
    @Override
    public String toString() {
        return "Summary {" +
                " avg=" + avg +
                ", peak=" + peak +
                ", valley=" + valley +
                '}';
    }
}
