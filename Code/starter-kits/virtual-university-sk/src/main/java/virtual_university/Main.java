package virtual_university;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Create a University instance
        University uni = new University("The University of Nowhere");

        // Add students to the university
        uni.addStudent("Alice");
        uni.addStudent("Bob");
        uni.addStudent("Carol");
        uni.addStudent("Dave");

        Student alice = uni.getStudent("Alice");
        Student bob = uni.getStudent("Bob");
        Student carol = uni.getStudent("Carol");
        Student dave = uni.getStudent("Dave");

        // Add professors to the university
        uni.addProfessor("Mr. Smith");
        uni.addProfessor("Ms. Jones");

        Professor mrSmith = uni.getProfessor("Mr. Smith");
        Professor msJones = uni.getProfessor("Ms. Jones");

        // Create some lectures
        uni.addLecture("Math 101", "Mr. Smith");
        uni.addLecture("Physics 201", "Mr. Smith");
        uni.addLecture("English 101", "Ms. Jones");
        uni.addLecture("History 203", "Ms. Jones");

        Lecture mathCourse = uni.getLecture("Math 101");
        //noinspection unused
        Lecture physicsCourse = mrSmith.getLecture("Physics 201");
        Lecture englishCourse = msJones.getLecture("English 101");
        //noinspection unused
        Lecture historyCourse = msJones.getLecture("History 203");

        // Create some assignments
        List<Assignment> assignments = List.of(
                new Assignment("Homework 1", "2024-06-01"),
                new Assignment("Homework 2", "2024-06-08"),
                new Assignment("Homework 3", "2024-06-15"),
                new Assignment("Homework 4", "2024-06-22")
        );

        for (Assignment entry : assignments) {
            String name = entry.name();
            String date = entry.dueDate();

            mathCourse.addAssignment(name, date);
            mrSmith.createAssignment("Physics 201", name, date);
            englishCourse.addAssignment(name, date);
            msJones.createAssignment("History 203", name, date);
        }

        // Students submit assignments
        alice.submitAssignment("Math 101", "Homework 1", "1 + 1 = 2");
        bob.submitAssignment("Math 101", "Homework 1", "1 + 1 < 3");
        carol.submitAssignment("Physics 201", "Homework 1", "F = m * a");
        dave.submitAssignment("History 203", "Homework 1", "The American Revolution was in 1776");

        for (Student student : uni.getStudents()) {
            student.submitAssignment("English 101", "Homework 1", "To be or not to be");
        }

        // Professors grade the assignments
        for (Professor professor : uni.getProfessors()) {
            for (Lecture lecture : professor.getLectures()) {
                for (Assignment assignment : lecture.getAssignments()) {
                    for (Submission submission : lecture.getSubmissionsFor(assignment)) {
                        professor.gradeSubmission(submission, getRandomGrade());
                    }
                }
            }
        }

        // Print Lecture details
        for (Lecture lecture : uni.getLectures()) {
            System.out.println(lecture.getInfo() + "\n");
        }
    }

    private static Grade getRandomGrade() {
        Random random = new Random();
        Grade[] grades = Grade.values();
        return grades[random.nextInt(grades.length - 1)];
    }
}
