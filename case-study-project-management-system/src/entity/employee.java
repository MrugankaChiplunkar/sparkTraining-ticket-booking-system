package entity;

public class employee {
    private int employeeId;
    private String employeeName;
    private String employeeDesignation;
    private String employeeGender;
    private double employeeSalary;
    private int projectId;

    // Default constructor
    public employee() {
    }

    // Parameterized constructor
    public employee(int employeeId, String employeeName, String employeeDesignation, String employeeGender, double employeeSalary, int projectId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDesignation = employeeDesignation;
        this.employeeGender = employeeGender;
        this.employeeSalary = employeeSalary;
        this.projectId = projectId;
    }

    // Getter and setter methods
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
