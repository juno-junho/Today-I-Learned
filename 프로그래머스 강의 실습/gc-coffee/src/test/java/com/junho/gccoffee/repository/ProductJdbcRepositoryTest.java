package com.junho.gccoffee.repository;

import com.junho.gccoffee.model.Category;
import com.junho.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeEach
    void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScripts("schema.sql"))
                .start();
    }

    @AfterAll
    static void afterAll() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository repository;

    private static final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1_000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다")
    void test_Insert() {
        // Given
        repository.insert(newProduct);
        // When
        List<Product> all = repository.findAll();
        // Then
        assertThat(all).isNotEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void test_findByName() {
        // Given, When
        Optional<Product> product = repository.findByName(newProduct.getProductName());
        // Then
        assertThat(product).isNotEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void test_findById() {
        // Given, When
        Optional<Product> product = repository.findById(newProduct.getProductId());
        // Then
        assertThat(product).isNotEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        // Given, When
        List<Product> products = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        // Then
        assertThat(products).isNotEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        repository.update(newProduct);

        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product).isNotEmpty();
        assertThat(product.get()).usingRecursiveAssertion().isEqualTo(newProduct);
    }

    @Test
    @Order(6)
    @DisplayName("상품을 전체 삭제한다.")
    void testDeleteAll() {
        repository.deleteAll();
        List<Product> all = repository.findAll();
        assertThat(all).isEmpty();
    }
}