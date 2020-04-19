package com.gamesys.hatef.repository;

import com.gamesys.hatef.model.PriceCandle;
import com.gamesys.hatef.model.SummaryDetails;

import java.util.List;
import java.util.Map;


public interface CandleRepository {
    Map<SummaryDetails, List<PriceCandle>> getAll();
    void save(PriceCandle priceCandle);
}
