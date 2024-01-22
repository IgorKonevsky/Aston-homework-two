package util;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.entities.Group;
import lombok.Getter;

@Getter
public class GroupTestUtil {
    private Group expectedGroup;
    private GroupDTO expectedGroupDTO;

    public GroupTestUtil() {
        expectedGroup = Group.builder()
                .id(1L)
                .number(101L)
                .build();
        expectedGroupDTO = GroupDTO.builder()
                .id(1L)
                .number(101L)
                .build();

    }


}
