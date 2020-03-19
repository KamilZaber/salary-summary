public class Employee {
    private int id;
    private String name;
    private String surname;
    private String job;
    private String salary;

    public Employee(int id, String name, String surname, String job, String salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.job = job;
        this.salary = salary;
    }

    public String getJob() {
        return job;
    }

    public String getSalary() {
        return salary;
    }
}
