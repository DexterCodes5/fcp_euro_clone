package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.repo.*;
import dev.dex.fcpeuro.repo.auth.*;
import dev.dex.fcpeuro.repo.category.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@RequiredArgsConstructor
public class ItUtil {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final UserRepository userRepository;
    private final CategoryBotRepository categoryBotRepository;
    private final CategoryMidRepository categoryMidRepository;
    private final CategoryTopRepository categoryTopRepository;
    private final BaseVehicleRepository baseVehicleRepository;
    private final BodyRepository bodyRepository;
    private final EngineRepository engineRepository;
    private final MakeRepository makeRepository;
    private final TransmissionRepository transmissionRepository;
    private final VehicleBodyEngineRepository vehicleBodyEngineRepository;
    private final VehicleBodyEngineTransmissionRepository vehicleBodyEngineTransmissionRepository;
    private final VehicleBodyRepository vehicleBodyRepository;
    private final VehicleRepository vehicleRepository;
    private final BrandRepository brandRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final PartRepository partRepository;
    private final ReviewRepository reviewRepository;

    public void deleteAll() {
        accessTokenRepository.deleteAll();
        refreshTokenRepository.deleteAll();
        resetPasswordTokenRepository.deleteAll();
        reviewRepository.deleteAll();
        customerOrderRepository.deleteAll();
        userRepository.deleteAll();
        partRepository.deleteAll();
        brandRepository.deleteAll();
        vehicleBodyEngineTransmissionRepository.deleteAll();
        vehicleBodyEngineRepository.deleteAll();
        vehicleBodyRepository.deleteAll();
        vehicleRepository.deleteAll();
        bodyRepository.deleteAll();
        transmissionRepository.deleteAll();
        makeRepository.deleteAll();
        baseVehicleRepository.deleteAll();
        engineRepository.deleteAll();
        categoryBotRepository.deleteAll();
        categoryMidRepository.deleteAll();
        categoryTopRepository.deleteAll();
    }

}
