package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.fitment.*;
import dev.dex.fcpeuro.repo.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FitmentServiceTest {
    @Mock
    private PartRepository partRepository;
    @Mock
    private BaseVehicleRepository baseVehicleRepository;
    @Mock
    private MakeRepository makeRepository;
    private FitmentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FitmentService(partRepository, baseVehicleRepository, makeRepository);
    }

    @Test
    void canDoesPartFitVehicle() {
        // given
        int partId = 1;
        int vehicleId = 1;
        List<Vehicle> vehicles = new ArrayList<>(List.of(
                new Vehicle(vehicleId, 1, "subModel")
        ));
        Part part = new Part(partId, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, vehicles, null, null, null);

        given(partRepository.findById(anyInt()))
                .willReturn(Optional.of(part));

        // when
        Boolean res = underTest.doesPartFitVehicle(partId, vehicleId);

        // then
        assertThat(res).isTrue();
    }

    @Test
    void willReturnFalseInDoesPartFitVehicleWhenVehiclesAreNull() {
        // given
        int partId = 1;
        int vehicleId = 1;

        // when
        Boolean res = underTest.doesPartFitVehicle(partId, vehicleId);

        // then
        assertThat(res).isFalse();
    }

    @Test
    void willReturnFalseInDoesPartFitVehicleWhenVehicleIdNotFound() {
        // given
        int partId = 1;
        int vehicleId = 1;
        List<Vehicle> vehicles = new ArrayList<>(List.of(
                new Vehicle(2, 1, "subModel")
        ));
        Part part = new Part(partId, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, vehicles, null, null, null);

        given(partRepository.findById(anyInt()))
                .willReturn(Optional.of(part));

        // when
        Boolean res = underTest.doesPartFitVehicle(partId, vehicleId);

        // then
        assertThat(res).isFalse();
    }

    @Test
    void canFitment() {
        // given
        int partId = 1;
        List<Vehicle> vehicles = new ArrayList<>(List.of(
                new Vehicle(1, 1, "subModel")
        ));
        Part part = new Part(partId, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, vehicles, null, null, null);
        List<BaseVehicle> baseVehicles = new ArrayList<>(List.of(
                new BaseVehicle(1, 2024, 1, "model")
        ));
        Make make = new Make(1, "make");

        given(partRepository.findById(anyInt()))
                .willReturn(Optional.of(part));
        given(baseVehicleRepository.findAllById(any()))
                .willReturn(baseVehicles);
        given(makeRepository.findById(anyInt()))
                .willReturn(Optional.of(make));

        // when
        List<FitmentResponse> res = underTest.fitment(partId);

        // then
        List<FitmentResponse> expected = new ArrayList<>(List.of(
                new FitmentResponse("make", "model",
                        new ArrayList<>(List.of(
                                new FitmentVehicle(1, 2024, "make", "model", "")
                        )))
        ));
        assertThat(res).
                usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void willThrowInFitmentWhenPartIdInvalid() {
        // given
        int partId = 1;

        // when
        // then
        assertThatThrownBy(() -> underTest.fitment(partId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Part with id "  + partId + " doesn't exist");
    }

    @Test
    void willThrowInFitmentWhenMakeInvalid() {
        // given
        int partId = 1;
        List<Vehicle> vehicles = new ArrayList<>(List.of(
                new Vehicle(1, 1, "subModel")
        ));
        Part part = new Part(partId, "title1", "url", "sku", 123, 10, 10.0,
                "OE", null, false, null, null, false,
                null, null, vehicles, null, null, null);
        List<BaseVehicle> baseVehicles = new ArrayList<>(List.of(
                new BaseVehicle(1, 2024, 1, "model")
        ));

        given(partRepository.findById(anyInt()))
                .willReturn(Optional.of(part));
        given(baseVehicleRepository.findAllById(any()))
                .willReturn(baseVehicles);

        // when
        // then
        assertThatThrownBy(() -> underTest.fitment(partId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid make id: " + 1);
    }
}