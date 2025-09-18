import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;
public class project {
    private String projectName;
    private List<Task> tasks;
    private List<Resource> resources;
    private List<Allocation> allocations;

    public project(String projectName) {
        this.projectName = projectName;
        this.tasks = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.allocations = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }
    public List<Resource> getResources() {
        return resources;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void allocateResourceToTask(Resource resource,int percentage,Task task) {
        allocations.add(new Allocation(task, resource, percentage));
    }

    public LocalDateTime ProjectCompletionTime() {
        LocalDateTime completionTime = null;
        for (Task task : tasks) {
            if (completionTime == null || task.getEndTime().isAfter(completionTime)) {
                completionTime = task.getEndTime();
            }
        }
        return completionTime;
    }
    public Duration ProjectDuration() {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        for (Task task : tasks) {
            if (startTime == null || task.getStartTime().isBefore(startTime)) {
                startTime = task.getStartTime();
            }
            if (endTime == null || task.getEndTime().isAfter(endTime)) {
                endTime = task.getEndTime();
            }
        }
        return Duration.between(startTime,endTime);
    }

    public List<Task>getOverLappingTasks(){
        List<Task> overlappingTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task1 = tasks.get(i);
            if (task1.getDependencies().isEmpty()) {
                continue;
            }
            for (int dependency : task1.getDependencies()) {
                Task task2 = getTaskById(dependency);
                if (task2 != null && task1.getStartTime().isBefore(task2.getEndTime())) {
                    if(!overlappingTasks.contains(task1)){
                        overlappingTasks.add(task1);
                    }
                    if(!overlappingTasks.contains(task2)){
                        overlappingTasks.add(task2);
                    }
                }
            }
        }
        return overlappingTasks;
    }

    public List<Resource> getTeamForTask(Task task) {
        List<Resource> team = new ArrayList<>();
        for (Allocation allocation : allocations) {
            if (allocation.getTask().equals(task)) {
                if (!team.contains(allocation.getResource())) {
                    team.add(allocation.getResource());
                }
            }
        }
        return team;
    }
    public Map<Resource, Double> getTotalEffortByResource() {
        Map<Resource, Double> effortMap = new HashMap<>();

        for (Allocation allocation : allocations) {
            Task task = allocation.getTask();
            Resource resource = allocation.getResource();

            long durationInHours = java.time.Duration.between(task.getStartTime(), task.getEndTime()).toHours();

            double effort = durationInHours * (allocation.getPercentage() / 100.0);

            effortMap.put(resource, effortMap.getOrDefault(resource, 0.0) + effort);
        }

        return effortMap;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", tasks=" + tasks +
                ", resources=" + resources +
                ", allocations=" + allocations +
                '}';
    }
}