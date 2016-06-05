package rs.dodalovic.demos.category;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class CategoryRepository {
    Category save(Category category) {
        System.out.println("Saving category: " + category);
        return category.withId(1);
    }

    List<String> getAllCategories() {
        return asList("Category 1", "Category 2", "Category 3");
    }

    Optional<Category> getCategory(int categoryId) {
        return Optional.of(new Category(categoryId, "Category-name"));
    }
}
