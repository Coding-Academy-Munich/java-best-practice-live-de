package virtual_university;

import java.util.*;

public class Lecture {
    private final String name;
    private final Professor professor;
    private final List<Assignment> assignments;
    private final Map<Assignment, List<Submission>> submissions;

    public Lecture(String name, Professor professor) {
        this.name = name;
        this.professor = professor;
        this.assignments = new ArrayList<>();
        this.submissions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void addAssignment(String name, String dueDate) {
        assignments.add(new Assignment(name, dueDate));
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addSubmission(Submission submission) {
        submissions.computeIfAbsent(submission.getAssignment(), k -> new ArrayList<>()).add(submission);
    }

    public List<Submission> getSubmissionsFor(Assignment assignment) {
        return submissions.getOrDefault(assignment, new ArrayList<>());
    }

    public Assignment getAssignment(String name) {
        return assignments.stream()
                .filter(a -> a.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public void submitAssignment(Student student, String assignmentName, String data) {
        Assignment assignment = getAssignment(assignmentName);
        Submission submission = new Submission(student, assignment, data);
        addSubmission(submission);
        student.addSubmission(submission);
    }

    @SuppressWarnings("unused")
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lecture: ").append(name).append("\n");
        sb.append("Professor: ").append(professor.getName()).append("\n");
        sb.append("Assignments:\n");
        for (Assignment assignment : assignments) {
            sb.append("  ").append(assignment.name()).append(" (due ").append(assignment.dueDate()).append(")\n");
        }
        sb.append("Submissions:\n");
        for (Map.Entry<Assignment, List<Submission>> entry : submissions.entrySet()) {
            sb.append("  ").append(entry.getKey().name()).append(":\n");
            for (Submission submission : entry.getValue()) {
                sb.append("    ").append(submission.getStudent().getName()).append(": ").append(submission.getData());
                if (submission.getGrade() != Grade.NONE) {
                    sb.append(" (graded by ").append(submission.getGradingProfessor().getName())
                            .append(" with ").append(submission.getGrade()).append(")");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
