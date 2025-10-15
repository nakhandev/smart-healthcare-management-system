package org.nakhan.repository;

import org.nakhan.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientId(Long patientId);
    List<Prescription> findByDoctorId(Long doctorId);
    List<Prescription> findByPatientIdAndIsActive(Long patientId, Boolean isActive);
    List<Prescription> findByMedicationNameContainingIgnoreCase(String medicationName);
    List<Prescription> findByIsActive(Boolean isActive);
}
