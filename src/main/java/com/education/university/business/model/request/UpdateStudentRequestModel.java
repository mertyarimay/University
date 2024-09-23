package com.education.university.business.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStudentRequestModel {
    @Size(min = 11,max = 11)
    private String studentNo;
}
