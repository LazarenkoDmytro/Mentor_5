public class Main {
    public static void main(String[] args) {
        Group group = new Group();

        for (int i = 0; i < 11; i++) {
            try {
                Student student = new Student("Student " + (9 - i), 18 + i, i);
                group.addStudentToEnd(student);
            } catch (StudentLimitExceededException e) {
                e.printStackTrace();
            }
        }

        Student student = group.findByName("Student 3").get();

        group.removeStudentFromEnd();
        System.out.println(group);

        try {
            String studentJson = ObjectToJsonConverter.toJson(group.findByName("Student 3").orElseGet(() -> new Student("Default", 0, 0)));
            System.out.println(studentJson);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}