package com.jpacourse.persistance.dao;

import com.jpacourse.service.PatientService;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.repository.PatientRepository;
import com.jpacourse.persistance.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void testDeletePatient() {
        // Мокаємо метод existsById для перевірки, чи існує пацієнт
        when(patientRepository.existsById(1L)).thenReturn(true);

        // Створюємо тестового пацієнта з порожнім списком візитів
        PatientEntity testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setVisits(new ArrayList<>());

        // Мокаємо метод findById, щоб повернути тестового пацієнта
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));

        // Викликаємо метод видалення пацієнта
        patientService.deletePatient(1L);

        // Перевіряємо, чи був викликаний метод deleteById
        verify(patientRepository, times(1)).deleteById(1L);

        // Переконуємося, що список візитів очищений
        assertTrue(testPatient.getVisits().isEmpty());
    }

    @Test
    public void testGetPatientById() {
        // Створюємо тестового пацієнта з значенням для нового поля isInsured
        PatientEntity testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setIsInsured(true);

        // Мокаємо метод findById, щоб повернути тестового пацієнта
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));

        // Викликаємо метод отримання пацієнта за ID
        PatientTO result = patientService.getPatientById(1L);

        // Перевіряємо, що результат не є null
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.getIsInsured()); // Перевірка нового поля

        // Перевіряємо, чи був викликаний метод findById
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPatientById_NotFound() {
        // Мокаємо метод findById, щоб повернути порожній результат
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Викликаємо метод і очікуємо RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientById(1L);
        });

        // Перевіряємо повідомлення винятка
        assertEquals("Пацієнт із ID 1 не знайдений", exception.getMessage());

        // Перевіряємо, чи був викликаний метод findById
        verify(patientRepository, times(1)).findById(1L);
    }
}
