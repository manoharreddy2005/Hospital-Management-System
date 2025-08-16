package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;
    public Patient(Connection connection,Scanner sc){
        this.connection = connection;
        this.sc=sc;
    }
    public void addPatient(){
        System.out.print("Enter the name:");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Enter the Age:");
        int age = sc.nextInt();
        System.out.print("Enter the gender: ");
        sc.nextLine();
        String gender = sc.nextLine();
        try{
            String Query = "Insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Updated Successfullly");
            }else{
                System.out.println("Oh! Sorry Failed to add");
            }
        }catch(SQLException e){
            e.printStackTrace();

        }

    }
    public void viewPatients(){
        String Query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+-----------------+--------------+----------------+");
            System.out.println("| Patient Id | Name            | Age          | Gender         |");
            System.out.println("+------------+-----------------+--------------+----------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-18s|%-14s|%-12s|\n",id,name,age,gender);
            }
            System.out.println("+------------+-----------------+--------------+----------------+");



        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id) {
        String Query = "Select * from patients where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
