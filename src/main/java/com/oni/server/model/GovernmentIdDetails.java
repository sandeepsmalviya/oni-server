package com.oni.server.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Embeddable;
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
public class GovernmentIdDetails {

	
//	18 Govt. ID Type
//	19 Govt ID Number
//	20 Links to Govt ID
//	21 Police Verification
	
	
	
	private String governmentIdType;
	private String governmentIdNumber;

	private String linksToGovernmentId;

	private String policeVerfication;
	

}
