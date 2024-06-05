package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebody.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebodyengine.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebodyenginetransmission.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {
    @Mock
    private MakeRepository makeRepository;
    @Mock
    private BaseVehicleRepository baseVehicleRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private BodyRepository bodyRepository;
    @Mock
    private VehicleBodyRepository vehicleBodyRepository;
    @Mock
    private VehicleBodyEngineRepository vehicleBodyEngineRepository;
    @Mock
    private EngineRepository engineRepository;
    @Mock
    private VehicleBodyEngineTransmissionRepository vehicleBodyEngineTransmissionRepository;
    @Mock
    private TransmissionRepository transmissionRepository;
    private VehicleService underTest;

    @BeforeEach
    void setUp() {
        underTest = new VehicleService(makeRepository, baseVehicleRepository, vehicleRepository,
                bodyRepository, vehicleBodyRepository, vehicleBodyEngineRepository, engineRepository,
                vehicleBodyEngineTransmissionRepository, transmissionRepository);
    }

    @Test
    void canFindYears() {
        // given
        List<BaseVehicle> baseVehicles = new ArrayList<>(List.of(
                new BaseVehicle(1, 2001, 1, "model"),
                new BaseVehicle(2, 2001, 1, "model"),
                new BaseVehicle(3, 2002, 1, "model")
        ));

        given(baseVehicleRepository.findAll())
                .willReturn(baseVehicles);

        // when
        List<Integer> res = underTest.findYears();

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(new ArrayList<Integer>(List.of(2001, 2002)));
    }

    @Test
    void canFindMakesByYear() {
        // given
        int year = 2001;
        List<BaseVehicle> baseVehicles = new ArrayList<>(List.of(
                new BaseVehicle(1, 2001, 1, "model"),
                new BaseVehicle(2, 2001, 2, "model"),
                new BaseVehicle(3, 2002, 2, "model")
        ));

        given(baseVehicleRepository.findByYear(anyInt()))
                .willReturn(baseVehicles);

        // when
        underTest.findMakesByYear(year);

        // then
        ArgumentCaptor<List<Integer>> idsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(makeRepository).findAllById(idsArgumentCaptor.capture());
        assertThat(idsArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new ArrayList<>(List.of(1,2)));
    }

    @Test
    void canFindBaseVehiclesByYearAndMakeId() {
        // given
        int year = 2001;
        int makeId = 1;


        // when
        underTest.findBaseVehiclesByYearAndMakeId(year, makeId);

        // then
        verify(baseVehicleRepository).findByYearAndMakeId(year, makeId);
    }

    @Test
    void canFindVehiclesByBaseVehicleId() {
        // given
        int baseVehicleId = 1;

        // when
        underTest.findVehiclesByBaseVehicleId(baseVehicleId);

        // then
        verify(vehicleRepository).findByBaseVehicleId(baseVehicleId);
    }

    @Test
    void canFindBodiesByVehicleId() {
        // given
        int vehicleId = 1;
        List<VehicleBody> vehicleBodies = new ArrayList<>(List.of(
                new VehicleBody(vehicleId, 1),
                new VehicleBody(vehicleId, 2),
                new VehicleBody(vehicleId, 3)
        ));

        given(vehicleBodyRepository.findByVehicleId(anyInt()))
                .willReturn(vehicleBodies);

        // when
        underTest.findBodiesByVehicleId(vehicleId);

        // then
        verify(bodyRepository).findAllById(new ArrayList<>(List.of(1,2,3)));
    }

    @Test
    void canFindEnginesByVehicleIdAndBodyId() {
        // given
        int vehicleId = 1;
        int bodyId = 1;
        List<VehicleBodyEngine> vehicleBodyEngines = new ArrayList<>(List.of(
                new VehicleBodyEngine(vehicleId, bodyId, 1),
                new VehicleBodyEngine(vehicleId, bodyId, 2),
                new VehicleBodyEngine(vehicleId, bodyId, 3)
        ));

        given(vehicleBodyEngineRepository.findByVehicleIdAndBodyId(anyInt(), anyInt()))
                .willReturn(vehicleBodyEngines);

        // when
        underTest.findEnginesByVehicleIdAndBodyId(vehicleId, bodyId);

        // then
        verify(engineRepository).findAllById(new ArrayList<>(List.of(1,2,3)));
    }

    @Test
    void canFindTransmissionsByVehicleIdBodyIdAndEngineId() {
        // given
        int vehicleId = 1;
        int bodyId = 1;
        int engineId = 1;
        List<VehicleBodyEngineTransmission> vehicleBodyEngineTransmissions = new ArrayList<>(List.of(
                new VehicleBodyEngineTransmission(vehicleId, bodyId, engineId, 1),
                new VehicleBodyEngineTransmission(vehicleId, bodyId, engineId, 2),
                new VehicleBodyEngineTransmission(vehicleId, bodyId, engineId, 3)
        ));
        List<Transmission> transmissions = new ArrayList<>(List.of(
                new Transmission(1, "transmission"),
                new Transmission(2, "transmission1"),
                new Transmission(3, "transmission2")
        ));

        given(vehicleBodyEngineTransmissionRepository.findByVehicleIdAndBodyIdAndEngineId(anyInt(),
                anyInt(), anyInt()))
                .willReturn(vehicleBodyEngineTransmissions);
        given(transmissionRepository.findAllById(any()))
                .willReturn(transmissions);

        // when
        List<Object> res = underTest.findTransmissionsByVehicleIdBodyIdAndEngineId(vehicleId, bodyId, engineId);

        // then
        List<Integer> transmissionIds = new ArrayList<>(List.of(1,2,3));
        String transmissionIdsStr = transmissionIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        Transmission.TransmissionNotSure transmissionNotSure =
                new Transmission.TransmissionNotSure(transmissionIdsStr, "Not sure");
        List<Object> expected = new ArrayList<>(transmissions);
        expected.add(0, transmissionNotSure);

        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void canIsVehicleValid() {
        // given
        String make = "make";
        String model = "model";
        int year = 2001;
        int vehicleId = 1;
        int bodyId = 1;
        int engineId = 1;
        List<Integer> transmissionIds = new ArrayList<>(List.of(1,2,3));

        Make make1 = new Make(1, make);
        BaseVehicle baseVehicle = new BaseVehicle(1, 2001, 1, "model");
        Vehicle vehicle = new Vehicle(1, 1, "subModel");
        List<VehicleBodyEngineTransmission> vehicleBodyEngineTransmissions =
                new ArrayList<>(List.of(
                        new VehicleBodyEngineTransmission(vehicleId, bodyId, engineId, 1),
                        new VehicleBodyEngineTransmission(vehicleId, bodyId, engineId, 2),
                        new VehicleBodyEngineTransmission(vehicleId, bodyId, engineId, 3)
                ));

        given(makeRepository.findByMake(anyString()))
                .willReturn(Optional.of(make1));
        given(baseVehicleRepository.findByYearAndMakeIdAndModel(anyInt(), anyInt(), anyString()))
                .willReturn(Optional.of(baseVehicle));
        given(vehicleRepository.findByIdAndBaseVehicleId(anyInt(), anyInt()))
                .willReturn(Optional.of(vehicle));
        given(vehicleBodyEngineTransmissionRepository
                .findByVehicleIdAndBodyIdAndEngineIdAndTransmissionIdIn(anyInt(), anyInt(), anyInt(),
                        any()))
                .willReturn(vehicleBodyEngineTransmissions);

        // when
        // then
        underTest.isVehicleValid(make, model, year, vehicleId, bodyId, engineId, transmissionIds);
    }

    @Test
    void isVehicleValidWillThrowWhenMakeNotFound() {
        // given
        String make = "make";
        String model = "model";
        int year = 2001;
        int vehicleId = 1;
        int bodyId = 1;
        int engineId = 1;
        List<Integer> transmissionIds = new ArrayList<>(List.of(1,2,3));

        // when
        // then
        assertThatThrownBy(() -> underTest.isVehicleValid(make, model, year, vehicleId, bodyId,
                engineId, transmissionIds))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Make " + make + " not found");
        verify(baseVehicleRepository, never()).findByYearAndMakeIdAndModel(anyInt(), anyInt(), anyString());
        verify(vehicleRepository, never()).findByIdAndBaseVehicleId(anyInt(), anyInt());
        verify(vehicleBodyEngineTransmissionRepository, never())
                .findByVehicleIdAndBodyIdAndEngineIdAndTransmissionIdIn(anyInt(), anyInt(), anyInt(), any());
    }

    @Test
    void isVehicleValidWillThrowWhenBaseVehicleNotFound() {
        // given
        String make = "make";
        String model = "model";
        int year = 2001;
        int vehicleId = 1;
        int bodyId = 1;
        int engineId = 1;
        List<Integer> transmissionIds = new ArrayList<>(List.of(1,2,3));

        Make make1 = new Make(1, make);

        given(makeRepository.findByMake(anyString()))
                .willReturn(Optional.of(make1));

        // when
        // then
        assertThatThrownBy(() -> underTest.isVehicleValid(make, model, year, vehicleId, bodyId,
                engineId, transmissionIds))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BaseVehicle with year=" + year
                        + ", makeId=" + make1.getId() + ", model=" + model + " not found");
        verify(vehicleRepository, never()).findByIdAndBaseVehicleId(anyInt(), anyInt());
        verify(vehicleBodyEngineTransmissionRepository, never())
                .findByVehicleIdAndBodyIdAndEngineIdAndTransmissionIdIn(anyInt(), anyInt(), anyInt(), any());
    }

    @Test
    void isVehicleValidWillThrowWhenVehicleNotFound() {
        // given
        String make = "make";
        String model = "model";
        int year = 2001;
        int vehicleId = 1;
        int bodyId = 1;
        int engineId = 1;
        List<Integer> transmissionIds = new ArrayList<>(List.of(1,2,3));

        Make make1 = new Make(1, make);
        BaseVehicle baseVehicle = new BaseVehicle(1, 2001, 1, "model");

        given(makeRepository.findByMake(anyString()))
                .willReturn(Optional.of(make1));
        given(baseVehicleRepository.findByYearAndMakeIdAndModel(anyInt(), anyInt(), anyString()))
                .willReturn(Optional.of(baseVehicle));

        // when
        // then
        assertThatThrownBy(() -> underTest.isVehicleValid(make, model, year, vehicleId, bodyId,
                engineId, transmissionIds))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Vehicle with id=" + vehicleId
                        + ", baseVehicleId=" + baseVehicle.getId() + " not found");
        verify(vehicleBodyEngineTransmissionRepository, never())
                .findByVehicleIdAndBodyIdAndEngineIdAndTransmissionIdIn(anyInt(), anyInt(), anyInt(), any());
    }

    @Test
    void isVehicleValidWillThrowWhenVehicleBodyEngineTransmissionInvalid() {
        // given
        String make = "make";
        String model = "model";
        int year = 2001;
        int vehicleId = 1;
        int bodyId = 1;
        int engineId = 1;
        List<Integer> transmissionIds = new ArrayList<>(List.of(1,2,3));

        Make make1 = new Make(1, make);
        BaseVehicle baseVehicle = new BaseVehicle(1, 2001, 1, "model");
        Vehicle vehicle = new Vehicle(1, 1, "subModel");

        given(makeRepository.findByMake(anyString()))
                .willReturn(Optional.of(make1));
        given(baseVehicleRepository.findByYearAndMakeIdAndModel(anyInt(), anyInt(), anyString()))
                .willReturn(Optional.of(baseVehicle));
        given(vehicleRepository.findByIdAndBaseVehicleId(anyInt(), anyInt()))
                .willReturn(Optional.of(vehicle));

        // when
        // then
        assertThatThrownBy(() -> underTest.isVehicleValid(make, model, year, vehicleId, bodyId,
                engineId, transmissionIds))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("VehicleBodyEngineTransmission with ids=" + transmissionIds
                        + " not found");
    }
}