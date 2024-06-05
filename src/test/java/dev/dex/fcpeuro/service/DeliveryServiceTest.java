package dev.dex.fcpeuro.service;


import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class DeliveryServiceTest {
    private DeliveryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DeliveryService();
    }

    @Test
    void canCalculateDeliveryMethods() {
        // given
        Address address = new Address("firstname", "lastname", "streetAddress",
                "streetAddressContd", "companyName", "city", "Bulgaria",
                "state", "zipCode", "phoneNumber");

        // when
        List<Object> res = underTest.calculateDeliveryMethods(address);

        // then
        assertThat(res.get(0)).isInstanceOf(DeliveryMethodResponse.class);
    }

    @Test
    void willThrowInCalculateDeliveryMethods() {
        // given
        Address address = new Address("firstname", "lastname", "streetAddress",
                "streetAddressContd", "companyName", "city", "country",
                "state", "zipCode", "phoneNumber");

        // when
        // then
        assertThatThrownBy(() -> underTest.calculateDeliveryMethods(address))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Country not implemented: " + address.getCountry());
    }
}