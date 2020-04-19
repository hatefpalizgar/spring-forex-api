package com.gamesys.hatef.service;

import com.gamesys.hatef.model.PriceCandle;
import com.gamesys.hatef.model.SummaryDetails;
import com.gamesys.hatef.repository.CandleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class CandleService {
    
    @Autowired
    private CandleRepository candleRepository;
    
    public Map<SummaryDetails, List<PriceCandle>> getAll() {
        return candleRepository.getAll();
    }
    
    public void save(PriceCandle priceCandle) {
        candleRepository.save(priceCandle);
    }
}
