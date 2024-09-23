package com.education.university.business.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequestModel {
    private int id;
    @NotBlank
    @NotNull
    @Size(min = 1)
    private String name;
    @NotBlank
    @NotNull
    @Size(min = 1)
    private String lastName;
    @Size(min = 11,max = 11)
    private String studentNo;
    private int sectionId;

}
