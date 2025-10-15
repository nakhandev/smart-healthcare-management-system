# Smart Healthcare Management System (SHMS)

A centralized web-based system for managing hospital operations including patient registration, doctor scheduling, digital prescriptions, and lab reports.

## Features

- **Patient Management**: Register, update, and search patient information
- **Doctor Management**: Manage doctor profiles and specializations
- **Appointment Scheduling**: Book and manage appointments between patients and doctors
- **Prescription Management**: Create and track digital prescriptions
- **Lab Report Handling**: Manage lab test results and reports
- **Medical Records**: Store and retrieve patient medical history

## Technology Stack

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Data JPA**
- **MySQL Database**
- **JWT Authentication**
- **Maven** (Build Tool)

## Prerequisites

- Java 17 or higher
- MySQL Server
- Maven 3.6+

## Database Configuration

The application is configured to use MySQL with the following settings:

- **Database**: shms_db
- **Username**: nakdev
- **Password**: Linux@1998
- **URL**: jdbc:mysql://localhost:3306/shms_db

Make sure to create the database and update the credentials in `src/main/resources/application.yml` if needed.

## Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd shms
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: http://localhost:1998
   - H2 Console (if enabled): http://localhost:1998/h2-console

## API Endpoints

### Patient Management
- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID
- `POST /api/patients` - Create new patient
- `PUT /api/patients/{id}` - Update patient
- `DELETE /api/patients/{id}` - Delete patient
- `GET /api/patients/search?query={query}` - Search patients

### Doctor Management
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `POST /api/doctors` - Create new doctor
- `PUT /api/doctors/{id}` - Update doctor
- `DELETE /api/doctors/{id}` - Delete doctor
- `GET /api/doctors/specialization/{specialization}` - Get doctors by specialization
- `GET /api/doctors/search?query={query}` - Search doctors

### Appointment Management
- `GET /api/appointments` - Get all appointments
- `GET /api/appointments/{id}` - Get appointment by ID
- `POST /api/appointments` - Create new appointment
- `PUT /api/appointments/{id}` - Update appointment
- `DELETE /api/appointments/{id}` - Delete appointment
- `GET /api/appointments/patient/{patientId}` - Get appointments by patient
- `GET /api/appointments/doctor/{doctorId}` - Get appointments by doctor
- `GET /api/appointments/status/{status}` - Get appointments by status

### Prescription Management
- `GET /api/prescriptions` - Get all prescriptions
- `GET /api/prescriptions/{id}` - Get prescription by ID
- `POST /api/prescriptions` - Create new prescription
- `PUT /api/prescriptions/{id}` - Update prescription
- `DELETE /api/prescriptions/{id}` - Delete prescription
- `GET /api/prescriptions/patient/{patientId}` - Get prescriptions by patient
- `GET /api/prescriptions/doctor/{doctorId}` - Get prescriptions by doctor
- `GET /api/prescriptions/patient/{patientId}/active` - Get active prescriptions by patient

### Lab Report Management
- `GET /api/lab-reports` - Get all lab reports
- `GET /api/lab-reports/{id}` - Get lab report by ID
- `POST /api/lab-reports` - Create new lab report
- `PUT /api/lab-reports/{id}` - Update lab report
- `DELETE /api/lab-reports/{id}` - Delete lab report
- `GET /api/lab-reports/patient/{patientId}` - Get lab reports by patient
- `GET /api/lab-reports/status/{status}` - Get lab reports by status

## Sample Data

The application includes sample data creation methods for testing:

- **Sample Patient**: Nawab Khan (nawab.khan@example.com)
- **Sample Doctor**: Dr. Nawab Khan (dr.nawab.khan@example.com)
- **Sample Appointment**: Scheduled appointment with sample patient and doctor
- **Sample Prescription**: Paracetamol prescription for sample patient
- **Sample Lab Report**: Complete Blood Count for sample patient

## Security

The application uses JWT (JSON Web Tokens) for authentication. Make sure to:

1. Set a strong secret key in `application.yml`
2. Implement proper authentication endpoints
3. Use role-based access control (ADMIN, DOCTOR, PATIENT)

## Testing

Run the tests using:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if necessary
5. Submit a pull request

## License

This project is licensed under the MIT License.
