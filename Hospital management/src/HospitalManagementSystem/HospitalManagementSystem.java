package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url ="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password ="Mypassword@4170";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,sc);
            Doctor doctor = new Doctor(connection);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add patient.");
                System.out.println("2. View patient.");
                System.out.println("3. View Doctors.");
                System.out.println("4. Book Appointment.");
                System.out.println("5. Exit...");
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();
                switch(choice){
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatients();
                        break;
                        // View Patient
                    case 3:
                        doctor.viewDoctors();
                        break;
                        // View Doctor
                    case 4:
                        //Book Appointment
                        bookAppointment(patient,doctor,connection,sc);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice..");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner sc){
        System.out.print("Enter the Patient Id:");
        int patient_id = sc.nextInt();
        System.out.print("Enter the doctor Id: ");
        int doctor_id = sc.nextInt();
        System.out.print("Enter appointment date(YYYY-MM-DD): ");
        String appointment_date = sc.next();
        if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)){
            if(checkDoctorAvailability(doctor_id,appointment_date,connection)){
                String appointmentQuery = "Insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment booked");
                    }else{
                        System.out.print("Failed to Book Appointment.");
                    }


                }catch(SQLException e){
                    e.printStackTrace();
                }

            }else{
                System.out.println("Doctor not available on this date.");
            }
        }else{
            System.out.println("Either doctor or patient available..");
        }
    }
    public static boolean checkDoctorAvailability(int doctor_id,String appointmentDate,Connection connection){
        String Query ="Select Count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
