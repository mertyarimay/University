package com.education.university.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationProblems extends ProblemDetails {
    private HashMap<String,String>validateProblems;
}
