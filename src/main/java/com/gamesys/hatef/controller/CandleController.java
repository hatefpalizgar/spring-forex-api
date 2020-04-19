package com.gamesys.hatef.controller;

import com.gamesys.hatef.model.PriceCandle;
import com.gamesys.hatef.model.SummaryDetails;
import com.gamesys.hatef.service.CandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/currency")
public class CandleController {
    
    @Autowired
    private CandleService service;
    
    @GetMapping("/all")
    public Map<SummaryDetails, List<PriceCandle>> getAllPriceCandles() {
        return service.getAll();
    }
}
