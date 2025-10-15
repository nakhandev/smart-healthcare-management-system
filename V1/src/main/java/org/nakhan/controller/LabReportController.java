package org.nakhan.controller;

import org.nakhan.entity.LabReport;
import org.nakhan.repository.LabReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lab-reports")
@CrossOrigin(origins = "*")
public class LabReportController {

    @Autowired
    private LabReportRepository labReportRepository;

    @GetMapping
    public List<LabReport> getAllLabReports() {
        return labReportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabReport> getLabReportById(@PathVariable Long id) {
        Optional<LabReport> labReport = labReportRepository.findById(id);
        return labReport.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LabReport createLabReport(@Valid @RequestBody LabReport labReport) {
        return labReportRepository.save(labReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabReport> updateLabReport(@PathVariable Long id, @Valid @RequestBody LabReport labReportDetails) {
        Optional<LabReport> optionalLabReport = labReportRepository.findById(id);
        if (optionalLabReport.isPresent()) {
            LabReport labReport = optionalLabReport.get();
            labReport.setTestName(labReportDetails.getTestName());
            labReport.setTestType(labReportDetails.getTestType());
            labReport.setLabName(labReportDetails.getLabName());
            labReport.setTestDate(labReportDetails.getTestDate());
            labReport.setReportDate(labReportDetails.getReportDate());
            labReport.setResults(labReportDetails.getResults());
            labReport.setObservations(labReportDetails.getObservations());
            labReport.setNormalRange(labReportDetails.getNormalRange());
            labReport.setStatus(labReportDetails.getStatus());
            labReport.setDoctorComments(labReportDetails.getDoctorComments());
            labReport.setFilePath(labReportDetails.getFilePath());
            return ResponseEntity.ok(labReportRepository.save(labReport));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabReport(@PathVariable Long id) {
        Optional<LabReport> labReport = labReportRepository.findById(id);
        if (labReport.isPresent()) {
            labReportRepository.delete(labReport.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/patient/{patientId}")
    public List<LabReport> getLabReportsByPatient(@PathVariable Long patientId) {
        return labReportRepository.findByPatientId(patientId);
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    public List<LabReport> getLabReportsByPatientAndStatus(@PathVariable Long patientId, @PathVariable String status) {
        return labReportRepository.findByPatientIdAndStatus(patientId, status);
    }

    @GetMapping("/test/{testName}")
    public List<LabReport> getLabReportsByTestName(@PathVariable String testName) {
        return labReportRepository.findByTestNameContainingIgnoreCase(testName);
    }

    @GetMapping("/status/{status}")
    public List<LabReport> getLabReportsByStatus(@PathVariable String status) {
        return labReportRepository.findByStatus(status);
    }

    @GetMapping("/lab/{labName}")
    public List<LabReport> getLabReportsByLabName(@PathVariable String labName) {
        return labReportRepository.findByLabName(labName);
    }
}
