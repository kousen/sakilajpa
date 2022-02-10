package com.kousenit.sakila.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class FilmDao {
    private final SimpleJdbcCall procFilmsInStock;
    private final SimpleJdbcCall procFilmsNotInStock;
    private final SimpleJdbcCall procRewardsReport;
    private final SimpleJdbcCall funcInventoryHeldByCustomer;

    @Autowired
    public FilmDao(DataSource dataSource) {
        procFilmsInStock = new SimpleJdbcCall(dataSource)
                .withProcedureName("film_in_stock");
        procFilmsNotInStock = new SimpleJdbcCall(dataSource)
                .withProcedureName("film_not_in_stock");
        procRewardsReport = new SimpleJdbcCall(dataSource)
                .withProcedureName("rewards_report");
        funcInventoryHeldByCustomer = new SimpleJdbcCall(dataSource)
                .withFunctionName("inventory_held_by_customer");
    }

    public int getFilmsInStock(Integer filmId, Integer storeId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_film_id", filmId)
                .addValue("p_store_id", storeId);
        Map<String, Object> out = procFilmsInStock.execute(in);
        return (int) out.get("p_film_count");
    }

    public int getFilmsNotInStock(Integer filmId, Integer storeId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_film_id", filmId)
                .addValue("p_store_id", storeId);
        Map<String, Object> out = procFilmsNotInStock.execute(in);
        return (int) out.get("p_film_count");
    }

    public int getRewardsReport(Short minMonthlyPurchases, Double minDollarAmountPurchased) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("min_monthly_purchases", minMonthlyPurchases)
                .addValue("min_dollar_amount_purchased", minDollarAmountPurchased);
        Map<String, Object> out = procRewardsReport.execute(in);
        return (int) out.get("count_rewardees");
    }

    public int getInventoryHeldByCustomer(Integer id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_inventory_id", id);
        return funcInventoryHeldByCustomer.executeFunction(Integer.class, in);
    }
}
