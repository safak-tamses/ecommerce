package com.x.ecommerceback.model.request;

import com.x.ecommerceback.model.Color;
import com.x.ecommerceback.model.Size;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/**
 * Represents a request object for finding products with specified filters.
 *
 * @param colors       The list of colors to filter by.
 * @param sizes        The list of sizes to filter by.
 * @param minPrice     The minimum price of the product.
 * @param maxPrice     The maximum price of the product.
 * @param minDiscount  The minimum discount of the product.
 * @param sort         The sorting order for the result.
 * @param stock        The stock status of the product.
 * @param pageNumber   The page number for pagination.
 * @param pageSize     The page size for pagination.
 */
public record FindAllProductRequest(
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
) {
}
