package org.nakhan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "doctors")
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {

    @Size(max = 20)
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Size(max = 10)
    private String gender;

    @Size(max = 200)
    private String address;

    @Size(max = 100)
    private String specialization;

    @Size(max = 50)
    private String licenseNumber;

    @Size(max = 100)
    private String qualification;

    private Integer experienceYears;

    @Size(max = 200)
    private String clinicAddress;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

    // Constructors
    public Doctor() {
        super();
        this.setRole(Role.DOCTOR);
    }

    public Doctor(String username, String email, String password, String firstName, String lastName) {
        super(username, email, password, firstName, lastName, Role.DOCTOR);
    }

    // Static factory method for sample data
    public static Doctor createSampleDoctor() {
        Doctor doctor = new Doctor("drnawab", "dr.nawab.khan@example.com", "password123", "Dr. Nawab", "Khan");
        doctor.setPhoneNumber("+1122334455");
        doctor.setDateOfBirth(LocalDate.of(1980, 5, 15));
        doctor.setGender("Male");
        doctor.setAddress("456 Medical Center, City, State");
        doctor.setSpecialization("General Medicine");
        doctor.setLicenseNumber("MED123456");
        doctor.setQualification("MBBS, MD");
        doctor.setExperienceYears(15);
        doctor.setClinicAddress("789 Health Clinic, City, State");
        return doctor;
    }

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
