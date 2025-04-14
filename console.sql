-- Tạo cơ sở dữ liệu
CREATE DATABASE employee_management;
USE employee_management;

-- Tạo bảng departments
CREATE TABLE departments (
    dept_no CHAR(4) PRIMARY KEY,
    dept_name VARCHAR(40) NOT NULL
);

-- Tạo bảng employees
CREATE TABLE employees (
    emp_no INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    hire_date DATE,
    salary DECIMAL(10, 2),
    dept_no CHAR(4),
    job_title VARCHAR(50),
    FOREIGN KEY (dept_no) REFERENCES departments(dept_no)
);

-- Tạo bảng salaries (lịch sử lương)
CREATE TABLE salaries (
    emp_no INT,
    salary DECIMAL(10, 2),
    from_date DATE,
    to_date DATE,
    PRIMARY KEY (emp_no, from_date),
    FOREIGN KEY (emp_no) REFERENCES employees(emp_no)
);

-- Chèn dữ liệu mẫu vào bảng departments
INSERT INTO departments (dept_no, dept_name) VALUES
    ('D001', 'Sales'),
    ('D002', 'HR'),
    ('D003', 'IT');

-- Chèn dữ liệu mẫu vào bảng employees
INSERT INTO employees (emp_no, first_name, last_name, email, hire_date, salary, dept_no, job_title) VALUES
    (1, 'Nguyen', 'Van A', 'nguyenvana@gmail.com', '2023-01-15', 50000.00, 'D001', 'Sales Executive'),
    (2, 'Tran', 'Thi B', 'tranthib@gmail.com', '2022-06-30', 60000.00, 'D002', 'HR Manager'),
    (3, 'Le', 'Van C', 'levanc@gmail.com', '2021-08-10', 75000.00, 'D003', 'IT Specialist');

-- Chèn dữ liệu mẫu vào bảng salaries
INSERT INTO salaries (emp_no, salary, from_date, to_date) VALUES
    (1, 50000.00, '2023-01-15', '2024-01-15'),
    (2, 60000.00, '2022-06-30', NULL),
    (3, 75000.00, '2021-08-10', NULL);