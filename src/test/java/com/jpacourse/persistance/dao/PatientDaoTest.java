package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class PatientDaoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PatientDao patientDao;

    private PatientEntity testPatient;
    private DoctorEntity testDoctor;

    @BeforeEach
    public void setUp() {
        testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setVisits(new ArrayList<>());

        testDoctor = new DoctorEntity();
        testDoctor.setId(2L);
        testDoctor.setFirstName("Alice");
        testDoctor.setLastName("Smith");
    }

    @Test
    public void testAddVisitToPatient_Success() {
        // Мокаємо поведінку EntityManager
        when(entityManager.find(PatientEntity.class, 1L)).thenReturn(testPatient);
        when(entityManager.find(DoctorEntity.class, 2L)).thenReturn(testDoctor);

        // Параметри для методу
        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Annual checkup";

        // Викликаємо метод
        patientDao.addVisitToPatient(1L, 2L, visitTime, description);

        // Перевіряємо, чи візит доданий
        assertEquals(1, testPatient.getVisits().size());
        VisitEntity addedVisit = testPatient.getVisits().get(0);
        assertEquals(description, addedVisit.getDescription());
        assertEquals(visitTime, addedVisit.getTime());
        assertEquals(testDoctor, addedVisit.getDoctor());
        assertEquals(testPatient, addedVisit.getPatient());

        // Перевіряємо, чи був викликаний merge
        verify(entityManager, times(1)).merge(testPatient);
    }

    @Test
    public void testAddVisitToPatient_PatientNotFound() {
        // Мокаємо EntityManager, щоб повернути null для пацієнта
        when(entityManager.find(PatientEntity.class, 1L)).thenReturn(null);

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Annual checkup";

        // Перевіряємо, що метод кидає виняток
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                patientDao.addVisitToPatient(1L, 2L, visitTime, description)
        );

        assertEquals("Patient or Doctor not found", exception.getMessage());
        verify(entityManager, never()).merge(any(PatientEntity.class));
    }

    @Test
    public void testAddVisitToPatient_DoctorNotFound() {
        // Мокаємо EntityManager, щоб повернути null для лікаря
        when(entityManager.find(PatientEntity.class, 1L)).thenReturn(testPatient);
        when(entityManager.find(DoctorEntity.class, 2L)).thenReturn(null);

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Annual checkup";

        // Перевіряємо, що метод кидає виняток
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                patientDao.addVisitToPatient(1L, 2L, visitTime, description)
        );

        assertEquals("Patient or Doctor not found", exception.getMessage());
        verify(entityManager, never()).merge(any(PatientEntity.class));
    }
}
