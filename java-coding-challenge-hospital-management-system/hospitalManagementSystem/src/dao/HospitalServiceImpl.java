package dao;

import java.sql.*;
import java.util.*;
import entity.Appointment;
import exception.PatientNumberNotFoundException;
import util.DBConnUtil;

public class HospitalServiceImpl implements IHospitalService {
    private Connection conn = DBConnUtil.getConnection();

    private boolean patientExists(int patientId) throws SQLException {
        String sql = "SELECT 1 FROM Patient WHERE patientId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            return ps.executeQuery().next();
        }
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT * FROM Appointment WHERE appointmentId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("patientId"),
                    rs.getInt("doctorId"),
                    rs.getString("appointmentDate"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException {
        List<Appointment> list = new ArrayList<>();
        try {
            if (!patientExists(patientId)) {
                throw new PatientNumberNotFoundException("Patient ID not found: " + patientId);
            }

            String sql = "SELECT * FROM Appointment WHERE patientId = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, patientId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getString("appointmentDate"),
                        rs.getString("description")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE doctorId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("patientId"),
                    rs.getInt("doctorId"),
                    rs.getString("appointmentDate"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean scheduleAppointment(Appointment appt) {
        String sql = "INSERT INTO Appointment VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appt.getAppointmentId());
            ps.setInt(2, appt.getPatientId());
            ps.setInt(3, appt.getDoctorId());
            ps.setString(4, appt.getAppointmentDate());
            ps.setString(5, appt.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAppointment(Appointment appt) {
        String sql = "UPDATE Appointment SET patientId=?, doctorId=?, appointmentDate=?, description=? WHERE appointmentId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appt.getPatientId());
            ps.setInt(2, appt.getDoctorId());
            ps.setString(3, appt.getAppointmentDate());
            ps.setString(4, appt.getDescription());
            ps.setInt(5, appt.getAppointmentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        String sql = "DELETE FROM Appointment WHERE appointmentId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
