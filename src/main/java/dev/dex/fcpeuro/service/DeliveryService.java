package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    public List<Object> calculateDeliveryMethods(Address address) {
        if (Objects.equals(address.getCountry(), "Bulgaria")) {
            LocalDate today = LocalDate.now();
            LocalDate receiveDate;
            if (today.getDayOfWeek() == DayOfWeek.SATURDAY) {
                receiveDate = today.plusDays(3);
            } else if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
                receiveDate = today.plusDays(2);
            } else {
                receiveDate = today.plusDays(1);
            }
            return List.of(new DeliveryMethodResponse(418.04, receiveDate));
        }
        throw new BadRequestException("Country not implemented: " + address.getCountry());
    }
}
