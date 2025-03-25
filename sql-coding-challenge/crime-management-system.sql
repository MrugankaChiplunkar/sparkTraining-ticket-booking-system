/*Coding Challenge SQL 
Crime Management Shema DDL and DML 
 -- Create tables*/

create database crime_management_system;
use crime_management_system;

create table crime (crimeID int primary key, incidentType varchar(255), incidentDate date, location varchar(255), description text, 
status varchar(20)); 


create table victim (victimID int primary key, crimeID int, name varchar(255), age int, contactInfo varchar(255), 
injuries varchar(255), foreign key (crimeID) references crime(crimeID)); 

create table suspect (suspectID int primary key, crimeID int, name varchar(255), age int, description text, criminalHistory text, 
foreign key (crimeID) references crime(crimeID));

/*Insert sample data */

insert into crime (CrimeID, IncidentType, IncidentDate, Location, Description, Status) values
(1, 'Robbery', '2023-09-15', '123 Main St, Cityville', 'Armed robbery at a convenience store', 'Open'), 
(2, 'Homicide', '2023-09-20', '456 Elm St, Townsville', 'Investigation into a murder case', 'Under Investigation'), 
(3, 'Theft', '2023-09-10', '789 Oak St, Villagetown', 'Shoplifting incident at a mall', 'Closed');

insert into victim (victimID, crimeID, name, age, contactInfo, injuries) values
(1, 1, 'John Doe', 34, 'johndoe@example.com', 'Minor injuries'), 
(2, 2, 'Jane Smith', 29, 'janesmith@example.com', 'Deceased'), 
(3, 3, 'Alice Johnson', 40, 'alicejohnson@example.com', 'None');

insert into suspect (suspectID, crimeID, name, age, description, criminalHistory) values 
(1, 1, 'Robber 1', 38, 'Armed and masked robber', 'Previous robbery convictions'), 
(2, 2, 'Unknown', NULL, 'Investigation ongoing', NULL), 
(3, 3, 'Suspect 1', 32, 'Shoplifting suspect', 'Prior shoplifting arrests');

/*Solve the below queries: 
1. Select all open incidents.*/
select * from crime where status = 'Open';

/*2. Find the total number of incidents.*/
select count(*) as totalIncidents from crime;

/*3. List all unique incident types.*/
select distinct incidentType from crime;

/*4. Retrieve incidents that occurred between '2023-09-01' and '2023-09-10'*/
select * from crime where incidentDate between '2023-09-01' and '2023-09-10';

/*5. List persons involved in incidents in descending order of age.*/
select name, age from (select name, age from victim union select name, age from suspect) as people order by age desc;

/*6. Find the average age of persons involved in incidents. */
select avg(age) as averageAge from (select age from victim union all select age from suspect) as people;

/*7. List incident types and their counts, only for open cases. */
select incidentType, count(*) as incidentCount from crime where status='Open' group by incidentType;

/*8. Find persons with names containing 'Doe'. */
select name from victim where name like '%Doe%' union select name from suspect where name like '%Doe%';

/*9. Retrieve the names of persons involved in open cases and closed cases.*/
select v.name, c.status from victim v join crime c on v.crimeID = c.crimeID where c.status in ('Open', 'Closed') union
select s.name, c.status from suspect s join crime c on s.crimeID = c.crimeID where c.status in ('Open', 'Closed');

/*10. List incident types where there are persons aged 30 or 35 involved.*/
select distinct c.incidentType from crime c join victim v on c.crimeID = v.crimeID where v.age in (30, 35) union
select distinct c.incidentType from crime c join suspect s on c.crimeID = s.crimeID where s.age in (30, 35);

/*11. Find persons involved in incidents of the same type as 'Robbery'. */
select v.name from victim v join crime c on v.crimeID = c.crimeID where c.incidentType = 'Robbery' union
select s.name from suspect s join crime c on s.crimeID = c.crimeID where c.incidentType = 'Robbery';

/*12. List incident types with more than one open case. */
select incidentType from crime where status='Open' group by incidentType having count(*)>1;

/*13. List all incidents with suspects whose names also appear as victims in other incidents.*/
select distinct c.* from crime c join suspect s on c.crimeID = s.crimeID join victim v on s.name = v.name;

/*14.  Retrieve all incidents along with victim and suspect details. */
select c.*, v.name as victimName, v.contactInfo, v.injuries, s.name as suspectName, s.description from crime c left join
victim v on c.crimeID = v.crimeID left join suspect s on c.crimeID = s.crimeID;

/*15. Find incidents where the suspect is older than any victim.*/
select distinct c.* from crime c join suspect s on c.crimeID = s.crimeID join victim v on c.crimeID = v.crimeID where s.age > v.age;

/*16. Find suspects involved in multiple incidents.*/
select name, count(distinct crimeID) as incidentCount from suspect group by name having count(distinct crimeID)>1;

/*17. List incidents with no suspects involved. */
select * from crime where crimeID in(select crimeID from suspect where name='Unknown');

/*18. List all cases where at least one incident is of type 'Homicide' and all other incidents are of type 
'Robbery'.*/
select * from crime where incidentType='Homicide' and not exists(select 1 from crime where incidentType not in ('Robbery', 'Homicide'));

/*19. Retrieve a list of all incidents and the associated suspects, showing suspects for each incident, or 
'No Suspect' if there are none. */
select c.crimeID, c.incidentType, c.status, (select s.name from suspect s where s.crimeID = c.crimeID limit 1) as suspectName from crime c;

/*20. List all suspects who have been involved in incidents with incident types 'Robbery' or 'Assault' */
select distinct * from suspect s join crime c on s.crimeID = c.crimeID where c.incidentType in ('Robbery', 'Assault');



