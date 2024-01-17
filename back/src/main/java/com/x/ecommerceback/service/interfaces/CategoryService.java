package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.model.Category;
import com.x.ecommerceback.model.request.CategoryRequest;
import com.x.ecommerceback.model.request.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest, String jwt);
    Category updateCategory(CategoryUpdateRequest categoryUpdateRequest,String jwt);
    List<Category> showCategoryList(String jwt);
    List<Category> deleteCategory(Long id,String jwt);
}
