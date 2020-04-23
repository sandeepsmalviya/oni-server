package com.oni.server.exception.message;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	private Type messageType;
	private String exceptionMessage;	
	private int statusCode;
	private HttpStatus httpStatus;
	private String path;
	@JsonInclude(Include.NON_NULL)
	private List<SubError> subErrors;
	
	
	public enum Type {
		PROCESSING_ERROR, VALIDATION_ERROR, CONSTRAINT_VIOLATIONS
	}

	
	 private void addSubError(SubError subError) {
	        if (subErrors == null) {
	            subErrors = new ArrayList<>();
	        }
	        subErrors.add(subError);
	    }

	    private void addValidationError(String object, String field, Object rejectedValue, String message) {
	        addSubError(new ValidationError(object, field, rejectedValue, message));
	    }

	    private void addValidationError(String object, String message) {
	        addSubError(new ValidationError(object, message));
	    }

	    private void addValidationError(FieldError fieldError) {
	        this.addValidationError(
	                fieldError.getObjectName(),
	                fieldError.getField(),
	                fieldError.getRejectedValue(),
	                fieldError.getDefaultMessage());
	    }

	    public void addValidationErrors(List<FieldError> fieldErrors) {
	        fieldErrors.forEach(this::addValidationError);
	    }

	    private void addValidationError(ObjectError objectError) {
	        this.addValidationError(
	                objectError.getObjectName(),
	                objectError.getDefaultMessage());
	    }

	    public void addValidationError(List<ObjectError> globalErrors) {
	        globalErrors.forEach(this::addValidationError);
	    }

	    /**
	     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
	     *
	     * @param cv the ConstraintViolation
	     */
	    private void addValidationError(ConstraintViolation<?> cv) {
	        this.addValidationError(
	                cv.getRootBeanClass().getSimpleName(),
	                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
	                cv.getInvalidValue(),
	                cv.getMessage());
	    }

	    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
	        constraintViolations.forEach(this::addValidationError);
	    }

}
