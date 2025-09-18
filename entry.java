import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class entry {
    public static void main(String[] arg) {
        project p1 = new project("Project Planning");

        // Formatter for date+time like 20250915+0800
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd+HHmm");

        // Reading the Task file and storing in the list of tasks
        try {
            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0].trim());
                String title = data[1].trim();

                // Parse start and end times as LocalDateTime
                LocalDateTime startTime = LocalDateTime.parse(data[2].trim(), formatter);
                LocalDateTime endTime   = LocalDateTime.parse(data[3].trim(), formatter);

                // Parse dependencies (if any)
                List<Integer> dependencies = new ArrayList<>();
                for (int i = 4; i < data.length; i++) {
                    String dep = data[i].trim();
                    if (!dep.isEmpty()) {
                        dependencies.add(Integer.parseInt(dep));
                    }
                }

                Task t1 = new Task(id, title, startTime, endTime, dependencies);
                p1.addTask(t1);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Task file not found");
        }

        // Reading the Resource file and storing in the list of resources
        try {
            File file = new File("data1.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");
                String name = data[0].trim();

                Resource r1 = new Resource(new ArrayList<>(), name);

                for (int i = 1; i < data.length; i++) {
                    String[] parts = data[i].split(":");
                    int taskId = Integer.parseInt(parts[0].trim());
                    int percentage = Integer.parseInt(parts[1].trim());

                    Task task = p1.getTaskById(taskId);
                    if (task != null) {
                        Allocation allocation = new Allocation(task, r1, percentage);
                        r1.getTaskAllocation().add(allocation);
                        p1.allocateResourceToTask(r1, percentage, task);
                    }
                }
                p1.addResource(r1);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Resource file not found");
        }

        // --- Output Section ---
        System.out.println("Project Name: " + p1.getProjectName());
        System.out.println("Project Completion Time: " + p1.ProjectCompletionTime());
        System.out.println("Project Duration: " + p1.ProjectDuration());

        System.out.println("\nOverlapping Tasks:");
        List<Task> overlappingTasks = p1.getOverLappingTasks();
        for (Task task : overlappingTasks) {
            System.out.println(" - " + task.getTitle());
        }

        System.out.println("\nTeam for Task 1:");
        List<Resource> teamForTask = p1.getTeamForTask(p1.getTaskById(1));
        for (Resource resource : teamForTask) {
            System.out.println(" - " + resource.getName());
        }

        System.out.println("\nTotal Effort Required by Each Resource:");
        Map<Resource, Double> totalEffort = p1.getTotalEffortByResource();
        for (Map.Entry<Resource, Double> entry : totalEffort.entrySet()) {
            System.out.println(" - " + entry.getKey().getName() + ": " + entry.getValue() + " hours");
        }
    }
}
