package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AddressDaoTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testShouldFindAddressById() {
        // Створюємо та зберігаємо нову адресу
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Elm Street");
        address.setAddressLine2("Apt 101");
        address.setCity("Springfield");
        address.setPostalCode("12345");

        // Зберігаємо об'єкт у базу
        entityManager.persist(address);
        entityManager.flush();

        // Отримуємо ID адреси
        Long addressId = address.getId();

        // Шукаємо адресу за ID
        AddressEntity foundAddress = entityManager.find(AddressEntity.class, addressId);

        // Перевіряємо, що адреса знайдена
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo("Springfield");
    }
}
