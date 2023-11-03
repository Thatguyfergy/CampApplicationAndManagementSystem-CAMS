package users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class UsersArray {
    private static ArrayList<Users> users = new ArrayList<Users>();
    private static String userFile = "csvfiles\\users.csv";
    private static String studentFile = "csvfiles\\students.csv";
    private static String staffFile = "csvfiles\\staff.csv";
    
    public UsersArray(String userFile) {
        UsersArray.userFile = userFile;
        try (BufferedReader csvReader = new BufferedReader(new FileReader(userFile))) {
            String row;
            String header = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: Name, Email, Faculty, Password, Role
                String[] buffer = data[1].split("@");
                if (data[4].equals("Student")){
                    users.add(new Student(buffer[0], data[2], data[3]));
                } else if (data[4].equals("Staff")){
                    users.add(new Staff(buffer[0], data[2], data[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateFiles(users);
    }

    public UsersArray(String studentFile, String staffFile){
        try (BufferedReader csvReader = new BufferedReader(new FileReader(studentFile))) {
            String row;
            String header = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: Name, Email, Faculty, Password, Role
                String[] buffer = data[1].split("@");
                users.add(new Student(buffer[0], data[2], data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader csvReader = new BufferedReader(new FileReader(staffFile))){
            String row;
            String header = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: Name, Email, Faculty, Password, Role
                String[] buffer = data[1].split("@");
                users.add(new Staff(buffer[0], data[2], data[3]));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        updateFiles(users);
    }

    private void updateFiles(ArrayList<Users> users){
        try (FileWriter csvWriter = new FileWriter(userFile)) {
            csvWriter.append("Name,Email,Faculty,Password,Role\n");
            for (Users user : users) {
                csvWriter.append(user.getID());
                csvWriter.append(",");
                csvWriter.append(user.getID() + "@E.NTU.EDU.SG");
                csvWriter.append(",");
                csvWriter.append(user.getFacultyInfo());
                csvWriter.append(",");
                csvWriter.append(user.getPassword());
                csvWriter.append(",");
                if (user instanceof Student){
                    csvWriter.append("Student");
                } else if (user instanceof Staff){
                    csvWriter.append("Staff");
                }
                csvWriter.append("\n");
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter csvWriter = new FileWriter(studentFile)){
            csvWriter.append("Name,Email,Faculty,Password,\n");
            for (Users user : users) {
                if (user instanceof Student){
                    csvWriter.append(user.getID());
                    csvWriter.append(",");
                    csvWriter.append(user.getID() + "@E.NTU.EDU.SG");
                    csvWriter.append(",");
                    csvWriter.append(user.getFacultyInfo());
                    csvWriter.append(",");
                    csvWriter.append(user.getPassword());
                    csvWriter.append("\n");
                }
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter csv = new FileWriter(staffFile)){
            csv.append("Name,Email,Faculty,Password,\n");
            for (Users user : users) {
                if (user instanceof Staff){
                    csv.append(user.getID());
                    csv.append(",");
                    csv.append(user.getID() + "@E.NTU.EDU.SG");
                    csv.append(",");
                    csv.append(user.getFacultyInfo());
                    csv.append(",");
                    csv.append(user.getPassword());
                    csv.append("\n");
                }
            }
            csv.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
