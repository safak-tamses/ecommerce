package com.x.ecommerceback.controller;

import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.model.Color;
import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.Properties;
import com.x.ecommerceback.model.Size;
import com.x.ecommerceback.model.request.FindAllProductRequest;
import com.x.ecommerceback.model.response.ProductResponse;
import com.x.ecommerceback.service.ProductServiceImplementation;
import com.x.ecommerceback.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam List<String> colors,
            @RequestParam List<String> sizes,
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice,
            @RequestParam Integer minDiscount,
            @RequestParam String sort,
            @RequestParam String stock,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam String firstCategory,
            @RequestParam String secondCategory,
            @RequestParam String thirdCategory
    ){
        FindAllProductRequest request = new FindAllProductRequest(colors,sizes,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize,firstCategory,secondCategory,thirdCategory);
        return new ResponseEntity<>(productService.findAllProduct(request), HttpStatus.OK);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<ProductResponse> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        return new ResponseEntity<>(productService.findProductByIdd(productId),HttpStatus.ACCEPTED);
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
//        return new ResponseEntity<>(productService.)
//    }





}
