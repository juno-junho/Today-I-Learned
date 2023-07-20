package com.prgrms.ktd.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 테스트 메서드 순서를 지정할 수 있음.
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
    // per class:  인스턴스가 하나. -> beforeall, afterall static으로 설정안해도 된다.
    // default : per method
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.ktd.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("schema.sql")
                    .build();
           /* HikariDataSource dataSoruce = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("sean5633")
                    .type(HikariDataSource.class)
                    .build();
            dataSoruce.setMaximumPoolSize(1000);
            dataSoruce.setMinimumIdle(100);
            return dataSoruce;*/
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
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

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        Optional<Customer> customer = customerJdbcRepository.findByName("new-user");
        assertThat(customer).isNotEmpty();

        var unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown).isEmpty();
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        Optional<Customer> customer = customerJdbcRepository.findByEmail("test00@gmail.com");
        assertThat(customer).isNotEmpty();

        var unknown = customerJdbcRepository.findByName("unknown-user@gmail.com");
        assertThat(unknown).isEmpty();
    }

    @Test
    @DisplayName("고객을 추가할 수 있다")
    public void testInsert() {
        customerJdbcRepository.deleteAll();

        Customer customer = new Customer(UUID.randomUUID(), "test-user", "test1-user@gmail.com", LocalDateTime.now());
        customerJdbcRepository.insert(customer);

        var retrievedCustomer = customerJdbcRepository.findById(customer.getCustomerId());

        assertThat(retrievedCustomer).isNotEmpty();
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        Optional<Customer> customer = customerJdbcRepository.findByEmail("test00@gmail.com");

        /**
         * updated-user의 name을 가진 customer 생성 -> update 치기
         * findall 로 받아온 list의 size가 1, customer와 같은 값인지 assert
         * findById로 customer 객체 id 조회
         * -> not empty한지, 필드 값이 customer와 같은 값인지 assert
         */
    }
}