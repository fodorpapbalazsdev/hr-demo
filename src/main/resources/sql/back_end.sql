-- adresses table

create table hr.addresses
(
	id serial not null,
	post_code int,
	city varchar(200),
	adress_line varchar(255)
);

create unique index addresses_id_uindex
	on hr.addresses (id);

alter table hr.addresses
	add constraint addresses_pk
		primary key (id);

-- patients table


CREATE TYPE genders AS ENUM ('male', 'female', 'unknown');


create table hr.patients
(
	id serial not null,
	first_name varchar(60),
	last_name varchar(100),
	mothers_name varchar(160),
	gender genders,
	date_of_birth date,
	date_of_death date,
	place_of_birth varchar(100),
	phone_number varchar(60),
	address_id int,
	email varchar(100)
);

create unique index patients_id_uindex
	on hr.patients (id);

create unique index patients_email_uindex
	on hr.patients (email);

alter table hr.patients
	add constraint patients_pk
		primary key (id);

alter table hr.patients
	add constraint patients_addresses_id_fk
		foreign key (address_id) references hr.addresses
			on update cascade on delete restrict;


-- patient_relationships table


CREATE TYPE relation_types AS ENUM ('parent', 'neighbor', 'sibling');
ALTER TYPE relation_types ADD VALUE 'colleague';
SELECT enum_range(NULL::relation_types)

CREATE TYPE quality_types AS ENUM ('positive', 'negative', 'unknown');

CREATE TYPE proximity_types AS ENUM ('1', '2', '3', '4', '5', '6', '7', '8', '9', '10');


create table hr.patient_relationships
(
	id serial not null,
	patient_is int,
	tpye relation_types,
	patient_of int,
	bidirectional boolean,
	quality quality_types,
	proximity proximity_types,
	start_date date,
	end_date date
);

create unique index patient_relationships_id_uindex
	on hr.patient_relationships (id);

alter table hr.patient_relationships
	add constraint patient_relationships_pk
		primary key (id);

alter table hr.patient_relationships
	add constraint patient_relationships_patients_id_id_fk
		foreign key (patient_is, patient_of) references hr.patients (id, id);

alter table hr.patient_relationships
	add constraint patient_relationships_patients_id_fk_2
		foreign key (patient_of) references hr.patients;



-- triggers


CREATE OR REPLACE FUNCTION trigger_start_date()
    RETURNS TRIGGER AS
$$
DECLARE
    patient_date_of_birth date;
BEGIN
    select date_of_birth into patient_date_of_birth from hr.patients where id = new.patient_is;

    IF NEW.start_date < patient_date_of_birth THEN
        NEW.start_date = patient_date_of_birth;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_start_date_properly_in_patient_relationships on patient_relationships;
CREATE TRIGGER set_start_date_properly_in_patient_relationships
    BEFORE INSERT
    ON patient_relationships
    FOR EACH ROW
EXECUTE PROCEDURE trigger_start_date();



CREATE OR REPLACE FUNCTION trigger_end_date()
    RETURNS TRIGGER AS
$$
DECLARE
BEGIN
    update hr.patient_relationships
    set end_date = new.date_of_death
    where (patient_is = new.id or patient_of = new.id) and end_date is null;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_end_date_properly_in_patient_relationships on patients;
CREATE TRIGGER set_end_date_properly_in_patient_relationships
    AFTER UPDATE
    ON patients
    FOR EACH ROW
EXECUTE PROCEDURE trigger_end_date();


