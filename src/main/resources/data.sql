-- Заповнення таблиці адрес
INSERT INTO address (id, address_line1, address_line2, city, postal_code) VALUES
(1, '123 Elm Street', 'Apt 101', 'Springfield', '12345'),
(2, '456 Oak Avenue', 'Suite 202', 'Greenwood', '54321'),
(3, '789 Maple Lane', NULL, 'Riverside', '67890');

-- Заповнення таблиці пацієнтів
INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, date_of_registration, address_id, is_insured) VALUES
(1, 'John', 'Doe', '1234567890', 'john.doe@example.com', 'P123', '1985-02-20', '2020-01-15', 1, true),
(2, 'Jane', 'Smith', '0987654321', 'jane.smith@example.com', 'P124', '1990-08-15', '2022-05-01', 2, false),
(3, 'Michael', 'Brown', '111222333', 'michael.brown@example.com', 'P125', '1980-05-30', '2019-09-20', 3, true);

-- Заповнення таблиці лікарів (додані `doctor_number`, `email`, `telephone_number`, `address_id`)
INSERT INTO doctor (id, first_name, last_name, specialization, doctor_number, email, telephone_number, address_id) VALUES
(1, 'Emily', 'Adams', 'Cardiology', 'D001', 'emily.adams@example.com', '123456789', 1),
(2, 'Mark', 'Brown', 'Orthopedics', 'D002', 'mark.brown@example.com', '987654321', 2),
(3, 'Sophia', 'Wilson', 'Dentistry', 'D003', 'sophia.wilson@example.com', '456789123', 3);

-- Заповнення таблиці медичних процедур
INSERT INTO medical_treatment (id, description, type) VALUES
(1, 'Heart check-up', 'Cardiology'),
(2, 'Knee surgery', 'Orthopedics'),
(3, 'Dental cleaning', 'Dentistry');

-- Заповнення таблиці візитів
INSERT INTO visit (id, description, time, patient_id, doctor_id) VALUES
(1, 'Routine check-up', '2025-04-10 10:00:00', 1, 1),
(2, 'Follow-up consultation', '2025-04-11 14:30:00', 2, 2),
(3, 'Physical therapy session', '2025-04-12 14:00:00', 1, 2),
(4, 'Annual dental check-up', '2025-04-13 16:00:00', 3, 3);

-- Заповнення зв’язків між візитами та медичними процедурами
INSERT INTO visit_medical_treatment (visit_id, medical_treatment_id) VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 3);
