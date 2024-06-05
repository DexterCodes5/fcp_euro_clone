package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.model.fitment.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
class FitmentControllerTest {
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
    void canDoesPartFitVehicle() throws Exception {
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
                        brand, vehicles, null, categoryBot, null);
        partRepository.save(part);

        // when
        String url = "/api/v1/fitment/does-part-fit-vehicle?partId=" + part.getId() + "&vehicleId=" + vehicle.getId();
        ResultActions resultActions = mockMvc
                .perform(get(url));

        // then
        String contentAsString = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo("true");
    }

    @Test
    void fitment() throws Exception {
        // given
        Make make = new Make(1, "make");
        makeRepository.save(make);

        BaseVehicle baseVehicle = new BaseVehicle(1, 2001, make.getId(), "model");
        baseVehicleRepository.save(baseVehicle);

        Vehicle vehicle = new Vehicle(1, baseVehicle.getId(), "subModel");
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
                brand, vehicles, null, categoryBot, null);
        partRepository.save(part);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/fitment?partId="+part.getId()));

        // then
        String contentAsString = resultActions
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<FitmentResponse> actual = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        List<FitmentResponse> expected = new ArrayList<>(List.of(
                new FitmentResponse("make", "model", new ArrayList<>(List.of(
                        new FitmentVehicle(baseVehicle.getId(), 2001, "make", "model", "")
                )))
        ));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}