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
class PartControllerTest {
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
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private BaseVehicleRepository baseVehicleRepository;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canGetParts() throws Exception {
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
        Part part = new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        Part part2 = new Part(2, "title", "url", "sku", 123, 10, 10.0, "OEM",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        Part part3 = new Part(3, "title", "url", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        partRepository.save(part);
        partRepository.save(part2);
        partRepository.save(part3);

        // when
        String url = "/api/v1/parts?vehicleId=" + vehicle.getId() + "&category=categoryBot&quality=OE&brand=brand";
        ResultActions resultActions = mockMvc
                .perform(get(url));

        // then
        String contentAsString = resultActions
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<PartResponse> actual = objectMapper.readValue(contentAsString, new TypeReference<>(){});
        System.out.println(actual);
        List<PartResponse> expected = new ArrayList<>(List.of(
                new PartResponse(part),
                new PartResponse(part3)
        ));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canGetByUrl() throws Exception {
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
        Part part = new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        Part part2 = new Part(2, "title", "url1", "sku", 123, 10, 10.0, "OEM",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        Part part3 = new Part(3, "title", "url2", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        partRepository.save(part);
        partRepository.save(part2);
        partRepository.save(part3);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/parts/get-part-by-url/url1"));

        // then
        String partJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PartResponse actual = objectMapper.readValue(partJson, PartResponse.class);
        PartResponse expected = new PartResponse(part2);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canGetPartById() throws Exception {
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
        Part part = new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        Part part2 = new Part(2, "title", "url1", "sku", 123, 10, 10.0, "OEM",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        Part part3 = new Part(3, "title", "url2", "sku", 123, 10, 10.0, "OE",
                null, false, null, null, false, null,
                brand, vehicles, new ArrayList<>(), categoryBot, new ArrayList<>());
        partRepository.save(part);
        partRepository.save(part2);
        partRepository.save(part3);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/parts/get-part-by-id/" + part.getId()));

        // then
        String partJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PartResponse actual = objectMapper.readValue(partJson, PartResponse.class);
        PartResponse expected = new PartResponse(part);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}