package org.nakhan.repository;

import org.nakhan.entity.LabReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabReportRepository extends JpaRepository<LabReport, Long> {
    List<LabReport> findByPatientId(Long patientId);
    List<LabReport> findByTestNameContainingIgnoreCase(String testName);
    List<LabReport> findByStatus(String status);
    List<LabReport> findByPatientIdAndStatus(Long patientId, String status);
    List<LabReport> findByLabName(String labName);
}
