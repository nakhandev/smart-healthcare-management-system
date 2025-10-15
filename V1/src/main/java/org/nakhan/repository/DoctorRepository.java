package org.nakhan.repository;

import org.nakhan.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUsername(String username);
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
}
