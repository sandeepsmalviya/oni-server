package com.oni.server.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalDetails {

	public enum Gender {
		MALE, FEMALE, TRANSGENDER
	}

	private String firstName;
	private String middleName;
	private String lastName;
	

	

	private String contactNumber;
	private String emergencyContactNumber;
	private String email;
	private Gender gender;
	
	@Embedded
	private Address permanentAddress;

}
