package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private PartService partService;
    private CategoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CategoryService(partService);
    }

    @Test
    void getCategories() {
        // given
        String category = "category";
        int vehicleId = 1;
        String quality = "OE";
        String brand = "brand";
        String searchOnly = "searchOnly";
        CategoryTop categoryTop = new CategoryTop(1, "categoryTop", null);
        CategoryMid categoryMid = new CategoryMid(1, "categoryMid", categoryTop, null);
        CategoryBot categoryBot = new CategoryBot(1, "categoryBot", categoryMid);
        List<Part> parts = new ArrayList<>(List.of(
                new Part(1, "title1", "url", "sku",
                        123, 10, 10.0, "OE", null, false,
                        null, null, false, null, null,
                        null, null, categoryBot, null),
                new Part(2, "title2", "url", "sku", 123, 10, 12.0, "OEM",
                        null, false, null, null, false, null,
                        null, null, null, categoryBot, null),
                new Part(3, "title3", "url", "sku", 123, 10, 11.0,
                        "OEM", null, false, null, null, false,
                        null, null, null, null, categoryBot,
                        null)));

        given(partService.findByCategoryAndVehicleIdAndQualityAndBrandAndSearchOnly(anyString(), anyInt(),
                anyString(), anyString(), anyString()))
                .willReturn(parts);

        // when
        List<CategoryResponse> res = underTest.getCategories(category, vehicleId, quality, brand, searchOnly);

        // then
        List<CategoryResponse> expected = new ArrayList<>(List.of(
                new CategoryResponse("categoryTop",
                        List.of(new CategoryMidResponse("categoryMid",
                                List.of("categoryBot")))
                        )
        ));
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}