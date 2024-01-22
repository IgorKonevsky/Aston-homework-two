package util;

import com.example.restApiCrudApp.dto.SubjectDTO;
import com.example.restApiCrudApp.entities.Subject;
import lombok.Getter;

@Getter
public class SubjectTestUtil {
    private Subject expectedSubject;
    private SubjectDTO expectedSubjectDTO;

    public SubjectTestUtil() {
        expectedSubject = Subject.builder()
                .id(1L)
                .name("name")
                .build();
        expectedSubjectDTO = SubjectDTO.builder()
                .id(1L)
                .name("name")
                .build();
    }
}
