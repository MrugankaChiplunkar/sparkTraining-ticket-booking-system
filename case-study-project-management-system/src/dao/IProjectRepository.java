package dao;
import entity.employee;
import entity.project;
import entity.task;
import java.util.List;

public interface IProjectRepository {

	boolean createEmployee(employee emp);
	boolean createProject(project pj);
	boolean createTask(task tk);
	boolean assignProjectToEmployee(int project_id, int employee_id);
	boolean assignTaskInProjectToEmployee(int task_id, int project_id, int employee_id);
	boolean deleteEmployee(int employee_id);
	boolean deleteProject(int project_id);
	List<task> getAllTasks(int employee_id, int project_id);
}
