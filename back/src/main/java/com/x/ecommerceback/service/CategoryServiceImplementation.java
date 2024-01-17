package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.model.Category;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.CategoryRequest;
import com.x.ecommerceback.model.request.CategoryUpdateRequest;
import com.x.ecommerceback.repository.CategoryRepository;
import com.x.ecommerceback.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;

    @Override
    public Category createCategory(CategoryRequest categoryRequest, String jwt) {
        try {
            Category category = new Category();
            category.setFirst(categoryRequest.first());
            category.setSecond(categoryRequest.second());
            category.setThird(categoryRequest.third());
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new CustomRuntimeException("Category kaydetme hatası: " + e);
        }
    }

    @Override
    public Category updateCategory(CategoryUpdateRequest categoryUpdateRequest, String jwt) {
        try {
            if(adminCheck(jwt)){
                Category category = categoryRepository.findById(categoryUpdateRequest.categoryId()).orElseThrow();
                category.setFirst(categoryUpdateRequest.first());
                category.setSecond(categoryUpdateRequest.second());
                category.setThird(categoryUpdateRequest.third());
                return categoryRepository.save(category);
            }
            else throw new RuntimeException();
        } catch (Exception e) {
            throw new CustomRuntimeException("Category güncelleme hatası: " + e);
        }
    }

    @Override
    public List<Category> showCategoryList(String jwt) {
        try {
            if (adminCheck(jwt))
            return categoryRepository.findAll();
            else throw new RuntimeException();
        } catch (Exception e) {
            throw new CustomRuntimeException("Category listeleme hatası: " + e);
        }

    }

    @Override
    public List<Category> deleteCategory(Long id, String jwt) {
        try {
            if (adminCheck(jwt)){
                Category category = categoryRepository.findById(id).orElseThrow();
                categoryRepository.delete(category);
                return categoryRepository.findAll();
            }
            else throw new RuntimeException();
        } catch (Exception e) {
            throw new CustomRuntimeException("Category silme hatası: " + e);
        }
    }

    private boolean adminCheck(String jwt) {
        try {
            User u = jwtService.findUserProfileByJwt(jwt);
            return u != null;
        } catch (Exception e) {
            throw new CustomRuntimeException("Admin kontrol hatası: " + e);
        }
    }
}
