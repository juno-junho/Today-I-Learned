package com.prgrms.ktd.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.ktd.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSoruce = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("sean5633")
                    .type(HikariDataSource.class)
                    .build();
            dataSoruce.setMaximumPoolSize(1000);
            dataSoruce.setMinimumIdle(100);
            return dataSoruce;
        }
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName())
                .isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() throws InterruptedException {
        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers).isNotEmpty();
        Thread.sleep(10000);
    }
}