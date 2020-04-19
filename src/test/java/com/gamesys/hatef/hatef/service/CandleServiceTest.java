package com.gamesys.hatef.service;

import com.gamesys.hatef.model.PriceCandle;
import com.gamesys.hatef.model.SummaryDetails;
import com.gamesys.hatef.repository.CandleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;


class CandleServiceTest {
    @Mock
    CandleRepository candleRepository;
    
    @InjectMocks
    CandleService candleService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    void shouldReturnAllCandlesWhenGetAllIsCalled() {
        //  given
        SummaryDetails mockSummary = new SummaryDetails(1.5d, 2d, 1d);
        PriceCandle mockPriceCandle = new PriceCandle("datetime", 2d, 2d, 1d, 1d);
        Map<SummaryDetails, List<PriceCandle>> expected = new HashMap<SummaryDetails, List<PriceCandle>>() {{
            put(mockSummary, singletonList(mockPriceCandle));
        }};
        //  when...then
        when(candleRepository.getAll())
                .thenReturn(expected);
        //  assertion
        Map<SummaryDetails, List<PriceCandle>> actual = candleService.getAll();
        Assertions.assertEquals(expected, actual);
    }
}
