import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalarySummary {
    public static <T> List<T> convertArrayToList(T array[])
    {
        List<T> list = new ArrayList<T>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }
    private static void displayMap(HashMap<String,Float> map) {
        for(String s: map.keySet()) {
            System.out.print(s + " = " + map.get(s) + "\n");
        }
    }

    private static List<Employee> readWorkersListCSV(String fileName) {
        BufferedReader reader = null;
        String line;
        String data[];
        List<Employee> employeesData = new ArrayList<Employee>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            while((line = reader.readLine()) != null) {
                data = line.replace("\"","").replace(" ", "").split(";");
                employeesData.add(new Employee(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4]));
            }
        }catch(FileNotFoundException e) {
            System.out.println("Problem with opening the file occured.");
        }catch(IOException e) {
            System.out.println("Problem with input/output occured.");
        }finally {
            if(reader != null) {
                try {
                    reader.close();
                }catch(IOException e){
                    System.out.println("Can't close file.");
                }
            }
        }
        return employeesData;
    }

    private static List<Employee> readWorkersListJSON(String fileName) {
        List<Employee> employeesData = null;
        Gson gson = new Gson();
        String jsonString;

        try {
            jsonString = new String(Files.readAllBytes(Paths.get(fileName)));
            employeesData = convertArrayToList(gson.fromJson(jsonString, Employees.class).getEmployees());
        }catch(IOException e) {
            System.out.println("Problem with input/output occured.");
        }

        return employeesData;
    }

    private static HashMap<String,Float> sumUpSalaries(List<Employee> workersData) {
        HashMap<String,Float> salariesSum = new HashMap<String,Float>();
        String job;
        Float salary;
        for(Employee w: workersData) {
            job = w.getJob();
            salary = Float.parseFloat(w.getSalary().replace(",","."));
            if(salariesSum.containsKey(job)){
                salariesSum.put(job,(salariesSum.get(job) + salary));
            }else {
                salariesSum.put(job,salary);
            }
        }
        return salariesSum;
    }

    public static void main(String args[]) {
        List<Employee> workersData;
        HashMap<String,Float> overall;

        System.out.println("CSV Summary:");
        workersData = readWorkersListCSV("employees (2).csv");
        overall = sumUpSalaries(workersData);
        displayMap(overall);

        System.out.println("JSON Summary:");
        workersData = readWorkersListJSON("employees (2).json");
        overall = sumUpSalaries(workersData);
        displayMap(overall);
    }
}