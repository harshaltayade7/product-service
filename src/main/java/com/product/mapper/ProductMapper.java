package com.product.mapper;

import com.product.entity.Product;
import com.product.request.ProductRequest;
import com.product.request.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    public Product toProduct(ProductRequest productRequest);
    public Product updateProduct(ProductUpdateRequest productUpdateRequest, @MappingTarget Product product);
    public Product toProductResponse(Product product);
    public List<Product> toProductsResponse(List<Product> products);
}
