package rs.dodalovic.demos.category;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rs.dodalovic.demos.DemoApplication;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class CategoriesControllerTest {
    private MockMvc mockMvc;
    private CategoryService categoryService;

    @Before
    public void beforeEachTest() {
        categoryService = mock(CategoryService.class);
        given(categoryService.getAllCategories()).willReturn(asList("Category 1", "Category 2", "Category 3"));
        mockMvc = MockMvcBuilders.standaloneSetup(new CategoriesController(categoryService)).build();
    }

    @Test
    public void returnsAllCategories() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0]", Matchers.equalTo("Category 1")))
                .andExpect(jsonPath("$[1]", Matchers.equalTo("Category 2")))
                .andExpect(jsonPath("$[2]", Matchers.equalTo("Category 3")));
    }

    @Test
    public void returnsLimitedNumberOfAllCategories() throws Exception {
        /* when */
        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/categories").param("limit", "2")
                        .accept(MediaType.APPLICATION_JSON_VALUE));
        /* then */
        resultActions.andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void asserts404ForNotExistingCategory() throws Exception {
        given(categoryService.getCategory("-1")).willReturn(Optional.empty());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void assertsCategoryFound() throws Exception {
        given(categoryService.getCategory("1")).willReturn(Optional.of("Category 1"));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("Category 1")));
    }

    @Test
    public void postForbiddenToGetCategory() throws Exception {
        given(categoryService.getCategory("1")).willReturn(Optional.of("Category 1"));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/categories/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void createsCategory() throws Exception {
        final Category category = mock(Category.class);
        given(category.getId()).willReturn(1);
        given(categoryService.saveCategory(any())).willReturn(category);
        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Category\"}")
        ).andExpect(status().isCreated())
                .andExpect(header().string("location", Matchers.containsString("/categories/1")));
    }
}