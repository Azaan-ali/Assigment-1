public class Allocation {
    private Task task;
    private int percentage;
    private Resource resource;

    public Allocation(Task task, Resource resource, int percentage) {
        this.task = task;
        this.resource = resource;
        this.percentage = percentage;
    }
    public int getPercentage() {
        return percentage;
    }

    public Task getTask() {
        return task;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return "Allocation{" +
                "task=" + task +
                ", resource=" + resource +
                '}';
    }
}
