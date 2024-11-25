import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class Group {

    private static final int MAX_STUDENTS = 10;
    private final Student[] students;
    private int studentsCount;

    public Group() {
        students = new Student[10];
        studentsCount = 0;
    }

    public void addStudentToEnd(Student student) throws StudentLimitExceededException {
        if (studentsCount >= MAX_STUDENTS) {
            throw new StudentLimitExceededException("Cannot add more than " + MAX_STUDENTS + " students.");
        }

        students[studentsCount] = student;
        studentsCount++;
    }

    public void removeStudentFromEnd() {
        if (studentsCount > 0) {
            students[studentsCount - 1] = null;
            studentsCount--;
        } else {
            System.out.println("The list is empty.");
        }
    }

    public Optional<Student> findByName(String name) {
        return Stream.of(students).filter(student -> name.equals(student.getName())).findFirst();
    }

    @Override
    public String toString() {
        Student[] temp = new Student[studentsCount];
        System.arraycopy(students, 0, temp, 0, studentsCount);
        Arrays.parallelSort(temp, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        return "Group{" +
                "students=" + Arrays.toString(temp) +
                '}';
    }
}
