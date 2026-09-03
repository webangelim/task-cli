import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskList {

    private static final String FILE_NAME = "tasks.json";
    private Long maxId = 1L;
    private final HashMap<Long, Task> tasks;

    public TaskList() {
        this.tasks = new HashMap<>();
        loadFile();
    }

    public String addTask(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        Task task = new Task(maxId, description.trim(), TaskStatus.TODO);
        tasks.put(task.getId(), task);
        long id = this.maxId;
        this.maxId++;

        saveFile();
        return "Task added successfully! (ID: " + id + ")";
    }

    public void updateTask(Long id, String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        Task task = tasks.get(id);
        if (task == null) {
            System.err.println("Task not found with ID: " + id);
            return;
        }

        task.setDescription(description.trim());
        task.setUpdatedAt(LocalDateTime.now());
        saveFile();
        System.out.println("Task updated successfully! (ID: " + id + ")");
    }

    public void deleteTask(Long id) {
        if (!tasks.containsKey(id)) {
            System.err.println("Task not found with ID: " + id);
            return;
        }

        tasks.remove(id);
        saveFile();
        System.out.println("Task deleted successfully! (ID: " + id + ")");
    }

    public void markAsDone(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.err.println("Task not found with ID: " + id);
            return;
        }

        task.setStatus(TaskStatus.DONE);
        task.setUpdatedAt(LocalDateTime.now());
        saveFile();
        System.out.println("Task marked as done! (ID: " + id + ")");
    }

    public void markInProgress(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.err.println("Task not found with ID: " + id);
            return;
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());
        saveFile();
        System.out.println("Task marked as in progress! (ID: " + id + ")");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task task : tasks.values()) {
            System.out.printf("Task ID: %d, Description: %s, Status: %s\n", task.getId(), task.getDescription(), task.getStatus());
        }
    }

    public void listDoneTasks() {
        boolean found = false;
        for (Task task : tasks.values()) {
            if (task.getStatus().equals(TaskStatus.DONE)) {
                System.out.printf("Task ID: %d, Description: %s, Status: %s\n", task.getId(), task.getDescription(), task.getStatus());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No done tasks found.");
        }
    }

    public void listInProgressTasks() {
        boolean found = false;
        for (Task task : tasks.values()) {
            if (task.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                System.out.printf("Task ID: %d, Description: %s, Status: %s\n", task.getId(), task.getDescription(), task.getStatus());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No in-progress tasks found.");
        }
    }

    public void listTodoTasks() {
        boolean found = false;
        for (Task task : tasks.values()) {
            if (task.getStatus().equals(TaskStatus.TODO)) {
                System.out.printf("Task ID: %d, Description: %s, Status: %s\n", task.getId(), task.getDescription(), task.getStatus());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No todo tasks found.");
        }
    }

    public void loadFile() {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) {
            return;
        }

        try {
            String content = Files.readString(path).trim();
            if (content.isEmpty() || content.equals("[]")) {
                return;
            }

            Pattern objectPattern = Pattern.compile("\\{[^}]*\\}");
            Matcher objectMatcher = objectPattern.matcher(content);

            Pattern idPattern = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
            Pattern descPattern = Pattern.compile("\"description\"\\s*:\\s*\"((?:\\\\\"|[^\"])*)\"");
            Pattern statusPattern = Pattern.compile("\"status\"\\s*:\\s*\"([^\"]*)\"");
            Pattern createdPattern = Pattern.compile("\"createdAt\"\\s*:\\s*\"([^\"]*)\"");
            Pattern updatedPattern = Pattern.compile("\"updatedAt\"\\s*:\\s*\"([^\"]*)\"");

            long currentMaxId = 0L;

            while (objectMatcher.find()) {
                String taskBlock = objectMatcher.group();

                Matcher idMatcher = idPattern.matcher(taskBlock);
                Matcher descMatcher = descPattern.matcher(taskBlock);
                Matcher statusMatcher = statusPattern.matcher(taskBlock);
                Matcher createdMatcher = createdPattern.matcher(taskBlock);
                Matcher updatedMatcher = updatedPattern.matcher(taskBlock);

                if (idMatcher.find() && descMatcher.find() && statusMatcher.find()) {
                    long id = Long.parseLong(idMatcher.group(1));
                    String description = unescapeJson(descMatcher.group(1));
                    TaskStatus status = TaskStatus.valueOf(statusMatcher.group(1));

                    LocalDateTime createdAt = createdMatcher.find() ? LocalDateTime.parse(createdMatcher.group(1)) : LocalDateTime.now();
                    LocalDateTime updatedAt = updatedMatcher.find() ? LocalDateTime.parse(updatedMatcher.group(1)) : LocalDateTime.now();

                    Task task = new Task(id, description, status, createdAt, updatedAt);
                    tasks.put(id, task);

                    if (id > currentMaxId) {
                        currentMaxId = id;
                    }
                }
            }

            this.maxId = currentMaxId + 1;
        } catch (Exception e) {
            System.err.println("Erro ao carregar o arquivo '" + FILE_NAME + "': " + e.getMessage());
        }
    }

    public void saveFile() {
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        int i = 0;
        int totalTasks = tasks.size();

        for (Task task : tasks.values()) {
            json.append("  {\n");
            json.append("    \"id\": ").append(task.getId()).append(",\n");
            json.append("    \"description\": \"").append(escapeJson(task.getDescription())).append("\",\n");
            json.append("    \"status\": \"").append(task.getStatus()).append("\",\n");
            json.append("    \"createdAt\": \"").append(task.getCreatedAt().toString()).append("\",\n");
            json.append("    \"updatedAt\": \"").append(task.getUpdatedAt().toString()).append("\"\n");
            json.append("  }");

            if (i < totalTasks - 1) {
                json.append(",");
            }
            json.append("\n");
            i++;
        }

        json.append("]");

        try {
            Path caminho = Paths.get(FILE_NAME);
            Files.writeString(caminho, json.toString());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    private String unescapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\\"", "\"")
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\t", "\t")
                    .replace("\\\\", "\\");
    }
}
