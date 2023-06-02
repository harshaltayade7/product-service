package com.product.controller;

import com.product.entity.Product;
import com.product.request.ProductRequest;
import com.product.request.ProductUpdateRequest;
import com.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid ProductRequest productRequest) {
       return productService.createProduct(productRequest);
    }
    @PutMapping("/productCode")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@RequestBody @Valid ProductUpdateRequest productUpdateRequest, String productCode){
       return productService.updateProduct(productUpdateRequest, productCode);
    }
    @DeleteMapping(value = "/{productCode}")
    public ResponseEntity<String> removeProduct(@PathVariable String productCode) {
        return productService.removeProduct(productCode);
    }
    @GetMapping("/get-all")
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
