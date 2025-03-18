/*Task 1: DATABASE DESIGN
1. Create the database named "TicketBookingSystem" */
create database TicketBookingSystem;

use TicketBookingSystem;

/*2. Create tables with appropriate relationships*/
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

/*4. Create appropriate Primary Key and Foreign Key constraints for referential integrity. */
alter table event add constraint fk_booking_id foreign key(booking_id) references booking(booking_id);

alter table booking add constraint fk_customer_id foreign key(customer_id) references customer(customer_id);

alter table booking add constraint fk_event_id foreign key(event_id) references event(event_id);

alter table customer add constraint booking_id foreign key(booking_id) references booking(booking_id);

/*Task 2: Select, Where, Between, AND, LIKE: 
1. Write a SQL query to insert at least 10 sample records into each table.*/
insert into venue values(001, 'Whispering Grove', 'Pune'), (002, 'Serene Vista', 'Mumbai'), 
(003, 'Emarald Pavillion', 'Delhi'), (004, 'Sunstone Terrace', 'Chennai'), (005, 'Moonlight Garden', 'Bangalore'), 
(006, 'Azure Canopy', 'Chandigarh'), (007, 'Crystal Stream', 'Hyderabad'), (008, 'Starlight Studio', 'Kolkata'),
(009, 'Curated Space', 'Jaipur'), (010, 'Regal Hall', 'Ahmedabad');

insert into event values(101, 'Coldplay: Music of the Spheres Tour', '2025-04-18', '18:00:00', 002, 1000, 250, 1500, 913, 'Concert');
insert into event values(102, 'Bengaluru Open', '2025-06-06', '10:00:00', 005, 2000, 1100, 850, 502, 'Sports'),
(103, 'Ed Sheeran: Mathematics Tour', '2025-12-25', '17:00:00', 001, 5000, 1000, 5000, 503, 'Concert'),
(104, 'YJHD: Premiere', '2025-09-26', '18:00:00', 006, 300, 50, 800, 504, 'Movie'),
(105, 'Khelo India Winter Games', '2025-11-02', '10:00:00', 010, 1000, 200, 2000, 505, 'Sports'),
(106, 'Guns N Roses', '2025-05-26', '16:00:00', 008, 3000, 1000, 2000, 506, 'Concert'),
(107, '3 Idiots', '2025-06-20', '17:00:00', 007, 300, 50, 600, 507, 'Movie'),
(108, 'ISSF Junior World', '2025-03-31', '11:00:00', 004, 1000, 200, 1000, 508, 'Sports'),
(109, 'Lord of the Rings', '2025-08-25', '19:00:00', 003, 500, 50, 1000, 509, 'Movie'),
(110, '21 Savage', '2025-10-13', '17:00:00', 003, 4000, 1000, 2000, 510, 'Concert');

insert into customer values(201, 'Rohan', 'rohan@gmail.com', '8632157942', 913);
insert into customer values(202, 'Mruganka', 'mruganka@gmail.com', '9784563211', 502),
(203, 'Om', 'om@gmail.com', '987654321', 503),
(204, 'Rutuja', 'rutuja@gmail.com', '9531247866',504),
(205, 'Sunny', 'sunny@gmail.com', '8745963215', 505),
(206, 'Vaishnavi', 'vaishnavi@gmail.com', '8796321455', 506),
(207, 'Atharva', 'atharva@gmail.com', '9863214789', 507),
(208, 'Isha', 'isha@gmail.com', '9874563215', 508),
(209, 'Yash', 'yash@gmail.com', '8963214578', 509),
(210, 'Shravani', 'shravani@gmail.com', '9863257412', 510);


insert into booking values(913, 201, 101, 5, 7500, '2025-03-18');
insert into booking values(502, 202, 102, 3, 2550, '2025-01-02'),
(503, 203, 103, 4, 20000, '2025-01-26'),
(504, 204, 104, 2, 1600, '2025-02-05'),
(505, 205, 105, 6, 12000, '2025-05-02'),
(506, 206, 106, 1, 2000, '2025-12-02'),
(507, 207, 107, 5, 3000, '2025-06-08'),
(508, 208, 108, 4, 4000, '2025-09-08'),
(509, 209, 109, 2, 2000, '2025-11-02'),
(510, 210, 110, 3, 6000, '2025-03-06');

/*2. Write a sql query to list all events*/
select * from event;
select event_name from event;

/*3. Write a SQL query to select events with available tickets. */
select event_name, event_id, available_seats from event where available_seats > 0;

/*4. Write a SQL query to select events name partial match with ‘cup’. */
insert into event values(111, 'World Cup', '2025-12-25', '17:00:00', 001, 5000, 1000, 5000, 503, 'Sports');
select * from event where event_name like '%cup%';

/*5. Write a SQL query to select events with ticket price range is between 1000 to 2500.*/
select * from event where ticket_price >= 1000 and ticket_price <= 2500;

/*6. Write a SQL query to retrieve events with dates falling within a specific range.*/
select * from event where event_date >= '2025-04-01' and event_date <= '2025-06-20';

/*7. Write a SQL query to retrieve events with available tickets that also have "Concert" in their name. */
select * from event where available_seats > 0 and event_type like '%Concert%';

/*8. Write a SQL query to retrieve users in batches of 5, starting from the 6th user.*/
select * from customer limit 5 offset 5;

/*9. Write a SQL query to retrieve bookings details contains booked no of ticket more than 4.*/
select * from booking where num_tickets > 4;

/*10. Write a SQL query to retrieve customer information whose phone number end with ‘000’ */
insert into customer values(212, 'Rahul', 'rahul@gmail.com', '8632157000', 511);
select * from customer where phone_number LIKE '%000';

/*11. Write a SQL query to retrieve the events in order whose seat capacity more than 15000. */
select * from event where totat_seats > 1500 order by totat_seats;

/*12. Write a SQL query to select events name not start with ‘x’, ‘y’, ‘z’ */
select * from event where event_name not like 'x%' and event_name not like 'y%' and event_name not like 'z%';






