DROP TABLE course;
CREATE TABLE IF NOT EXISTS course(number int AUTO_INCREMENT,
				INDEX num_ind (number),
				name varchar(20) NOT NULL, semester char NOT NULL, year int NOT NULL,
				hours int NOT NULL, PRIMARY KEY(number))
				ENGINE=INNODB;

CREATE TRIGGER count_total_hours BEFORE INSERT ON course
	FOR EACH ROW SET @numHours = @numHours + NEW.hours;
       
SET @numHours = 0;
INSERT INTO course (name, semester, year, hours) values('yoav','a',2015,3);
SELECT @numHours AS 'Total amount inserted'; 