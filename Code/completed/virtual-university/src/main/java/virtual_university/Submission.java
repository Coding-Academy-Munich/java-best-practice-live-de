package virtual_university;

public class Submission {
    private final Student student;
    private final Assignment assignment;
    private final String data;
    private Professor gradedBy;
    private Grade grade;

    public Submission(Student student, Assignment assignment, String data) {
        this(student, assignment, data, null, Grade.NONE);
    }

    public Submission(Student student, Assignment assignment, String data, Professor gradedBy, Grade grade) {
        this.student = student;
        this.assignment = assignment;
        this.data = data;
        this.gradedBy = gradedBy;
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public String getData() {
        return data;
    }

    public Professor getGradingProfessor() {
        return gradedBy;
    }

    public Grade getGrade() {
        return grade;
    }

    public void assignGrade(Professor professor, Grade grade) {
        this.gradedBy = professor;
        this.grade = grade;
    }
}

