package com.vinicius.backend.repository;

import com.vinicius.backend.domain.Tutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TutorRepositoryTest {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveTutor() {
        Tutor tutor = new Tutor();
        tutor.setName("John Doe");
        tutor.setIdNumber("123456789");
        tutor.setIdType(Tutor.IdentificationType.PASSAPORTE);
        tutor.setEmail("john@example.com");
        tutor.setPhone("123456789");

        Tutor savedTutor = tutorRepository.save(tutor);

        Tutor foundTutor = entityManager.find(Tutor.class, savedTutor.getId());

        assertEquals(savedTutor, foundTutor);
    }
}
