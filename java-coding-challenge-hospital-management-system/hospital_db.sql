/*creation of database*/
create database hospital_db;
use hospital_db;

/*creation of tables*/
create table Patient(patientId int primary key auto_increment, firstName varchar(50) not null, lastName varchar(50), dateOfBirth date,
gender varchar(10), contactNumber varchar(10), address varchar(100));

create table Doctor(doctorId int primary key auto_increment, firstName varchar(50) not null, lastName varchar(50), specialization varchar(100),
 contactNumber varchar(10) );

create table Appointment(appointmentId int primary key auto_increment, patientId int, doctorId int, appointmentDate date, description text, 
foreign key (patientId) references Patient(patientId) on delete cascade, foreign key (doctorId) references Doctor(doctorId) on delete set null);

/*sample entries*/
insert into Patient values(1,'Mruganka', 'Chiplunkar', '2003-08-09', 'Female', '9876543210', 'Pune'),
(2,'Rahul', 'Singh', '2000-03-04', 'Male','1234567890', 'Mumbai');

insert into Doctor values(1, 'Aniket', 'Saxena', 'Cardiologist', '6541230892'), 
(2, 'Nidhi', 'Kumar', 'Neurologist', '5324793156');

insert into Appointment values(1,1,1, '2025-04-10', 'Regular check up'), (2,2,2, '2025-04-12', 'Migraine consultation');

