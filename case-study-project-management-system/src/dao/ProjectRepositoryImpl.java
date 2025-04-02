package dao;
import entity.employee;
import entity.task;
import entity.project;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectRepositoryImpl implements IProjectRepository {
	
	private Connection conn = DBConnection.getConnection();

	@Override
	public boolean createEmployee(employee emp) {
		String sql = "insert into employee(employee_name, employee_designation, employee_gender, employee_salary, project_id) values (?,?,?,?,?)";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, emp.getEmployeeName());
			stmt.setString(2, emp.getEmployeeDesignation());
			stmt.setString(3, emp.getEmployeeGender());
			stmt.setString(4, emp.getEmployeeSalary());
			stmt.setString(5, emp.getProjectId());
			
			return stmt.executeUpdate() > 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean createProject(project pj) {
		
		String sql = "insert into project(project_name, project_description, start_date, status) values (?, ?, ?, ?)";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, pj.getProjectName());
			stmt.setString(2, pj.getDescription());
			stmt.setString(3, pj.getStartDate());
			stmt.setString(4, pj.getStatus());
			
			return stmt.executeUpdate() > 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createTask(task tk) {
		String sql = "insert into task(task_name, project_id, employee_id, status) values (?,?,?,?)";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setString(1, tk.getTaskname());
			stmt.setString(2, tk.getProjectId());
			stmt.setString(3, tk.getEmployeeId());
			stmt.setString(4, tk.getStatus());
			
			return stmt.executeUpdate() > 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean assignProjectToEmployee(int project_id, int employee_id) {
		String sql = "update employee set project_id = ? where employee_id = ";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1, project_id);
			stmt.setInt(2, employee_id);
			
			return stmt.executeUpdate() > 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean assignTaskInProjectToEmployee(int task_id, int project_id, int employee_id) {
		String sql = "update task set project_id = ?, employee_id = ? where task_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1, project_id);
			stmt.setInt(2, employee_id);
			stmt.setInt(3, task_id);
			
			return stmt.executeUpdate() > 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteEmployee(int employee_id) throws EmployeeNotFoundException{
		String checksql = "select employee_name from employee where employee_id = ?";
		String deletesql = "delete from employee where employee_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(checksql))
		{
			stmt.setInt(1, employee_id);
			ResultSet rs = checkStmt.executeQuery();
			if(!rs.next()) {
				throw new EmployeeNotFoundException("Employee with id" + employee_id + "not found");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		try(Connection con = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(deletesql))
{
	stmt.setInt(1, employee_id);
	return deleteStmt.executeUpdate() > 0;
}
catch(SQLException e) {
	e.printStackTrace();
}
		
		return false;
	}

	@Override
	public boolean deleteProject(int project_id) throws ProjectNotFoundException{
		String checksql = "select project_name from project where project_id =? ";
		String deletesql = "delete from project where project_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(checksql))
		{
			stmt.setInt(1, project_id);
			ResultSet rs = checkStmt.executeQuery();
			if(!rs.next()) {
				throw new ProjectNotFoundException("Project with id" + project_id + "not found");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		try(Connection con = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(deletesql))
{
	stmt.setInt(1, project_id);
	return deleteStmt.executeUpdate() > 0;
}
catch(SQLException e) {
	e.printStackTrace();
}
		
		return false;
	}

	@Override
	public List<task> getAllTasks(int employee_id, int project_id) {
		List<task> tasks = new ArrayList<>();
		String sql = "select * from task where employee_id = ? and project_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql))
		{
			stmt.setInt(1, employee_id);
			stmt.setInt(2, project_id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				tasks.add(new task(
					rs.getInt("task_id"), 
					rs.getString("task_name"),
					rs.getInt("project_id"),
					rs.getInt("employee_id"),
					rs.getInt("status")
						));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}

}
