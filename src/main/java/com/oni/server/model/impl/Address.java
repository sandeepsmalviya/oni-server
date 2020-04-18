package com.oni.server.model.impl;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

	private String city;
	private String state;
	private String pincode;
	private String line1;
	private String line2;
}
