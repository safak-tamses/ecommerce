package com.x.ecommerceback.controller;

import com.x.ecommerceback.exception.UnauthorizedException;
import com.x.ecommerceback.model.Category;
import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.Properties;
import com.x.ecommerceback.model.request.CategoryRequest;
import com.x.ecommerceback.model.request.CategoryUpdateRequest;
import com.x.ecommerceback.model.request.CreateProductRequest;
import com.x.ecommerceback.service.UserService;
import com.x.ecommerceback.service.interfaces.CategoryService;
import com.x.ecommerceback.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductRequest createProductRequest, @RequestHeader("Authorization") String jwtToken) {
        try {
            Product createdProduct = productService.createProduct(createProductRequest, jwtToken);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody CategoryRequest categoryRequest, @RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest,jwtToken),HttpStatus.CREATED);
    }
    @GetMapping("/category")
    public ResponseEntity<List<Category>> showCategory(@RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(categoryService.showCategoryList(jwtToken),HttpStatus.OK);
    }
    @PutMapping("/category")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest,@RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(categoryService.updateCategory(categoryUpdateRequest,jwtToken),HttpStatus.OK);
    }
    @DeleteMapping("/category/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id,@RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(categoryService.deleteCategory(id,jwtToken),HttpStatus.OK);
    }

    @PostMapping("/products/id/{productId}")
    public ResponseEntity<Product> editProductProperties(@RequestHeader("Authorization") String jwtToken, @PathVariable Long productId, @RequestBody List<Properties> properties){
        return new ResponseEntity<>(productService.editProductProperties(productId,properties,jwtToken),HttpStatus.OK);
    }

}
