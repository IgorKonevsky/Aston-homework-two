package com.example.restApiCrudApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GroupDTO {
    private Long id;
    private Long number;
    private List<TeacherDTO> teacherDTOS;
}
