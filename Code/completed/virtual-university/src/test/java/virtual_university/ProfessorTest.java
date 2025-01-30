package virtual_university;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProfessorTest {
    private University university;
    private Professor professor;

    @BeforeEach
    public void setUp() {
        university = new University("Test University");
        university.addProfessor("Test Professor");
        professor = university.getProfessor("Test Professor");
    }

    @Test
    public void testProfessorInitiallyHasNoLectures() {
        assertTrue(professor.getLectures().isEmpty());
    }

    @Test
    public void testLecturesAreUpdatedWhenNewLectureIsAdded() {
        university.addLecture("Test Lecture", "Test Professor");
        assertEquals(1, professor.getLectures().size());
        assertEquals("Test Lecture", professor.getLecture("Test Lecture").getName());
    }

    @Test
    public void testCanCreateAssignments() {
        university.addLecture("Test Lecture", "Test Professor");
        Lecture lecture = university.getLecture("Test Lecture");

        professor.createAssignment("Test Lecture", "Test Assignment", "2024-06-02");

        assertEquals(1, lecture.getAssignments().size());
        assertEquals("Test Assignment", lecture.getAssignment("Test Assignment").name());
    }

    @Test
    public void testCanGradeSubmissions() {
        university.addStudent("Test Student");
        university.addLecture("Test Lecture", "Test Professor");
        Lecture lecture = university.getLecture("Test Lecture");
        Student student = university.getStudent("Test Student");
        lecture.addAssignment("Test Assignment", "2024-06-02");
        lecture.submitAssignment(student, "Test Assignment", "Test Submission");
        Submission submission = lecture.getSubmissionsFor(lecture.getAssignment("Test Assignment")).get(0);

        professor.gradeSubmission(submission, Grade.C);

        assertEquals(Grade.C, submission.getGrade());
        assertNotNull(submission.getGradingProfessor());
        assertEquals(professor, submission.getGradingProfessor());
    }
}
