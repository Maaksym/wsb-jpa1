-- Insert into address table (używamy snake_case, zgodnie z wygenerowanym schematem)
INSERT INTO address (city, address_line1, address_line2, postal_code) VALUES
('Warszawa', 'Main Street', 'Apartment 1', '00-001'),
('Kraków', 'Old Town Road', '', '31-001'),
('Wrocław', 'Freedom Avenue', 'Suite 2', '50-001'),
('Poznań', 'Green Street', 'Building A', '60-101');

-- Insert into doctor table (przyjmując, że Hibernate konwertuje nazwy pól na snake_case)
INSERT INTO doctor (first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES
('Jan', 'Kowalski', '123456789', 'jan.kowalski@example.com', 'DOC123', 'Cardiology', 1),
('Anna', 'Nowak', '987654321', 'anna.nowak@example.com', 'DOC124', 'Neurology', 2);

-- Insert into patient table
INSERT INTO patient (first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id) VALUES
('Michał', 'Wiśniewski', '567890123', 'michal.wisniewski@example.com', 'PAT123', '1985-05-20', 1),
('Agnieszka', 'Zielińska', '678901234', 'agnieszka.zielinska@example.com', 'PAT124', '1990-08-15', 2),
('Katarzyna', 'Nowicka', '789012345', 'katarzyna.nowicka@example.com', 'PAT125', '1988-02-10', 3);

-- Insert into medical_treatment table
INSERT INTO medical_treatment (description, type) VALUES
('Physical Therapy', 'Rehabilitation'),
('Blood Test', 'Diagnostics'),
('MRI Scan', 'Imaging');

-- Insert into visit table
INSERT INTO visit (description, time, patient_id, medical_treatment_id) VALUES
('Routine Check-up', '2025-03-20T10:00:00', 1, 2),
('Follow-up Visit', '2025-03-21T15:00:00', 2, 1),
('MRI Consultation', '2025-03-22T09:30:00', 3, 3);
