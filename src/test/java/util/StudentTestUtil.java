package util;

import com.example.restApiCrudApp.dto.StudentDTO;
import com.example.restApiCrudApp.entities.Student;
import lombok.Getter;

@Getter
public class StudentTestUtil {
    private Student expectedStudent;
    private StudentDTO expectedStudentDTO;

    public StudentTestUtil() {
        expectedStudent = Student.builder()
                .id(1L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .groupId(101L)
                .build();
        expectedStudentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .groupId(101L)
                .build();

    }
}
