package com.vinicius.backend.service.impl;

import com.vinicius.backend.domain.Tutor;
import com.vinicius.backend.dto.request.TutorRequest;
import com.vinicius.backend.repository.TutorRepository;
import com.vinicius.backend.service.TutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Tutor createTutor(TutorRequest tutorRequest) {
        boolean emailExists = tutorRepository.existsByEmail(tutorRequest.getEmail());
        if (emailExists) {
            log.error("Email já cadastrado");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        Tutor tutor = toEntity(tutorRequest);
        return tutorRepository.save(tutor);
    }

    @Override
    public Tutor getTutorById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NO_CONTENT, "Tutor não encontrado para o id informado."));
    }

    @Override
    public Tutor getTutorByIdNumber(String idNumber) {
        Tutor tutor = tutorRepository.findByIdNumber(idNumber);
        if (tutor == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Tutor não encontrado para o número de identificação informado.");
        }
        return tutor;
    }

    @Override
    public Page<Tutor> getAllTutors(Integer page,
                                    Integer size) {
        Pageable pageable = Pageable.ofSize(size)
                .withPage(page);
        return tutorRepository.findAll(pageable);
    }

    @Override
    public Tutor updateTutor(Long id,
                             TutorRequest tutorRequest) {
        boolean exists = tutorRepository.existsById(id);
        if (!exists) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o tutor com o id informado.");
        }

        Tutor tutor = toEntity(tutorRequest);
        return tutorRepository.save(tutor);
    }

    @Override
    public void deleteTutor(Long id) {
        Tutor existingTutor = getTutorById(id);
        if (existingTutor == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o tutor com o id informado.");
        }
        tutorRepository.deleteById(id);
    }

    private Tutor toEntity(TutorRequest tutorRequest) throws HttpClientErrorException{
        return Tutor.builder()
                .name(tutorRequest.getName())
                .idNumber(tutorRequest.getIdNumber())
                .idType(Tutor.IdentificationType.valueOf(tutorRequest.getIdType()))
                .email(tutorRequest.getEmail())
                .phone(tutorRequest.getPhone())
                .build();
    }

}
