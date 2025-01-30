package virtual_university;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubmissionTest {
    @SuppressWarnings("FieldCanBeLocal")
    private University university;
    @SuppressWarnings("FieldCanBeLocal")
    private Student student;
    private Professor professor;
    @SuppressWarnings("FieldCanBeLocal")
    private Lecture lecture;
    private Submission submission;

    @BeforeEach
    public void setUp() {
        university = new University("Test University");
        university.addStudent("Test Student");
        university.addProfessor("Test Professor");
        university.addLecture("Test Lecture", "Test Professor");
        student = university.getStudent("Test Student");
        professor = university.getProfessor("Test Professor");
        lecture = university.getLecture("Test Lecture");
        lecture.addAssignment("Test Assignment", "2024-06-02");
        lecture.submitAssignment(student, "Test Assignment", "Test Submission");
        submission = lecture.getSubmissionsFor(lecture.getAssignment("Test Assignment")).get(0);
    }

    @Test
    public void testAssigningAGradeAlsoSetsTheGradingProfessor() {
        submission.assignGrade(professor, Grade.B);
        assertEquals(Grade.B, submission.getGrade());
        assertEquals(professor, submission.getGradingProfessor());
    }
}
