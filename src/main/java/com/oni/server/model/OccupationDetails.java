package com.oni.server.model;

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
public class OccupationDetails {

	private String companyName;
	private String designation;
	private String linksToCompanyLetter;
	private String linksToCompanyId;
}
