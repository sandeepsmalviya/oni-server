package com.oni.server.exception.handler;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oni.server.exception.message.ErrorMessage;
import com.oni.server.exception.message.ErrorMessage.Type;

@RestControllerAdvice
public class OniServerExceptionHandler {

	// TODO
	// HttpMessageNotReadableException

	Logger logger = LoggerFactory.getLogger(OniServerExceptionHandler.class);

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleNumberFormatException(NumberFormatException exception,
			HttpServletRequest request) {

		logger.debug("NumberFormatException = " + exception.getMessage());

		Type type = Type.PROCESSING_ERROR;
		String exceptionMessage = getMessageFromException(exception);
		ErrorMessage errorMessage = new ErrorMessage(new Date(System.currentTimeMillis()), type, exceptionMessage,
				HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, request.getRequestURI(), null);

		return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException exception,
			HttpServletRequest request) {

		logger.debug("NullPointerException = " + exception.getMessage());

		Type type = Type.PROCESSING_ERROR;
		String exceptionMessage = getMessageFromException(exception);
		ErrorMessage errorMessage = new ErrorMessage(new Date(System.currentTimeMillis()), type, exceptionMessage,
				HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, request.getRequestURI(), null);

		return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());

	}

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleException(Exception exception, HttpServletRequest request) {

		logger.debug("Exception = " + exception.getMessage());

		Type type = Type.PROCESSING_ERROR;
		String exceptionMessage = getMessageFromException(exception);

		ErrorMessage errorMessage = new ErrorMessage(new Date(System.currentTimeMillis()), type, exceptionMessage,
				HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, request.getRequestURI(), null);

		return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());

	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException exception,
			HttpServletRequest request) {
		logger.debug("ConstraintViolationException = " + exception.getMessage());
		Type type = Type.CONSTRAINT_VIOLATIONS;

		String exceptionMessage = getMessageFromException(exception);
		ErrorMessage errorMessage = new ErrorMessage(new Date(System.currentTimeMillis()), type, exceptionMessage,
				HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, request.getRequestURI(), null);
		errorMessage.addValidationErrors(exception.getConstraintViolations());
		return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());

	}

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {

		logger.debug("MethodArgumentNotValidException = " + exception.getMessage());
		Type type = Type.VALIDATION_ERROR;

		String exceptionMessage = getMessageFromException(exception);
		exceptionMessage = "Validation failed";
		ErrorMessage errorMessage = new ErrorMessage(new Date(System.currentTimeMillis()), type, exceptionMessage,
				HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, request.getRequestURI(), null);

		errorMessage.addValidationErrors(exception.getBindingResult().getFieldErrors());

//		List<FieldError> list = exception.getBindingResult().getFieldErrors();
//
//		List<SubError> validationErrors = new ArrayList<>();
//		for (FieldError fieldError : list) {
//
//			ValidationError validationError = new ValidationError();
//
//			validationError.setObjectName(fieldError.getObjectName());
//			validationError.setField(fieldError.getField());
//			validationError.setMessage(fieldError.getDefaultMessage());
//			if (fieldError.getRejectedValue() != null) {
//				validationError.setRejectedValue(fieldError.getRejectedValue());
//			}
//
//			validationErrors.add(validationError);
//
//		}
//
//		errorMessage.setSubErrors(validationErrors);

		return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());

	}

	private String getMessageFromException(Exception exception) {
		String message = null;
		message = exception.getMessage();

		if (message == null) {
			message = exception.getLocalizedMessage();
		}

		return message;
	}

}
