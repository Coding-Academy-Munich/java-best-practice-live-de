package virtual_university;

import java.util.*;

public class Professor {
    private final String name;
    private University university;

    public Professor(String name, University university) {
        this.name = name;
        this.university = university;
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

    public List<Lecture> getLectures() {
        return university.getLectures().stream()
                .filter(lecture -> lecture.getProfessor().equals(this))
                .collect(java.util.stream.Collectors.toList());
    }

    public Lecture getLecture(String courseName) {
        return getLectures().stream()
                .filter(lecture -> lecture.getName().equals(courseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
    }

    public void createAssignment(String lectureName, String assignmentName, String dueDate) {
        getLecture(lectureName).addAssignment(assignmentName, dueDate);
    }

    public void gradeSubmission(Submission submission, Grade grade) {
        submission.assignGrade(this, grade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(name, professor.name) && Objects.equals(university, professor.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, university);
    }
}
