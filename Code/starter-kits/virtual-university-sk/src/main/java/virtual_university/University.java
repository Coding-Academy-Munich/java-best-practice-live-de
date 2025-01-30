package virtual_university;

import java.util.*;

public class University {
    private final String name;
    private final List<Student> students;
    private final List<Professor> professors;
    private final List<Lecture> lectures;

    public University(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.lectures = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(String name) {
        students.add(new Student(name, this));
    }

    public void addProfessor(String name) {
        professors.add(new Professor(name, this));
    }

    public void addLecture(String name, String professorName) {
        Professor professor = getProfessor(professorName);
        lectures.add(new Lecture(name, professor));
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getStudent(String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public Professor getProfessor(String name) {
        return professors.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public Lecture getLecture(String name) {
        return lectures.stream()
                .filter(l -> l.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        University that = (University) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

