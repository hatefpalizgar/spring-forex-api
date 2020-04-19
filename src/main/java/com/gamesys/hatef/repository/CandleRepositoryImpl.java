package com.gamesys.hatef.repository;

import com.gamesys.hatef.model.PriceCandle;
import com.gamesys.hatef.model.SummaryDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.apache.commons.math3.util.Precision.round;


@Repository
@Slf4j
public class CandleRepositoryImpl implements CandleRepository {
    
    @Value("${app.default.latest}")
    private int defaultCandlesToShow;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    @Cacheable("candles")
    public Map<SummaryDetails, List<PriceCandle>> getAll() {
        log.info("Retrieving latest price candles from database");
        List<PriceCandle> candlesData = getListOfCandles();
        return Collections.singletonMap(getSummaryOf(candlesData),
                                        getListOfCandles());
    }
    
    private List<PriceCandle> getListOfCandles() {
        String sqlQuery = String.format("SELECT TOP %d * FROM candles", defaultCandlesToShow);
        return jdbcTemplate.query(sqlQuery, this::priceCandlesRowMapper);
    }
    
    private SummaryDetails getSummaryOf(List<PriceCandle> candlesData) {
        double avg = candlesData.stream().mapToDouble(c -> (c.getOpen() + c.getClose())).average().getAsDouble();
        double peak = candlesData.stream().mapToDouble(PriceCandle::getHigh).max().getAsDouble();
        double valley = candlesData.stream().mapToDouble(PriceCandle::getLow).min().getAsDouble();
        avg = round(avg, 4);
        peak = round(peak, 4);
        valley = round(valley, 4);
        return new SummaryDetails(avg, peak, valley);
    }
    
    @Override
    public void save(PriceCandle priceCandle) {
        String sqlInsertQuery = "INSERT INTO candles(datetime,open,high,low,close) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sqlInsertQuery,
                            priceCandle.getDatetime(),
                            priceCandle.getOpen(),
                            priceCandle.getHigh(),
                            priceCandle.getLow(),
                            priceCandle.getClose());
    }
    
    private PriceCandle priceCandlesRowMapper(ResultSet resultSet, int i) throws SQLException {
        return new PriceCandle()
                .setDatetime(resultSet.getString("datetime"))
                .setOpen(resultSet.getDouble("open"))
                .setHigh(resultSet.getDouble("high"))
                .setLow(resultSet.getDouble("low"))
                .setClose(resultSet.getDouble("close"));
    }
}
