package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.exception.UnauthorizedException;
import com.x.ecommerceback.model.*;
import com.x.ecommerceback.model.Properties;
import com.x.ecommerceback.model.request.CreateProductRequest;
import com.x.ecommerceback.model.request.FindAllProductRequest;
import com.x.ecommerceback.model.response.ProductResponse;
import com.x.ecommerceback.repository.CategoryRepository;
import com.x.ecommerceback.repository.ProductRepository;
import com.x.ecommerceback.repository.PropertiesRepository;
import com.x.ecommerceback.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;
    private final PropertiesRepository propertiesRepository;

    @Override
    public Product createProduct(CreateProductRequest request, String jwtToken) {
        try {

            if (jwtService.hasAuthority(jwtToken)) {
                Category category = categoryRepository.findByFirstAndSecondAndThird(
                        request.firstCategory(), request.secondCategory(), request.thirdCategory()
                );

                if (category == null) {
                    throw new RuntimeException();
                } else {
                    Product product = new Product();
                    product.setTitle(request.title());
                    product.setDescription(request.description());
                    product.setPrice(request.price());
                    product.setDiscountedPrice(request.discountedPrice());
                    product.setDiscountPercent(request.discountPercent());
                    product.setBrand(request.brand());
                    product.setImageUrl(request.imageUrl());
                    product.setCategory(category);

                    return productRepository.save(product);
                }
            } else {
                throw new UnauthorizedException("User does not have permission to create a product.");
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("xx " + e);

        }


    }


    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    /* TODO */
    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product p = findProductById(productId);
        if (p.getQuantity() != 0) {
            p.setQuantity(product.getQuantity());
        }
        return productRepository.save(p);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> p = productRepository.findById(productId);
        if (p.isPresent()) {
            return p.get();
        }
        throw new ProductException("Product not found with id - " + productId);

    }
    @Override
    public ProductResponse findProductByIdd(Long productId) throws ProductException {
        Optional<Product> p = productRepository.findById(productId);
        if (p.isPresent()) {
            Product product = p.get();
            return new ProductResponse(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getDiscountedPrice(),
                    product.getDiscountPercent(),
                    product.getQuantity(),
                    product.getBrand(),
                    product.getImageUrl(),
                    product.getNumRatings(),
                    product.getProperties(),
                    product.getRatings(),
                    product.getReviews(),
                    product.getCreated(),
                    product.getUpdated(),
                    product.getCategory().getFirst(),
                    product.getCategory().getSecond(),
                    product.getCategory().getThird()
            );
        }
        throw new ProductException("Product not found with id - " + productId);

    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> findAllProduct(FindAllProductRequest findAllProductRequest) {
        Pageable pageable = PageRequest.of(findAllProductRequest.pageNumber(), findAllProductRequest.pageSize());
        List<Product> products = productRepository.filterProduct(
                findAllProductRequest.minPrice(),
                findAllProductRequest.maxPrice(),
                findAllProductRequest.minDiscount(),
                findAllProductRequest.sort()
        );

        products = products.stream()
                .filter(product -> product.getCategory().getFirst().equals(findAllProductRequest.firstCategory())
                        &&
                        product.getCategory().getSecond().equals(findAllProductRequest.secondCategory())
                        &&
                        product.getCategory().getThird().equals(findAllProductRequest.thirdCategory())).collect(Collectors.toList());




        List<String> colorList = findAllProductRequest.colors();
        colorList = colorList.stream().map(String::toUpperCase).toList();

        Set<Product> pl = new HashSet<>();

        if (!colorList.isEmpty()) {
            for (String c : colorList) {
                for (Product p : products) {
                    for (Properties pr : p.getProperties()) {
                        if (pr.getColor().name().equals(c)) {
                            pl.add(p);
                        }
                    }
                }
            }
            products = new ArrayList<>(pl);
        }

        List<String> sizeList = findAllProductRequest.sizes();
        sizeList = sizeList.stream().map(String::toUpperCase).toList();

        Set<Product> pls = new HashSet<>();

        if (!sizeList.isEmpty()){
            for (String s : sizeList){
                for (Product p : products){
                    for (Properties pr : p.getProperties()){
                        if (pr.getSize().name().equals(s)){
                            pls.add(p);
                        }
                    }
                }
            }
            products = new ArrayList<>(pls);
        }



        if (findAllProductRequest.stock() != null) {
            if (findAllProductRequest.stock().equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (findAllProductRequest.stock().equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }


        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        if (startIndex < endIndex) {
            List<Product> pageContent = products.subList(startIndex, endIndex);
            return new PageImpl<>(pageContent, pageable, products.size());
        } else {
            return Page.empty();
        }
    }

    @Override
    public Product editProductProperties(Long productId, List<Properties> properties, String jwt) {
        try {
            if (jwtService.hasAuthority(jwt)) {
                Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

                int quantity = 0;
                for (Properties p : properties) {
                    if (p.getQuantity() != null) {
                        Properties properties1 = new Properties();
                        properties1.setProduct(product);
                        properties1.setSize(p.getSize());
                        properties1.setColor(p.getColor());
                        properties1.setQuantity(p.getQuantity());
                        quantity += p.getQuantity();

                        propertiesRepository.save(properties1);
                        product.addProperties(properties1);
                    } else {
                        throw new RuntimeException("Quantity cannot be null");
                    }
                }

                product.setQuantity(quantity);
                return productRepository.save(product);
            } else {
                throw new RuntimeException("Unauthorized access");
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("" + e);
        }
    }


}
