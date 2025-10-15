package org.nakhan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_reports")
public class LabReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Size(max = 100)
    private String testName;

    @Size(max = 50)
    private String testType;

    @Size(max = 100)
    private String labName;

    @NotNull
    private LocalDateTime testDate;

    private LocalDateTime reportDate;

    @Size(max = 1000)
    private String results;

    @Size(max = 500)
    private String observations;

    @Size(max = 200)
    private String normalRange;

    @Size(max = 50)
    private String status; // NORMAL, ABNORMAL, PENDING

    @Size(max = 500)
    private String doctorComments;

    @Size(max = 200)
    private String filePath; // Path to uploaded report file

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public LabReport() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.testDate = LocalDateTime.now();
        this.reportDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public LabReport(Patient patient, String testName, String testType) {
        this();
        this.patient = patient;
        this.testName = testName;
        this.testType = testType;
    }

    // Static factory method for sample data
    public static LabReport createSampleLabReport(Patient patient) {
        LabReport labReport = new LabReport(patient, "Complete Blood Count", "Blood Test");
        labReport.setLabName("City Diagnostic Center");
        labReport.setResults("Hemoglobin: 14.2 g/dL, WBC: 7200 cells/μL, Platelets: 250,000/μL");
        labReport.setObservations("All values within normal range");
        labReport.setNormalRange("Hemoglobin: 12-16 g/dL, WBC: 4000-11000 cells/μL");
        labReport.setStatus("NORMAL");
        labReport.setDoctorComments("Patient's blood parameters are normal. No immediate concerns.");
        return labReport;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctorComments() {
        return doctorComments;
    }

    public void setDoctorComments(String doctorComments) {
        this.doctorComments = doctorComments;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
