package virtual_university;

import java.util.*;

public class Student {
    private final String name;
    private University university;
    private final List<Submission> submissions;

    public Student(String name, University university) {
        this.name = name;
        this.university = university;
        this.submissions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public University getUniversity() {
        return university;
    }

    @SuppressWarnings("unused")
    public void setUniversity(University university) {
        this.university = university;
    }

    public void submitAssignment(String lectureName, String assignmentName, String data) {
        Lecture lecture = university.getLecture(lectureName);
        lecture.submitAssignment(this, assignmentName, data);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public Submission getSubmission(String assignmentName) {
        return submissions.stream()
                .filter(s -> s.getAssignment().name().equals(assignmentName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    void addSubmission(Submission submission) {
        submissions.add(submission);
    }
}
