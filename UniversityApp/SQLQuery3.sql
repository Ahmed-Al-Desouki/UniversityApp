
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'UniversityDB')
BEGIN
    CREATE DATABASE UniversityDB;
END
GO


USE UniversityDB;
GO

-- Create Departments table with additional columns
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Departments')
BEGIN
    CREATE TABLE Departments (
        id INT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(255) NOT NULL,        
        location VARCHAR(255),             
        head_of_department VARCHAR(255),   
        contact_number VARCHAR(20),         
        established_date DATE,              
        description TEXT                   
    );
END
GO

-- Create Professors table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Professors')
BEGIN
    CREATE TABLE Professors (
        id INT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(255) NOT NULL,          
        hire_date DATE NOT NULL,             
        department_id INT,                  
        FOREIGN KEY (department_id) REFERENCES Departments(id)
    );
END
GO

-- Create Students table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Students')
BEGIN
    CREATE TABLE Students (
        id INT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(255) NOT NULL,          
        email VARCHAR(255) NOT NULL,         
        dob DATE NOT NULL,                   
        department_id INT,                  
        gpa DECIMAL(3, 2) DEFAULT 0,         
        FOREIGN KEY (department_id) REFERENCES Departments(id)
    );
END
GO

-- Create Courses table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Courses')
BEGIN
    CREATE TABLE Courses (
        id INT IDENTITY(1,1) PRIMARY KEY,
        title VARCHAR(255) NOT NULL,         
        credits INT NOT NULL,                
        department_id INT,                 
        professor_id INT,                    
        FOREIGN KEY (department_id) REFERENCES Departments(id),
        FOREIGN KEY (professor_id) REFERENCES Professors(id)
    );
END
GO

-- Create Enrollments table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Enrollments')
BEGIN
    CREATE TABLE Enrollments (
        id INT IDENTITY(1,1) PRIMARY KEY,
        student_id INT,                     
        course_id INT,                     
        semester VARCHAR(50),              
        year INT,                         
        FOREIGN KEY (student_id) REFERENCES Students(id),
        FOREIGN KEY (course_id) REFERENCES Courses(id)
    );
END
GO

-- Create Grades table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Grades')
BEGIN
    CREATE TABLE Grades (
        id INT IDENTITY(1,1) PRIMARY KEY,
        enrollment_id INT,                  
        grade VARCHAR(2),                 
        FOREIGN KEY (enrollment_id) REFERENCES Enrollments(id)
    );
END
GO

-- Insert Departments data
INSERT INTO Departments (name, location, head_of_department, contact_number, established_date, description) VALUES
('Computer Science', 'Building A', 'Dr. Mohamed Abdallah', '123-456-7890', '2005-09-01', 'Department of Computer Science focusing on programming, algorithms, and data structures.'),
('Mathematics', 'Building B', 'Dr. Sara Adel', '234-567-8901', '2000-05-15', 'Department of Mathematics focusing on algebra, geometry, and calculus.'),
('Chemistry', 'Building C', 'Dr. Sami Youssef', '345-678-9012', '2003-01-10', 'Department of Chemistry focusing on organic and inorganic chemistry.'),
('English Language', 'Building D', 'Dr. Mona Kamal', '456-789-0123', '2010-11-20', 'Department of English Language focusing on linguistics and literature.'),
('Physics', 'Building E', 'Dr. Hany Fawzy', '567-890-1234', '1998-06-05', 'Department of Physics focusing on quantum physics and astrophysics.');
GO

-- Insert Professors data
INSERT INTO Professors (name, hire_date, department_id) VALUES
('Dr. Mohamed Abdallah', '2010-09-01', 1),
('Dr. Sara Adel', '2012-05-15', 2),
('Dr. Sami Youssef', '2018-01-10', 3),
('Dr. Mona Kamal', '2019-11-20', 4),
('Dr. Hany Fawzy', '2017-06-05', 5);
GO

-- Insert Students data
INSERT INTO Students (name, email, dob, department_id) VALUES
('Mohamed Abdallah', 'mohamed@example.com', '2000-08-15', 1),
('Sara Adel', 'sara@example.com', '2001-02-25', 2),
('Khaled Mahmoud', 'khaled@example.com', '2003-03-18', 3),
('Laila Samir', 'laila@example.com', '2002-11-30', 4),
('Nour Eldin Hassan', 'nour@example.com', '2001-04-12', 5),
('Heba Mohamed', 'heba@example.com', '2000-08-09', 5);
GO

-- Insert Courses data
INSERT INTO Courses (title, credits, department_id, professor_id) VALUES
('Introduction to Programming', 3, 1, 1),
('Linear Algebra', 3, 2, 2),
('Organic Chemistry', 3, 3, 3),
('English Literature', 2, 4, 4),
('Quantum Physics', 3, 5, 5);
GO

-- Insert Enrollments data
INSERT INTO Enrollments (student_id, course_id, semester, year) VALUES
(1, 1, 'Fall', 2024),
(2, 2, 'Spring', 2024),
(3, 3, 'Fall', 2024),
(4, 4, 'Spring', 2024),
(5, 5, 'Fall', 2024),
(6, 5, 'Spring', 2024);
GO

-- Insert Grades data
INSERT INTO Grades (enrollment_id, grade) VALUES
(1, 'B'),
(2, 'A'),
(3, 'C'),
(4, 'B+'),
(5, 'A'),
(6, 'C');
GO
