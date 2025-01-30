package virtual_university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniversityTest {
    private University university;

    @BeforeEach
    public void setUp() {
        university = new University("Test University");
    }

    @Test
    public void testCanAddStudent() {
        university.addStudent("Alice");

        assertEquals(1, university.getStudents().size());
        Student student = university.getStudents().get(0);
        assertEquals("Alice", student.getName());
        assertEquals(university, student.getUniversity());
    }

    @Test
    public void testCanGetStudentByName() {
        university.addStudent("Alice");
        university.addStudent("Bob");

        assertEquals("Alice", university.getStudent("Alice").getName());
        assertEquals("Bob", university.getStudent("Bob").getName());
    }

    @Test
    public void testCanAddProfessor() {
        university.addProfessor("Bob");

        assertEquals(1, university.getProfessors().size());
        Professor professor = university.getProfessors().get(0);
        assertEquals("Bob", professor.getName());
        assertEquals(university, professor.getUniversity());
    }

    @Test
    public void testCanGetProfessorByName() {
        university.addProfessor("Alice");
        university.addProfessor("Bob");

        assertEquals("Alice", university.getProfessor("Alice").getName());
        assertEquals("Bob", university.getProfessor("Bob").getName());
    }

    @Test
    public void testCanAddLecture() {
        university.addProfessor("Alice");
        university.addLecture("Math", "Alice");

        assertEquals(1, university.getLectures().size());
        Lecture lecture = university.getLectures().get(0);
        assertEquals("Math", lecture.getName());
        assertEquals("Alice", lecture.getProfessor().getName());
        assertTrue(lecture.getAssignments().isEmpty());
    }

    @Test
    public void testCanGetLectureByName() {
        university.addProfessor("Alice");
        university.addLecture("Math", "Alice");
        university.addLecture("Physics", "Alice");

        assertEquals("Math", university.getLecture("Math").getName());
        assertEquals("Physics", university.getLecture("Physics").getName());
    }
}
