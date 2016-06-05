package rs.dodalovic.demos.category;

import java.util.List;
import java.util.Optional;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    List<String> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    Optional<Category> getCategory(int categoryId) {
        return categoryRepository.getCategory(categoryId);
    }

    Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
