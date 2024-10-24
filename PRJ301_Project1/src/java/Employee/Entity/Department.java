package Employee.Entity;
import java.util.ArrayList;
public class Department {
    private int id;
    private String name;
    private String type;
    private ArrayList<Employee> emps = new ArrayList<>();
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }   
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Employee> getEmps() {
        return emps;
    }
    public void setEmps(ArrayList<Employee> emps) {
        this.emps = emps;
    }    
}
