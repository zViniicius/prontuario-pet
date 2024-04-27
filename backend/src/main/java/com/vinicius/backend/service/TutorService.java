package com.vinicius.backend.service;

import com.vinicius.backend.domain.Tutor;
import com.vinicius.backend.dto.request.TutorRequest;
import org.springframework.data.domain.Page;

public interface TutorService {


    Tutor createTutor(TutorRequest tutorRequest);

    Tutor getTutorById(Long id);

    Tutor getTutorByIdNumber(String idNumber);


    Page<Tutor> getAllTutors(Integer page,
                             Integer size);

    Tutor updateTutor(Long id,
                      TutorRequest tutorRequest);

    void deleteTutor(Long id);
}
