package com.education.university.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllStudentResponse {
    private String name;
    private String lastName;
    private  String studentNo;
    private String sectionName;
}
