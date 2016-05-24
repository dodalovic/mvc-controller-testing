package rs.dodalovic.demos.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class CategoryService {
    public List<String> getAllCategories() {
        return asList("Category 1", "Category 2", "Category 3");
    }

    public Optional<String> getCategory(String categoryId) {
        return Optional.of("Category " + categoryId);
    }
}
