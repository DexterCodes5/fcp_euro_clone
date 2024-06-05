package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.repo.*;
import dev.dex.fcpeuro.repo.category.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItUtil itUtil;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CategoryTopRepository categoryTopRepository;
    @Autowired
    private CategoryMidRepository categoryMidRepository;
    @Autowired
    private CategoryBotRepository categoryBotRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private PartRepository partRepository;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canGetCategories() throws Exception {
        // given
        Vehicle vehicle = new Vehicle(1, 1, "subModel");
        vehicleRepository.save(vehicle);

        CategoryTop categoryTop = new CategoryTop(1, "categoryTop", null);
        categoryTopRepository.save(categoryTop);

        CategoryMid categoryMid = new CategoryMid(1, "categoryMid", categoryTop, null);
        categoryMidRepository.save(categoryMid);

        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", categoryMid);
        categoryBotRepository.save(categoryBot);

        Brand brand = new Brand(1, "brand", null, null);
        brandRepository.save(brand);

        List<Vehicle> vehicles = new ArrayList<>(List.of(vehicle));
        List<Part> parts = new ArrayList<>(List.of(
                new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE",
                        null, false, null, null, false, null,
                        brand, vehicles, null, categoryBot, null),
                new Part(2, "title", "url", "sku", 123, 10, 10.0, "OEM",
                        null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null)
        ));
        partRepository.saveAll(parts);

        // when
        String url = "/api/v1/categories?vehicleId=" + vehicle.getId() + "&category=categoryBot"
                + "&quality=OE&brand=brand";
        ResultActions resultActions = mockMvc
                .perform(get(url));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<CategoryResponse> actual = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        List<CategoryResponse> expected = new ArrayList<>(List.of(
                new CategoryResponse("categoryTop", new ArrayList<>(List.of(
                        new CategoryMidResponse("categoryMid", List.of("categoryBot"))
                )))));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}