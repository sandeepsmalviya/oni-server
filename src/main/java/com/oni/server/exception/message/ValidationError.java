package com.oni.server.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError extends SubError {

	private String objectName;
	private String field;
	private Object rejectedValue;
	private String message;
	public ValidationError(String objectName, String message) {
		super();
		this.objectName = objectName;
		this.message = message;
	}

	
	
}
