package com.vinicius.backend.repository;

import com.vinicius.backend.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Tutor findByIdNumber(String idNumber);

    boolean existsByEmail(String email);

}
