create table if not exists members (
    id varchar(10) not null primary key,
    first_name varchar(50) not null,
    middle_name varchar(50),
    last_name varchar(50) not null,
    email varchar(256) not null unique,
    mobile varchar(15) not null,
    dob date not null
);

create table if not exists teams (
    id varchar(10) not null primary key,
    team_name varchar(100) not null,
    description varchar(1000),
    created_date date not null,
    manager_id varchar(10) not null references members(id)
);

create table if not exists teams_members (
    id varchar(10) not null primary key,
    team_id varchar(10) not null references teams(id),
    member_id varchar(10) not null references members(id)
);

create table if not exists funds (
    id varchar(10) not null primary key,
    comment varchar(1000) not null,
    total bigserial not null,
    fund_date date not null,
    team_member_id varchar(10) not null references teams_members(id)
);

create table if not exists expenses (
    id varchar(10) not null primary key,
    description varchar(1000) not null,
    total bigserial not null,
    expense_date date not null,
    team_id varchar(10) not null references teams(id)
);