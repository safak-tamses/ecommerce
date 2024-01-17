package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.Properties;
import com.x.ecommerceback.model.request.CreateProductRequest;
import com.x.ecommerceback.model.request.FindAllProductRequest;
import com.x.ecommerceback.model.response.ProductResponse;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest request, String jwtToken);
    public String deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    public ProductResponse findProductByIdd(Long productId) throws ProductException;
    public List<Product> findProductByCategory(String category);
    public Page<Product> findAllProduct(FindAllProductRequest findAllProductRequest);

    public Product editProductProperties(Long productId, List<Properties> properties, String jwt);
}
