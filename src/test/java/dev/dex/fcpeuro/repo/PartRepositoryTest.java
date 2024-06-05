package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.repo.category.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class PartRepositoryTest {
    @Autowired
    private PartRepository underTest;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CategoryTopRepository categoryTopRepository;
    @Autowired
    private CategoryMidRepository categoryMidRepository;
    @Autowired
    private CategoryBotRepository categoryBotRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        brandRepository.deleteAll();
        vehicleRepository.deleteAll();
        categoryBotRepository.deleteAll();
        categoryMidRepository.deleteAll();
        categoryTopRepository.deleteAll();
    }

    @Test
    void canFindByCategoryAndVehicleIdAndQualityAndBrandAndKit() {
        // given
        Brand brand = new Brand(1, "brand", null, null);
        brandRepository.save(brand);
        List<Vehicle> vehicles = new ArrayList<>(List.of(new Vehicle(1, 1, "subModel")));
        vehicleRepository.save(vehicles.get(0));
        CategoryTop categoryTop = new CategoryTop(1, "categoryTop", null);
        categoryTopRepository.save(categoryTop);
        CategoryMid categoryMid = new CategoryMid(1, "categoryMid", categoryTop, null);
        categoryMidRepository.save(categoryMid);
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", categoryMid);
        categoryBotRepository.save(categoryBot);
        List<Part> parts = new ArrayList<>(List.of(
                new Part(1, "title", "url", "sku", 123, 10, 10.0, "OE", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null),
                new Part(2, "title", "url", "sku", 123, 10, 10.0, "OEM", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null)
        ));
        underTest.saveAll(parts);

        // when
        List<Part> res = underTest.findByCategoryAndVehicleIdAndQualityAndBrandAndKit("categoryBot", 1,
                "OE", "brand", false);

        // then
        assertThat(res.get(0))
                .usingRecursiveComparison()
                .isEqualTo(parts.get(0));
    }

    @Test
    void canFindByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByAsc() {
        // given
        Brand brand = new Brand(1, "brand", null, null);
        brandRepository.save(brand);
        Vehicle vehicle = new Vehicle(1, 1, "subModel");
        vehicleRepository.save(vehicle);
        List<Vehicle> vehicles = new ArrayList<>(List.of(vehicle));
        CategoryTop categoryTop = new CategoryTop(1, "categoryTop", null);
        categoryTopRepository.save(categoryTop);
        CategoryMid categoryMid = new CategoryMid(1, "categoryMid", categoryTop, null);
        categoryMidRepository.save(categoryMid);
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", categoryMid);
        categoryBotRepository.save(categoryBot);
        List<Part> parts = new ArrayList<>(List.of(
                new Part(1, "title1", "url", "sku", 123, 10, 10.0, "OE", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null),
                new Part(2, "title2", "url", "sku", 123, 10, 12.0, "OEM", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null),
                new Part(3, "title3", "url", "sku", 123, 10, 11.0, "OEM", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null)
        ));
        underTest.saveAll(parts);

        // when
        List<Part> res = underTest.findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByAsc("categoryBot", vehicle.getId(),
                "OEM", "brand", false);

        // then
        assertThat(res.get(0))
                .usingRecursiveComparison()
                .isEqualTo(parts.get(2));
        assertThat(res.get(1))
                .usingRecursiveComparison()
                .isEqualTo(parts.get(1));
    }

    @Test
    void canFindByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByDesc() {
        // given
        Brand brand = new Brand(1, "brand", null, null);
        brandRepository.save(brand);
        Vehicle vehicle = new Vehicle(1, 1, "subModel");
        vehicleRepository.save(vehicle);
        List<Vehicle> vehicles = new ArrayList<>(List.of(vehicle));
        CategoryTop categoryTop = new CategoryTop(1, "categoryTop", null);
        categoryTopRepository.save(categoryTop);
        CategoryMid categoryMid = new CategoryMid(1, "categoryMid", categoryTop, null);
        categoryMidRepository.save(categoryMid);
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", categoryMid);
        categoryBotRepository.save(categoryBot);
        List<Part> parts = new ArrayList<>(List.of(
                new Part(1, "title1", "url", "sku", 123, 10, 10.0, "OE", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null),
                new Part(2, "title2", "url", "sku", 123, 10, 12.0, "OEM", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null),
                new Part(3, "title3", "url", "sku", 123, 10, 11.0, "OEM", null, false, null, null, false, null, brand,
                        vehicles, null, categoryBot, null)
        ));
        underTest.saveAll(parts);

        // when
        List<Part> res = underTest.findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByDesc("categoryBot",
                vehicle.getId(), "OEM", "brand", false);

        // then
        assertThat(res.get(0))
                .usingRecursiveComparison()
                .isEqualTo(parts.get(1));
        assertThat(res.get(1))
                .usingRecursiveComparison()
                .isEqualTo(parts.get(2));
    }
}