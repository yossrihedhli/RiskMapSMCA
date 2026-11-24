
package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        String database = jdbcTemplate.queryForObject(
            "SELECT current_database()", String.class
        );

        return Map.of(
            "application", "RiskMap SMCA",
            "status", "UP",
            "database", database
        );
    }
}
