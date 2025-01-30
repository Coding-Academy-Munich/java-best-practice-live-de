package virtual_university;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LectureTest {
    @SuppressWarnings("FieldCanBeLocal")
    private University university;
    private Lecture lecture;
    private Student student;

    @BeforeEach
    public void setUp() {
        university = new University("Test University");
        university.addProfessor("Test Professor");
        university.addStudent("Test Student");
        university.addLecture("Test Lecture", "Test Professor");
        lecture = university.getLecture("Test Lecture");
        student = university.getStudent("Test Student");
    }

    @Test
    public void testAddSingleAssignment() {
        lecture.addAssignment("Test Assignment", "2024-06-02");
        Assignment assignment = lecture.getAssignment("Test Assignment");
        assertEquals("Test Assignment", assignment.name());
        assertEquals("2024-06-02", assignment.dueDate());
    }

    @Test
    public void testAddMultipleAssignments() {
        lecture.addAssignment("Test Assignment 1", "2024-06-02");
        lecture.addAssignment("Test Assignment 2", "2024-06-03");
        lecture.addAssignment("Test Assignment 3", "2024-06-04");

        assertEquals(3, lecture.getAssignments().size());
        assertEquals("2024-06-02", lecture.getAssignment("Test Assignment 1").dueDate());
        assertEquals("2024-06-03", lecture.getAssignment("Test Assignment 2").dueDate());
        assertEquals("2024-06-04", lecture.getAssignment("Test Assignment 3").dueDate());
    }

    @Test
    public void testSubmitAssignment() {
        lecture.addAssignment("Test Assignment", "2024-06-02");
        lecture.submitAssignment(student, "Test Assignment", "Test Submission");
        Assignment assignment = lecture.getAssignment("Test Assignment");
        List<Submission> submissions = lecture.getSubmissionsFor(assignment);
        assertEquals(1, submissions.size());
        assertEquals("Test Student", submissions.get(0).getStudent().getName());
        assertEquals("Test Submission", submissions.get(0).getData());
    }

    @Test
    public void testSubmitMultipleAssignments() {
        lecture.addAssignment("Test Assignment 1", "2024-06-02");
        lecture.addAssignment("Test Assignment 2", "2024-06-03");

        lecture.submitAssignment(student, "Test Assignment 1", "Test Submission 1.1");
        lecture.submitAssignment(student, "Test Assignment 1", "Test Submission 1.2");
        lecture.submitAssignment(student, "Test Assignment 1", "Test Submission 1.3");
        lecture.submitAssignment(student, "Test Assignment 2", "Test Submission 2.1");
        lecture.submitAssignment(student, "Test Assignment 2", "Test Submission 2.2");

        Assignment assignment1 = lecture.getAssignment("Test Assignment 1");
        Assignment assignment2 = lecture.getAssignment("Test Assignment 2");
        assertEquals(3, lecture.getSubmissionsFor(assignment1).size());
        assertEquals(2, lecture.getSubmissionsFor(assignment2).size());
    }
}
