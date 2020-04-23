package com.oni.server.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	
	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	@Range(min=0, max=60, message = "age should not be negative and not more than 60")
	private Integer age;
}
