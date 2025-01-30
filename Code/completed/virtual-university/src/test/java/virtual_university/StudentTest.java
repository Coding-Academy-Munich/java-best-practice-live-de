package virtual_university;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {
    @SuppressWarnings("FieldCanBeLocal")
    private University university;
    private Student student;

    @BeforeEach
    public void setUp() {
        university = new University("Test University");
        university.addStudent("Test Student");
        university.addProfessor("Test Professor");
        university.addLecture("Test Lecture", "Test Professor");
        university.getLecture("Test Lecture").addAssignment("Test Assignment", "2024-06-02");
        student = university.getStudent("Test Student");
    }

    @Test
    public void testStudentInitiallyHasNoSubmissions() {
        assertTrue(student.getSubmissions().isEmpty());
    }

    @Test
    public void testCanSubmitAnAssignment() {
        student.submitAssignment("Test Lecture", "Test Assignment", "Test Submission");

        assertEquals(1, student.getSubmissions().size());
        Submission submission = student.getSubmission("Test Assignment");
        assertEquals("Test Submission", submission.getData());
    }
}
