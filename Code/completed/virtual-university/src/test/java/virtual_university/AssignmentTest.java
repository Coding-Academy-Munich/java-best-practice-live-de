package virtual_university;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AssignmentTest {
    @Test
    public void testAssignment() {
        Assignment assignment = new Assignment("Test Assignment", "2024-06-02");
        assertEquals("Test Assignment", assignment.name());
        assertEquals("2024-06-02", assignment.dueDate());
    }
}
