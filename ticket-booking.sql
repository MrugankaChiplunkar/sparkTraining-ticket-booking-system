create database TicketBookingSystem;

use TicketBookingSystem;

create table venue(venue_id int primary key auto_increment,
venue_name varchar(100) not null, address varchar(200) not null);

create table event(
event_id int primary key auto_increment, 
event_name varchar(100) not null,
event_date date not null,
event_time time not null,
venue_id int, totat_seats int not null, 
available_seats int not null,
ticket_price float, booking_id int,
event_type enum('Movie', 'Sports', 'Concert') not null,
foreign key (venue_id) references venue(venue_id)
);

create table customer(
customer_id int primary key auto_increment, 
customer_name varchar(100) not null, 
email varchar(100) not null unique,
phone_number varchar(100) not null unique,
booking_id int
);

create table booking(
booking_id int primary key auto_increment,
customer_id int not null, event_id int not null, 
num_tickets int not null,
total_cost float not null, booking_date date not null
);

alter table event add constraint fk_booking_id foreign key(booking_id) references booking(booking_id);

alter table booking add constraint fk_customer_id foreign key(customer_id) references customer(customer_id);

alter table booking add constraint fk_event_id foreign key(event_id) references event(event_id);

alter table customer add constraint booking_id foreign key(booking_id) references booking(booking_id);
