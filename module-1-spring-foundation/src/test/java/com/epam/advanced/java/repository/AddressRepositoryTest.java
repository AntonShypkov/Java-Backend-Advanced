package com.epam.advanced.java.repository;


import com.epam.advanced.java.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository;

    @Test
    void save() {
        //given
        var testAddress = Address.builder()
                .addresName("Travell Agency 'Around the World'")
                .country("Georgia")
                .city("Batumi")
                .street("Tbel_Abuseridze")
                .building("3b")
                .room("Office 4a")
                .build();

        //when
        addressRepository.save(testAddress);

        //then
        var addresses = addressRepository.findAll();

        assertThat(addresses).hasSize(1);
        assertThat(addresses).hasSameElementsAs(List.of(testAddress));
    }
}
