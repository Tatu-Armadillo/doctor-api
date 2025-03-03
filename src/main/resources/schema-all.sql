drop schema if exists hackaton cascade;

create schema hackaton;

create table hackaton.hospital (
    id bigserial primary key,
    name varchar(255) not null,
    address varchar (255) not null
);

create table hackaton.doctor (
    id bigserial primary key,
    active_crm varchar(8) not null unique,
    user_identifier varchar(255) unique,
    professional_contact varchar(255)
);

create table hackaton.speciality (
    id bigserial primary key,
    name varchar(255) unique not null
);

create table hackaton.speciality_doctor (
    id_doctor bigint not null,
    id_speciality bigint not null,
    primary key (id_doctor, id_speciality)
);

create table hackaton.appointment (
    id bigserial primary key,
    scheduled timestamp not null,
    canceled boolean not null,
    patient varchar(255) not null,
    doctor bigint not null,
    hospital bigint not null
);

alter table if exists hackaton.speciality_doctor add constraint fk_speciality_doctor_doctor foreign key (id_doctor) references hackaton.doctor;
alter table if exists hackaton.speciality_doctor add constraint fk_speciality_doctor_speciality foreign key (id_speciality) references hackaton.speciality;
alter table if exists hackaton.appointment add constraint fk_doctor_appointment foreign key (doctor) references hackaton.doctor;
alter table if exists hackaton.appointment add constraint fk_hospital_appointment foreign key (hospital) references hackaton.hospital;

INSERT INTO hackaton.hospital (name, address) VALUES
    ('Hospital São Paulo', 'Rua das Flores, 123, São Paulo - SP'),
    ('Hospital Santa Catarina', 'Avenida Paulista, 987, São Paulo - SP'),
    ('Hospital das Clinicas', 'Rua Dr. Arnaldo, 455, São Paulo - SP');

INSERT INTO hackaton.doctor (active_crm, user_identifier, professional_contact) VALUES
    ('123456SP', 'dr.joao', 'joao.medico@email.com'),
    ('789101SP', 'dr.maria', 'maria.medica@email.com'),
    ('112131SP', 'dr.pedro', 'pedro.medico@email.com');

INSERT INTO hackaton.speciality (name) VALUES
    ('Cardiologia'),
    ('Ortopedia'),
    ('Pediatria');

INSERT INTO hackaton.speciality_doctor (id_doctor, id_speciality) VALUES
    (1, 1), -- Dr. João é cardiologista
    (2, 2), -- Dr. Maria é ortopedista
    (3, 3), -- Dr. Pedro é pediatra
    (1, 3), -- Dr. João também atende Pediatria
    (2, 1); -- Dr. Maria também atende Cardiologia
