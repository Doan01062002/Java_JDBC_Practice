package business.modal;

import business.EmployeeStatus;
import business.Gender;
import java.time.LocalDate;

/**
 * Lớp đại diện cho thông tin nhân viên
 */
public class Employee {
    private String employeeId;
    private String employeeName;
    private String email;
    private String phone;
    private Gender gender;
    private int salaryGrade;
    private double salary;
    private LocalDate birthDate;
    private String address;
    private EmployeeStatus status;
    private int departmentId;

    public Employee() {}

    public Employee(String employeeId, String employeeName, String email, String phone, Gender gender,
                    int salaryGrade, double salary, LocalDate birthDate, String address,
                    EmployeeStatus status, int departmentId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.salaryGrade = salaryGrade;
        this.salary = salary;
        this.birthDate = birthDate;
        this.address = address;
        this.status = status;
        this.departmentId = departmentId;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public int getSalaryGrade() { return salaryGrade; }
    public void setSalaryGrade(int salaryGrade) { this.salaryGrade = salaryGrade; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public EmployeeStatus getStatus() { return status; }
    public void setStatus(EmployeeStatus status) { this.status = status; }
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    @Override
    public String toString() {
        return "Employee{employeeId='" + employeeId + "', employeeName='" + employeeName +
                "', email='" + email + "', phone='" + phone + "', gender=" + gender +
                ", salaryGrade=" + salaryGrade + ", salary=" + salary + ", birthDate=" + birthDate +
                ", address='" + address + "', status=" + status + ", departmentId=" + departmentId + '}';
    }
}