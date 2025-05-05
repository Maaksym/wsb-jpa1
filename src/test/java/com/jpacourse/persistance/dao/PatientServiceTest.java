package com.jpacourse.persistance.dao;

import com.jpacourse.service.PatientService;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private PatientService patientService;

    private PatientEntity testPatient;

    @BeforeEach
    public void setUp() {
        testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setIsInsured(true);
    }

    @Test
    public void testDeletePatient() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));

        PatientTO result = patientService.getPatientById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.getIsInsured());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPatientById_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                patientService.getPatientById(1L));

        assertEquals("Пацієнт із ID 1 не знайдений", exception.getMessage());
        verify(patientRepository, times(1)).findById(1L);
    }

    /**
     * 2. Тест JPQL-запиту: Знайти всі візити пацієнта за його ID
     */
    @Test
    public void testFindVisitsByPatientId() {
        List<VisitEntity> mockVisits = List.of(new VisitEntity());
        when(patientDao.findVisitsByPatientId(1L)).thenReturn(mockVisits);

        List<VisitTO> result = patientService.findVisitsByPatientId(1L);

        assertFalse(result.isEmpty());
        verify(patientDao, times(1)).findVisitsByPatientId(1L);
    }

    /**
     * 3. Тест JPQL-запиту: Знайти пацієнтів із більше ніж X візитами
     */
    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        List<PatientEntity> mockPatients = List.of(testPatient);
        when(patientDao.findPatientsWithMoreThanXVisits(2)).thenReturn(mockPatients);

        List<PatientTO> result = patientService.findPatientsWithMoreThanXVisits(2);

        assertFalse(result.isEmpty());
        verify(patientDao, times(1)).findPatientsWithMoreThanXVisits(2);
    }

    /**
     * 4. Тест JPQL-запиту: Знайти пацієнтів за статусом страхування
     */
    @Test
    public void testFindPatientsByInsuranceStatus() {
        List<PatientEntity> mockPatients = List.of(testPatient);
        when(patientDao.findPatientsByInsuranceStatus(true)).thenReturn(mockPatients);

        List<PatientTO> result = patientService.findPatientsByInsuranceStatus(true);

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getIsInsured());
        verify(patientDao, times(1)).findPatientsByInsuranceStatus(true);
    }
}
