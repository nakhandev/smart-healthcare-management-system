package org.nakhan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Size(max = 100)
    private String recordType; // VISIT, SURGERY, EMERGENCY, etc.

    @NotNull
    private LocalDateTime recordDate;

    @Size(max = 200)
    private String chiefComplaint;

    @Size(max = 500)
    private String historyOfPresentIllness;

    @Size(max = 500)
    private String physicalExamination;

    @Size(max = 200)
    private String diagnosis;

    @Size(max = 500)
    private String treatmentPlan;

    @Size(max = 500)
    private String medications;

    @Size(max = 500)
    private String followUpInstructions;

    @Size(max = 500)
    private String additionalNotes;

    @Size(max = 200)
    private String filePath; // Path to uploaded medical record file

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public MedicalRecord() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.recordDate = LocalDateTime.now();
    }

    public MedicalRecord(Patient patient, String recordType, String chiefComplaint) {
        this();
        this.patient = patient;
        this.recordType = recordType;
        this.chiefComplaint = chiefComplaint;
    }

    // Static factory method for sample data
    public static MedicalRecord createSampleMedicalRecord(Patient patient) {
        MedicalRecord record = new MedicalRecord(patient, "VISIT", "Routine checkup");
        record.setHistoryOfPresentIllness("Patient reports feeling well with no specific complaints.");
        record.setPhysicalExamination("Vital signs normal. Heart and lung sounds clear.");
        record.setDiagnosis("No acute issues identified");
        record.setTreatmentPlan("Continue current lifestyle. Follow up in 6 months.");
        record.setMedications("None currently prescribed");
        record.setFollowUpInstructions("Schedule next appointment for routine screening.");
        record.setAdditionalNotes("Patient advised on healthy diet and regular exercise.");
        return record;
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

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getHistoryOfPresentIllness() {
        return historyOfPresentIllness;
    }

    public void setHistoryOfPresentIllness(String historyOfPresentIllness) {
        this.historyOfPresentIllness = historyOfPresentIllness;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getFollowUpInstructions() {
        return followUpInstructions;
    }

    public void setFollowUpInstructions(String followUpInstructions) {
        this.followUpInstructions = followUpInstructions;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
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
