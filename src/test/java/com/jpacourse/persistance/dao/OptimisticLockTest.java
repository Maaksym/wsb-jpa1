package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class OptimisticLockTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testOptimisticLocking() {
        // Перший контекст
        EntityManager em1 = entityManagerFactory.createEntityManager();
        em1.getTransaction().begin();
        PatientEntity patient1 = em1.find(PatientEntity.class, 1L);

        // Другий контекст
        EntityManager em2 = entityManagerFactory.createEntityManager();
        em2.getTransaction().begin();
        PatientEntity patient2 = em2.find(PatientEntity.class, 1L);

        // Зміни в першому сеансі
        patient1.setEmail("updated.email@example.com");
        em1.getTransaction().commit();
        em1.close(); // обов’язково

        // Зміни в другому сеансі — має викликати OptimisticLockException при коміті
        patient2.setEmail("conflicting.update@example.com");

        assertThrows(RollbackException.class, () -> {
            em2.getTransaction().commit(); // тут має бути виняток
        });

        em2.close();
    }
}

