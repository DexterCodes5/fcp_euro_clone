package dev.dex.fcpeuro.controller;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebody.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebodyengine.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebodyenginetransmission.*;
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
class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItUtil itUtil;
    @Autowired
    private BaseVehicleRepository baseVehicleRepository;
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleBodyRepository vehicleBodyRepository;
    @Autowired
    private BodyRepository bodyRepository;
    @Autowired
    private EngineRepository engineRepository;
    @Autowired
    private VehicleBodyEngineRepository vehicleBodyEngineRepository;
    @Autowired
    private TransmissionRepository transmissionRepository;
    @Autowired
    private VehicleBodyEngineTransmissionRepository vehicleBodyEngineTransmissionRepository;

    @BeforeEach
    void setUp() {
        itUtil.deleteAll();
    }

    @Test
    void canGetYears() throws Exception {
        // given
        List<BaseVehicle> baseVehicles = new ArrayList<>(List.of(
                new BaseVehicle(1, 2001, 1, "model"),
                new BaseVehicle(2, 2002, 1, "model"),
                new BaseVehicle(3, 2003, 1, "model")
        ));
        baseVehicleRepository.saveAll(baseVehicles);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/years"));

        // then
        String yearsJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Integer> actual = objectMapper.readValue(yearsJson, new TypeReference<>(){});
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(2001, 2002, 2003));
    }

    @Test
    void canGetMakesByYear() throws Exception {
        // given
        Make make = new Make(1, "make");
        Make make2 = new Make(2, "make2");
        Make make3 = new Make(3, "make3");
        makeRepository.save(make);
        makeRepository.save(make2);
        makeRepository.save(make3);

        List<BaseVehicle> baseVehicles = new ArrayList<>(List.of(
                new BaseVehicle(1, 2001, make.getId(), "model"),
                new BaseVehicle(2, 2001, make2.getId(), "model"),
                new BaseVehicle(3, 2001, make3.getId(), "model")
        ));
        baseVehicleRepository.saveAll(baseVehicles);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/makes?year=2001"));

        // then
        String makesJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Make> actual = objectMapper.readValue(makesJson, new TypeReference<>(){});
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(make, make2, make3));
    }

    @Test
    void getBaseVehiclesByYearAndMake() throws Exception {
        // given
        BaseVehicle baseVehicle = new BaseVehicle(1, 2001, 1, "model");
        BaseVehicle baseVehicle2 = new BaseVehicle(2, 2001, 2, "model");
        BaseVehicle baseVehicle3 = new BaseVehicle(3, 2001, 2, "model");
        baseVehicleRepository.save(baseVehicle);
        baseVehicleRepository.save(baseVehicle2);
        baseVehicleRepository.save(baseVehicle3);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/base_vehicles?year=2001&make=2"));

        // then
        String baseVehiclesJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<BaseVehicle> actual = objectMapper.readValue(baseVehiclesJson, new TypeReference<>(){});
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(baseVehicle2, baseVehicle3));
    }

    @Test
    void canGetVehiclesByBaseVehicleId() throws Exception {
        // given
        Vehicle vehicle = new Vehicle(1, 1, "subModel");
        Vehicle vehicle2 = new Vehicle(2, 1, "subModel");
        Vehicle vehicle3 = new Vehicle(3, 2, "subModel");
        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle2);
        vehicleRepository.save(vehicle3);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/vehicles?baseVehicleId=1"));

        // then
        String vehiclesJson = resultActions.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<Vehicle> actual = objectMapper.readValue(vehiclesJson, new TypeReference<>(){});
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(vehicle, vehicle2));
    }

    @Test
    void getBodiesByVehicleId() throws Exception {
        // given
        Body body = new Body(1, "body");
        Body body2 = new Body(2, "body2");
        Body body3 = new Body(3, "body3");
        bodyRepository.save(body);
        bodyRepository.save(body2);
        bodyRepository.save(body3);

        VehicleBody vehicleBody = new VehicleBody(1, body.getId());
        VehicleBody vehicleBody2 = new VehicleBody(1, body2.getId());
        VehicleBody vehicleBody3 = new VehicleBody(2, body3.getId());
        vehicleBodyRepository.save(vehicleBody);
        vehicleBodyRepository.save(vehicleBody2);
        vehicleBodyRepository.save(vehicleBody3);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/bodies?vehicleId=1"));

        // then
        String bodiesJson = resultActions
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Body> actual = objectMapper.readValue(bodiesJson, new TypeReference<>(){});
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(body, body2));
    }

    @Test
    void canGetEnginesByVehicleIdAndBodyId() throws Exception {
        // given
        Engine engine = new Engine(null, "engine");
        Engine engine2 = new Engine(null, "engine2");
        Engine engine3 = new Engine(null, "engine3");
        engineRepository.save(engine);
        engineRepository.save(engine2);
        engineRepository.save(engine3);

        VehicleBodyEngine vehicleBodyEngine = new VehicleBodyEngine(1, 1, engine.getId());
        VehicleBodyEngine vehicleBodyEngine2 = new VehicleBodyEngine(1, 2, engine2.getId());
        VehicleBodyEngine vehicleBodyEngine3 = new VehicleBodyEngine(2, 1, engine3.getId());
        vehicleBodyEngineRepository.save(vehicleBodyEngine);
        vehicleBodyEngineRepository.save(vehicleBodyEngine2);
        vehicleBodyEngineRepository.save(vehicleBodyEngine3);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/engines?vehicleId=1&bodyId=1"));

        // then
        String enginesJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Engine> actual = objectMapper.readValue(enginesJson, new TypeReference<>(){});
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(engine));
    }

    @Test
    void canGetTransmissionsByVehicleIdBodyIdAndEngineId() throws Exception {
        // given
        Transmission transmission = new Transmission(1, "transmission");
        Transmission transmission2 = new Transmission(2, "transmission2");
        Transmission transmission3 = new Transmission(3, "transmission3");
        transmissionRepository.save(transmission);
        transmissionRepository.save(transmission2);
        transmissionRepository.save(transmission3);

        VehicleBodyEngineTransmission vehicleBodyEngineTransmission =
                new VehicleBodyEngineTransmission(1, 1, 1, transmission.getId());
        VehicleBodyEngineTransmission vehicleBodyEngineTransmission2 =
                new VehicleBodyEngineTransmission(1, 1, 1, transmission2.getId());
        VehicleBodyEngineTransmission vehicleBodyEngineTransmission3 =
                new VehicleBodyEngineTransmission(2, 1, 1, transmission3.getId());
        VehicleBodyEngineTransmission vehicleBodyEngineTransmission4 =
                new VehicleBodyEngineTransmission(1, 1, 2, transmission.getId());
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission);
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission2);
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission3);
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission4);

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/vehicles/transmissions?vehicleId=1&bodyId=1&engineId=1"));

        // then
        String transmissionsJson = resultActions.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Object> actual = objectMapper.readValue(transmissionsJson, new TypeReference<>(){});

        Map<String, Object> t = new LinkedHashMap<>();
        t.put("id", transmission.getId() + "," + transmission2.getId());
        t.put("transmission", "Not sure");
        Map<String, Object> t2 = new LinkedHashMap<>();
        t2.put("id", transmission.getId());
        t2.put("transmission", "transmission");
        Map<String, Object> t3 = new LinkedHashMap<>();
        t3.put("id", transmission2.getId());
        t3.put("transmission", "transmission2");
        List<Map> expected = new ArrayList<>(List.of(
                t, t2, t3
        ));

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canIsVehicleValid() throws Exception {
        // given
        String model = "model";
        Make make = new Make(1, "make");
        makeRepository.save(make);

        BaseVehicle baseVehicle = new BaseVehicle(1, 2001, make.getId(), model);
        baseVehicleRepository.save(baseVehicle);

        Vehicle vehicle = new Vehicle(1, baseVehicle.getId(), "subModel");
        vehicleRepository.save(vehicle);

        VehicleBodyEngineTransmission vehicleBodyEngineTransmission =
                new VehicleBodyEngineTransmission(vehicle.getId(), 1, 1, 1);
        VehicleBodyEngineTransmission vehicleBodyEngineTransmission2 =
                new VehicleBodyEngineTransmission(vehicle.getId(), 1, 1, 2);
        VehicleBodyEngineTransmission vehicleBodyEngineTransmission3 =
                new VehicleBodyEngineTransmission(vehicle.getId(), 1, 1, 3);
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission);
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission2);
        vehicleBodyEngineTransmissionRepository.save(vehicleBodyEngineTransmission3);

        // when
        String url = "/api/v1/vehicles/is-vehicle-valid?make=make&model=model&year=2001" +
                "&vehicleId=" + vehicle.getId() + "&bodyId=1&engineId=1&transmissionIds=1,2,3";
        ResultActions resultActions = mockMvc
                .perform(post(url));

        // then
        resultActions.andExpect(status().isOk());
    }
}