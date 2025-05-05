-- Спочатку видаляємо зв’язки
DROP TABLE IF EXISTS visit_medical_treatment;
DROP TABLE IF EXISTS visit;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS medical_treatment;
DROP TABLE IF EXISTS address;

-- Створюємо таблиці заново
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(255),
    postal_code VARCHAR(50)
);

CREATE TABLE patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    telephone_number VARCHAR(50),
    email VARCHAR(255),
    patient_number VARCHAR(50),
    date_of_birth DATE,
    date_of_registration TIMESTAMP,
    address_id BIGINT,
    is_insured BOOLEAN DEFAULT FALSE,
    version INT DEFAULT 0,
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

CREATE TABLE doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    specialization VARCHAR(255),
    doctor_number VARCHAR(50),
    email VARCHAR(255),
    telephone_number VARCHAR(50),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

CREATE TABLE medical_treatment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    type VARCHAR(255)
);

CREATE TABLE visit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    time TIMESTAMP,
    patient_id BIGINT,
    doctor_id BIGINT,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE
);

CREATE TABLE visit_medical_treatment (
    visit_id BIGINT,
    medical_treatment_id BIGINT,
    PRIMARY KEY (visit_id, medical_treatment_id),
    FOREIGN KEY (visit_id) REFERENCES visit(id) ON DELETE CASCADE,
    FOREIGN KEY (medical_treatment_id) REFERENCES medical_treatment(id)
);
