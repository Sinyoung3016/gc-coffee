package kdt.gccoffee.repository;

import kdt.gccoffee.model.Category;
import kdt.gccoffee.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductJdbcRepositoryTest {

    @Container
    private static final MySQLContainer<?> mySqlContainer
            = new MySQLContainer<>(DockerImageName.parse("mysql"))
            .withInitScript("schema.sql");

    @Autowired
    ProductRepository repository;

    static Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("[성공] 상품을 추가할 수 있다")
    void testInsert() {
        repository.insert(newProduct);
        List<Product> products = repository.findAll();
        assertThat(products.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("[성공] 상품을 이름으로 조회할 수 았다")
    void testFindByName(){
        Optional<Product> product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("[성공] 상품을 상품 아이디로 조회할 수 았다")
    void testFindById(){
        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("[성공] 상품을 카테고리로 조회할 수 았다")
    void testFindByCategory(){
        List<Product> products = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(products.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("[성공] 상품을 수정할 수 았다")
    void testUpdate(){
        newProduct.setProductName("updated-product");
        repository.update(newProduct);
        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("[성공] 상품을 전부 삭제한다")
    void testDeleteAll(){
        repository.deleteAll();
        List<Product> products = repository.findAll();
        assertThat(products.isEmpty(), is(true));
    }
}
