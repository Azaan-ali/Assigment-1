import java.time.LocalDateTime;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> dependencies; 

    public Task(int id, String title, LocalDateTime startTime, LocalDateTime endTime, List<Integer> dependencies) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dependencies = dependencies;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    public boolean overlapsWith(Task other) {
        return (startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime));
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dependencies=" + dependencies +
                '}';
    }
}
