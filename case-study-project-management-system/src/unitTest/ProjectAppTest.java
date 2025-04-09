package unitTest;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class ProjectAppTest {

    private static ProjectRepositoryImpl repo;

    @BeforeAll
    static void initRepo() {
        repo = new ProjectRepositoryImpl();
    }

    private int createProjectAndReturnId() {
        Project project = new Project(0, "JUnit Project", "Test Project", LocalDate.now(), "started");
        boolean created = repo.createProject(project);
        assertTrue(created, "Project created");

        List<Project> allProjects = repo.getAllProjects();
        return allProjects.get(allProjects.size() - 1).getProjectId(); 
    }

    private int createEmployeeAndReturnId(int projectId) {
        Employee emp = new Employee(0, "Mruganka", "Developer", "Female", 500000, projectId);
        boolean created = repo.createEmployee(emp);
        assertTrue(created, "Employee created");

        List<Employee> allEmployees = repo.getAllEmployees();
        return allEmployees.get(allEmployees.size() - 1).getEmployeeId(); 
    }

    @Test
    @Order(1)
    public void testCreateEmployeeSuccess() {
        int projectId = createProjectAndReturnId();
        Employee emp = new Employee(0, "Mruganka", "Tester", "Female", 400000, projectId);
        boolean result = repo.createEmployee(emp);
        assertTrue(result, "Employee created successfully");
    }

    @Test
    @Order(2)
    public void testCreateTaskSuccess() {
        int projectId = createProjectAndReturnId();
        int employeeId = createEmployeeAndReturnId(projectId);

        Task task = new Task(0, "Unit Test Task", projectId, employeeId, "Assigned");
        boolean result = repo.createTask(task);
        assertTrue(result, "Task created successfully");
    }

    @Test
    @Order(3)
    public void testSearchTasksForEmployeeInProject() {
        int projectId = createProjectAndReturnId();
        int employeeId = createEmployeeAndReturnId(projectId);

        Task task = new Task(0, "Search Task", projectId, employeeId, "Started");
        repo.createTask(task);

        List<Task> tasks = repo.getAllTasks(employeeId, projectId);
        assertNotNull(tasks, "Task list should not be null");
        assertTrue(tasks.size() > 0, "There should be at least one task assigned");
    }

    @Test
    @Order(4)
    public void testEmployeeNotFoundExceptionThrown() {
        int invalidEmpId = 9999; 
        assertThrows(EmployeeNotFoundException.class, () -> {
            repo.deleteEmployee(invalidEmpId);
        });
    }
}
