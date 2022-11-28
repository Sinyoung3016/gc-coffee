package kdt.gccoffee.repository;

import kdt.gccoffee.model.Category;
import kdt.gccoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static kdt.gccoffee.Utils.*;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        int updated = jdbcTemplate.update(
                "insert into products(product_id, product_name, category, price, descriptions, created_at, updated_at)" +
                        " values(UUID_TO_BIN(:productId), :productName, :category, :price, :descriptions, :createdAt, :updatedAt)",
                toParamMap(product));
        if (updated != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        int updated = jdbcTemplate.update(
                "update products set product_name = :productName, category = :category, price = :price, descriptions = :descriptions, created_at = :createdAt, updated_at = :updatedAt where product_id = UUID_TO_BIN(:productId)",
                toParamMap(product));
        if (updated != 1){
            throw new RuntimeException("Nothing was updated");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(
                            "select * from products where product_id = UUID_TO_BIN(:productId)",
                            Collections.singletonMap("productId", productId.toString().getBytes()),
                            productRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(
                            "select * from products where product_name = :productName",
                            Collections.singletonMap("productName", productName),
                            productRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
                "select * from products where category = :category",
                Collections.singletonMap("category", category.toString()),
                productRowMapper
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products;", Collections.emptyMap());
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        String productName = resultSet.getString("product_name");
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        String descriptions = resultSet.getString("descriptions");
        LocalDateTime created_at = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updated_at = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Product(productId, productName, category, price, descriptions, created_at, updated_at);
    };

    private Map<String, Object> toParamMap(Product product) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("descriptions", product.getDescriptions());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());
        return paramMap;
    }
}
