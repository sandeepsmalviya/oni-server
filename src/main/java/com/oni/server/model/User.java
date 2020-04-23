package com.oni.server.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User // implements Serializable 
{

	@Id
	@GeneratedValue
	private Integer userId;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date registrationDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date joiningDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date leavingDate;

	@Embedded
	private PersonalDetails personalDetails;

	@Embedded
	private AccomodationDetails accomodationDetails;

	@Embedded
	private OccupationDetails occupationDetails;

	@Embedded
	private GovernmentIdDetails governmentIdDetails;

	public static User createEmptyUser() {

		User user = new User();

		PersonalDetails personalDetails = new PersonalDetails();
		AccomodationDetails accomodationDetails = new AccomodationDetails();
		OccupationDetails occupationDetails = new OccupationDetails();
		GovernmentIdDetails governmentIdDetails = new GovernmentIdDetails();
		user.setAccomodationDetails(accomodationDetails);
		user.setGovernmentIdDetails(governmentIdDetails);
		user.setOccupationDetails(occupationDetails);
		user.setPersonalDetails(personalDetails);

		Address address = new Address();
		personalDetails.setPermanentAddress(address);
		return user;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", personalDetails=" + personalDetails + ", accomodationDetails="
				+ accomodationDetails + ", occupationDetails=" + occupationDetails + ", governmentIdDetails="
				+ governmentIdDetails + ", registrationDate=" + registrationDate + ", joiningDate=" + joiningDate
				+ ", leavingDate=" + leavingDate + "]";
	}

}
