-- Заповнення таблиці адрес
INSERT INTO address (address_line1, address_line2, city, postal_code) VALUES
('123 Elm Street', 'Apt 101', 'Springfield', '12345'),
('456 Oak Avenue', 'Suite 202', 'Greenwood', '54321');

-- Заповнення таблиці пацієнтів
INSERT INTO patient (first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id) VALUES
('John', 'Doe', '1234567890', 'john.doe@example.com', 'P123', '1985-02-20', 1),
('Jane', 'Smith', '0987654321', 'jane.smith@example.com', 'P124', '1990-08-15', 2);

-- Заповнення таблиці лікарів
INSERT INTO doctor (first_name, last_name, specialization) VALUES
('Emily', 'Adams', 'Cardiology'),
('Mark', 'Brown', 'Orthopedics');

-- Заповнення таблиці медичних процедур
INSERT INTO medical_treatment (description, type) VALUES
('Heart check-up', 'Cardiology'),
('Knee surgery', 'Orthopedics');

-- Заповнення таблиці візитів
INSERT INTO visit (description, time, patient_id, doctor_id) VALUES
('Routine check-up', '2025-04-10 10:00:00', 1, 1),
('Follow-up consultation', '2025-04-11 14:30:00', 2, 2);

-- Заповнення зв’язків між візитами та медичними процедурами
INSERT INTO visit_medical_treatment (visit_id, medical_treatment_id) VALUES
(1, 1),
(2, 2);
