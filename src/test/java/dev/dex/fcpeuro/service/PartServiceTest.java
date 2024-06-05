package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.entity.kitpart.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.repo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {
    @Mock
    private PartRepository partRepository;
    private PartService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PartService(partRepository);
    }

    @Test
    void canGetPartResponsesWithoutOrder() {
        // given
        String category = "category";
        int vehicleId = 1;
        String quality = "OE";
        String brand = "brand";
        String searchOnly = "search-only";
        String order = null;
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", null);
        List<Part> parts = new ArrayList(List.of(
                new Part(1, "title1", "url", "sku", 123, 10, 10.0,
                        "OE", null, false, null, null, false,
                        null, null, null, new ArrayList<>(), categoryBot,
                        new ArrayList<>())
        ));

        given(partRepository.findByCategoryAndVehicleIdAndQualityAndBrandAndKit(anyString(), anyInt(), anyString(),
                anyString(), anyBoolean()))
                .willReturn(parts);

        // when
        List<PartResponse> res = underTest.getPartResponses(category, vehicleId, quality, brand, searchOnly, order);

        // then
        List<PartResponse> expected = parts.stream().map(PartResponse::new).toList();
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canGetPartResponsesWithOrderAscend() {
        // given
        String category = "category";
        int vehicleId = 1;
        String quality = "OE";
        String brand = "brand";
        String searchOnly = "search-only";
        String order = "ascend_by_price";
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", null);
        List<Part> parts = new ArrayList(List.of(
                new Part(1, "title1", "url", "sku", 123, 10, 10.0,
                        "OE", null, false, null, null, false,
                        null, null, null, new ArrayList<>(), categoryBot,
                        new ArrayList<>())
        ));

        given(partRepository.findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByAsc(anyString(), anyInt(), anyString(),
                anyString(), anyBoolean()))
                .willReturn(parts);

        // when
        List<PartResponse> res = underTest.getPartResponses(category, vehicleId, quality, brand, searchOnly, order);

        // then
        List<PartResponse> expected = parts.stream().map(PartResponse::new).toList();
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canGetPartResponsesWithOrderDesc() {
        // given
        String category = "category";
        int vehicleId = 1;
        String quality = "OE";
        String brand = "brand";
        String searchOnly = "search-only";
        String order = "descend_by_price";
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", null);
        List<Part> parts = new ArrayList(List.of(
                new Part(1, "title1", "url", "sku", 123, 10, 10.0,
                        "OE", null, false, null, null, false,
                        null, null, null, new ArrayList<>(), categoryBot,
                        new ArrayList<>())
        ));

        given(partRepository.findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByDesc(anyString(), anyInt(), anyString(),
                anyString(), anyBoolean()))
                .willReturn(parts);

        // when
        List<PartResponse> res = underTest.getPartResponses(category, vehicleId, quality, brand, searchOnly, order);

        // then
        List<PartResponse> expected = parts.stream().map(PartResponse::new).toList();
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canFindByUrl() {
        // given
        String url = "url";
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", null);
        Part part1 = new Part(2, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, null, new ArrayList<>(), categoryBot,
                new ArrayList<>());
        Part part2 = new Part(3, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, null, new ArrayList<>(), categoryBot,
                new ArrayList<>());
        KitPart kitPart1 = new KitPart(1, 1, part1, 1);
        KitPart kitPart2 = new KitPart(2, 1, part2, 1);
        Part part = new Part(1, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, null, new ArrayList<>(), categoryBot,
                new ArrayList<>(List.of(
                        kitPart2, kitPart1
                )));

        given(partRepository.findByUrl(anyString()))
                .willReturn(Optional.of(part));

        // when
        PartResponse res = underTest.findByUrl(url);

        // then
        PartResponse expected = new PartResponse(part);
        expected.getKitParts().stream()
                .sorted((kp1, kp2) -> kp1.getId().compareTo(kp2.getId()))
                .toList();
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void findByUrlWillThrowWhenUrlInvalid() {
        // given
        String url = "url";

        // when
        // then
        assertThatThrownBy(() -> underTest.findByUrl(url))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Url not found " + url);
    }

    @Test
    void canFindById() {
        // given
        int id = 1;
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", null);
        Part part = new Part(1, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, null, new ArrayList<>(), categoryBot,
                new ArrayList<>());

        given(partRepository.findById(anyInt()))
                .willReturn(Optional.of(part));

        // when
        PartResponse res = underTest.findById(id);

        // then
        PartResponse expected = new PartResponse(part);
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void findByIdWillThrowWhenIdInvalid() {
        // given
        int id = 1;

        // when
        // then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Part id not found " + id);
    }
}