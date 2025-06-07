package main.java.dao;

import main.java.entities.Doctor;
import main.java.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Add a new doctor
    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (name, specialty, contact) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSpecialty());
            statement.setString(3, doctor.getContact());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update doctor details
    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctors SET name = ?, specialty = ?, contact = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSpecialty());
            statement.setString(3, doctor.getContact());
            statement.setInt(4, doctor.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get doctor by ID
    public Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Doctor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("specialty"),
                        resultSet.getString("contact")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        String sql = "SELECT * FROM doctors";
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                doctors.add(new Doctor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("specialty"),
                        resultSet.getString("contact")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    // Delete doctor by ID
    public boolean deleteDoctor(int id) {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
