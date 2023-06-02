package com.product.service;

import com.product.entity.Product;
import com.product.mapper.ProductMapper;
import com.product.request.ProductRequest;
import com.product.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final MongoTemplate mongoTemplate;
    private final ProductMapper productMapper;

    public Query productQuery(String productCode) {
        Query query = new Query(Criteria.where("productCode").is(productCode));
        return query;
    }
    public List<Product> getProductByProductCode(String productCode) {
        var product = mongoTemplate.find(productQuery(productCode), Product.class);
        if(product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return product;
    }
    public Product createProduct(ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        var response = productMapper.toProductResponse(mongoTemplate.save(product));
        return response;
    }
    public Product updateProduct(ProductUpdateRequest productUpdateRequest, String productCode) {
        var updatedProduct = productMapper.updateProduct(productUpdateRequest, getProductByProductCode(productCode).get(0));
        var response = productMapper.toProductResponse(mongoTemplate.save(updatedProduct));
        return response;
    }

    public ResponseEntity<String> removeProduct(String productCode) {
        if(!getProductByProductCode(productCode).isEmpty()) {
            mongoTemplate.remove(productQuery(productCode), Product.class);
            return new ResponseEntity<>("Product with product name "+ productCode+" removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>( "Product with name "+ productCode+" not found", HttpStatus.NOT_FOUND);
        }
   }
    public List<Product> getProducts() {
        return productMapper.toProductsResponse(mongoTemplate.findAll(Product.class));
    }
}
