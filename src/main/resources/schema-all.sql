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
alter table if exists hackaton.appointment add constraint fk_doctor_appointment foreign key (doctor) references hackaton.appointment;
alter table if exists hackaton.appointment add constraint fk_hospital_appointment foreign key (hospital) references hackaton.appointment;
