package org.nakhan.controller;

import org.nakhan.entity.Prescription;
import org.nakhan.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        return prescription.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prescription createPrescription(@Valid @RequestBody Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @Valid @RequestBody Prescription prescriptionDetails) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(id);
        if (optionalPrescription.isPresent()) {
            Prescription prescription = optionalPrescription.get();
            prescription.setMedicationName(prescriptionDetails.getMedicationName());
            prescription.setDosage(prescriptionDetails.getDosage());
            prescription.setFrequency(prescriptionDetails.getFrequency());
            prescription.setDuration(prescriptionDetails.getDuration());
            prescription.setInstructions(prescriptionDetails.getInstructions());
            prescription.setDiagnosis(prescriptionDetails.getDiagnosis());
            prescription.setIsActive(prescriptionDetails.getIsActive());
            return ResponseEntity.ok(prescriptionRepository.save(prescription));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if (prescription.isPresent()) {
            prescriptionRepository.delete(prescription.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Prescription> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    @GetMapping("/patient/{patientId}/active")
    public List<Prescription> getActivePrescriptionsByPatient(@PathVariable Long patientId) {
        return prescriptionRepository.findByPatientIdAndIsActive(patientId, true);
    }

    @GetMapping("/medication/{medicationName}")
    public List<Prescription> getPrescriptionsByMedication(@PathVariable String medicationName) {
        return prescriptionRepository.findByMedicationNameContainingIgnoreCase(medicationName);
    }
}
