create schema hr;

create table hr.employees
(
    id         serial       not null,
    email      varchar(255) not null,
    password   varchar(66)  not null,
    full_name  varchar(200),
    dep_id     int,
    created_at timestamp,
    created_by int,
    updated_at timestamp,
    updated_by int
);

create unique index employees_email_uindex
    on hr.employees (email);

create unique index employees_id_uindex
    on hr.employees (id);

alter table hr.employees
    add constraint employees_pk
        primary key (id);

create table hr.departments
(
    id         serial not null,
    name       varchar(100),
    manager_id int,
    created_at timestamp,
    created_by int,
    updated_at timestamp,
    updated_by int
);

create unique index departments_id_uindex
    on hr.departments (id);

alter table hr.departments
    add constraint departments_pk
        primary key (id);

alter table hr.employees
    add constraint employees_departments_id_fk
        foreign key (dep_id) references hr.departments
            on update cascade on delete restrict;

CREATE OR REPLACE FUNCTION trigger_set_created_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.created_at = NOW();
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_set_updated_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_created_ad_employees on employees;
CREATE TRIGGER set_created_ad_employees
    BEFORE INSERT
    ON employees
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_created_timestamp();

DROP TRIGGER IF EXISTS set_created_ad_departments on departments;
CREATE TRIGGER set_created_ad_departments
    BEFORE INSERT
    ON departments
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_created_timestamp();

DROP TRIGGER IF EXISTS set_updated_ad_demployees on employees;
CREATE TRIGGER set_updated_ad_demployees
    BEFORE UPDATE
    ON employees
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_updated_timestamp();

DROP TRIGGER IF EXISTS set_updated_ad_departments on departments;
CREATE TRIGGER set_updated_ad_departments
    BEFORE UPDATE
    ON departments
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_updated_timestamp();

alter table hr.employees
    add profile_picture text;

-- insert a dumy admin user
insert into employees (email, password, full_name, created_by, updated_by)
values ('admin@admin.com', 'hashedsecret', 'admin admin', 0, 0);

insert into departments (name, manager_id, created_by, updated_by)
values ('queue', 1, 0, 0);
