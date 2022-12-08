package deronzier.remi.patientsmicroservice.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import deronzier.remi.patientsmicroservice.exceptions.PatientNotFoundException;
import deronzier.remi.patientsmicroservice.models.Patient;
import deronzier.remi.patientsmicroservice.repositories.PatientRepository;
import deronzier.remi.patientsmicroservice.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository repository;

    @Value("${label.patient}")
    private String patient;

    @Value("${label.successfully-deleted}")
    private String successfullyDeleted;

    @Value("${label.not-found}")
    private String notFound;

    @Override
    public List<Patient> findAll() {
        return repository.findAll();
    }

    @Override
    public Patient find(long id) throws PatientNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(patient + " " + id + " " + notFound));
    }

    @Override
    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Patient update(long id, Patient patient) throws PatientNotFoundException {
        Patient retrivedPatient = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(patient + " " + id + " " + notFound));
        if (patient.getFirstName() != null) {
            retrivedPatient.setFirstName(patient.getFirstName());
        }
        if (patient.getLastName() != null) {
            retrivedPatient.setLastName(patient.getLastName());
        }
        if (patient.getDateOfBirth() != null) {
            retrivedPatient.setDateOfBirth(patient.getDateOfBirth());
        }
        if (patient.getSex() != null) {
            retrivedPatient.setSex(patient.getSex());
        }
        if (patient.getAddress() != null) {
            retrivedPatient.setAddress(patient.getAddress());
        }
        if (patient.getPhoneNumber() != null) {
            retrivedPatient.setPhoneNumber(patient.getPhoneNumber());
        }
        return repository.save(retrivedPatient);
    }

    @Override
    public String delete(long id) throws PatientNotFoundException {
        repository.findById(id).orElseThrow(() -> new PatientNotFoundException(patient + " " + id + " " + notFound));
        repository.deleteById(id);
        return patient + " " + id + " " + successfullyDeleted;
    }
}