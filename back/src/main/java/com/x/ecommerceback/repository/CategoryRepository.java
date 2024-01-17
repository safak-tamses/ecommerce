package com.x.ecommerceback.repository;

import com.x.ecommerceback.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByFirstAndSecondAndThird(String first, String second, String third);
}
