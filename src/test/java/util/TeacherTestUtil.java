package util;

import com.example.restApiCrudApp.dto.TeacherDTO;
import com.example.restApiCrudApp.entities.Teacher;
import lombok.Getter;

@Getter
public class TeacherTestUtil {
    private Teacher expectedTeacher;
    private TeacherDTO expectedTeacherDTO;

    public TeacherTestUtil() {
        expectedTeacher = Teacher.builder()
                .id(1L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .subjectId(101L)
                .build();
        expectedTeacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .subjectId(101L)
                .build();
    }
}
