import java.util.*;
public class Resource {
    private List<Allocation> taskAllocation;
    private String name;

    public Resource(List<Allocation> taskAllocation, String name) {
        this.taskAllocation = taskAllocation;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Allocation>getTaskAllocation(){
        return taskAllocation;
    }
    @Override
    public String toString() {
        return "Resource{" +
                ", name='" + name + '\'' +
                ", loadAssignmentPercentage=" + taskAllocation +
                '}';
    }
}
