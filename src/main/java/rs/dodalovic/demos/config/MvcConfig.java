package rs.dodalovic.demos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.dodalovic.demos.category.CategoryRepository;
import rs.dodalovic.demos.category.CategoryService;

@Configuration
public class MvcConfig {
    @Bean
    public CategoryRepository categoryRepository() {
        return new CategoryRepository();
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService(categoryRepository());
    }
}
