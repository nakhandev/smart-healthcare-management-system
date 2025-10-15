package org.nakhan.repository;

import org.nakhan.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
    List<MedicalRecord> findByRecordType(String recordType);
    List<MedicalRecord> findByPatientIdAndRecordType(Long patientId, String recordType);
    List<MedicalRecord> findByDiagnosisContainingIgnoreCase(String diagnosis);
}
