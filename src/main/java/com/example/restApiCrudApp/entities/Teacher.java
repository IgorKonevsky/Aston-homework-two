package com.example.restApiCrudApp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Teacher {
    private Long id;
    private String firstName;
    private String lastName;
    private Long subjectId;
    private List<Group> groups;

}
