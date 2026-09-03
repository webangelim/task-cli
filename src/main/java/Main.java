public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        TaskList taskList = new TaskList();
        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: add <description>");
                        return;
                    }
                    String result = taskList.addTask(args[1]);
                    System.out.println(result);
                }
                case "update" -> {
                    if (args.length < 3) {
                        System.out.println("Usage: update <id> <description>");
                        return;
                    }
                    Long id = Long.parseLong(args[1]);
                    taskList.updateTask(id, args[2]);
                }
                case "delete" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: delete <id>");
                        return;
                    }
                    Long id = Long.parseLong(args[1]);
                    taskList.deleteTask(id);
                }
                case "mark-in-progress" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: mark-in-progress <id>");
                        return;
                    }
                    Long id = Long.parseLong(args[1]);
                    taskList.markInProgress(id);
                }
                case "mark-done" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: mark-done <id>");
                        return;
                    }
                    Long id = Long.parseLong(args[1]);
                    taskList.markAsDone(id);
                }
                case "list" -> {
                    if (args.length == 1) {
                        taskList.listTasks();
                    } else {
                        String filter = args[1].toLowerCase();
                        switch (filter) {
                            case "done" -> taskList.listDoneTasks();
                            case "todo" -> taskList.listTodoTasks();
                            case "in-progress" -> taskList.listInProgressTasks();
                            default -> System.out.println("Unknown filter: " + filter + ". Use done, todo, or in-progress.");
                        }
                    }
                }
                default -> {
                    System.out.println("Unknown command: " + command);
                    printUsage();
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Task ID must be a valid integer number.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: task-cli <command> [arguments]");
        System.out.println("Commands:");
        System.out.println("  add <description>");
        System.out.println("  update <id> <description>");
        System.out.println("  delete <id>");
        System.out.println("  mark-in-progress <id>");
        System.out.println("  mark-done <id>");
        System.out.println("  list [done|todo|in-progress]");
    }
}