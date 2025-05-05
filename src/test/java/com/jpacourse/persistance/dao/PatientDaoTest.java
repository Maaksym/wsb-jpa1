package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        testPatient.setIsInsured(true);

        testDoctor = new DoctorEntity();
        testDoctor.setId(2L);
        testDoctor.setFirstName("Alice");
        testDoctor.setLastName("Smith");
    }

    @Test
    public void testAddVisitToPatient_Success() {
        when(entityManager.find(PatientEntity.class, 1L)).thenReturn(testPatient);
        when(entityManager.find(DoctorEntity.class, 2L)).thenReturn(testDoctor);

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Annual checkup";

        patientDao.addVisitToPatient(1L, 2L, visitTime, description);

        assertEquals(1, testPatient.getVisits().size());
        VisitEntity addedVisit = testPatient.getVisits().get(0);
        assertEquals(description, addedVisit.getDescription());
        assertEquals(visitTime, addedVisit.getTime());
        assertEquals(testDoctor, addedVisit.getDoctor());
        assertEquals(testPatient, addedVisit.getPatient());

        verify(entityManager, times(1)).merge(testPatient);
    }

    @Test
    public void testAddVisitToPatient_PatientNotFound() {
        when(entityManager.find(PatientEntity.class, 1L)).thenReturn(null);

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Annual checkup";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                patientDao.addVisitToPatient(1L, 2L, visitTime, description)
        );

        assertEquals("Patient or Doctor not found", exception.getMessage());
        verify(entityManager, never()).merge(any(PatientEntity.class));
    }

    @Test
    public void testAddVisitToPatient_DoctorNotFound() {
        when(entityManager.find(PatientEntity.class, 1L)).thenReturn(testPatient);
        when(entityManager.find(DoctorEntity.class, 2L)).thenReturn(null);

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Annual checkup";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                patientDao.addVisitToPatient(1L, 2L, visitTime, description)
        );

        assertEquals("Patient or Doctor not found", exception.getMessage());
        verify(entityManager, never()).merge(any(PatientEntity.class));
    }

    /**
     * Тест пошуку пацієнтів за прізвищем
     */
    @Test
    public void testFindPatientsByLastName() {
        List<PatientEntity> mockPatients = List.of(testPatient);

        // Мокаємо TypedQuery
        TypedQuery<PatientEntity> queryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PatientEntity.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(mockPatients);

        List<PatientEntity> result = patientDao.findPatientsByLastName("Doe");

        assertFalse(result.isEmpty());
        assertEquals("Doe", result.get(0).getLastName());
    }

    /**
     * Тест пошуку пацієнтів із більше ніж X візитами
     */
    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        testPatient.getVisits().add(new VisitEntity()); // Додаємо візит
        List<PatientEntity> mockPatients = List.of(testPatient);

        TypedQuery<PatientEntity> queryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PatientEntity.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(mockPatients);

        List<PatientEntity> result = patientDao.findPatientsWithMoreThanXVisits(0);

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getVisits().size() > 0);
    }

    /**
     * Тест пошуку пацієнтів за страховим статусом
     */
    @Test
    public void testFindPatientsByInsuranceStatus() {
        List<PatientEntity> mockPatients = List.of(testPatient);

        TypedQuery<PatientEntity> queryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PatientEntity.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(mockPatients);

        List<PatientEntity> result = patientDao.findPatientsByInsuranceStatus(true);

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getIsInsured());
    }
}
