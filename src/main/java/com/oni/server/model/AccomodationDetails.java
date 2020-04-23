package com.oni.server.model;

import java.sql.Timestamp;

import javax.persistence.Column;
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
public class AccomodationDetails {

	private String bedNumber;
	private String registrationNumber;

	private String accomodationType;

	@Column(columnDefinition = "double default 0.0")
	private double depositAmount;

	@Column(columnDefinition = "double default 0.0")
	private double rentAmount;

	private String preferredMealTypeVegNonVeg;

}
