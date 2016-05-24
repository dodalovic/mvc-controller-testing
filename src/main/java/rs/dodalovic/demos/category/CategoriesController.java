package rs.dodalovic.demos.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoryService categoryService;

    @RequestMapping
    public List<String> allCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<String> showCategory(@PathVariable("categoryId") String categoryId) {
        final Optional<String> category = categoryService.getCategory(categoryId);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.badRequest().body(categoryId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@RequestBody Category category) throws
            URISyntaxException {
        return ResponseEntity.created(new URI("http://localhost/categories/1")).body(null);
    }

    @Autowired
    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
