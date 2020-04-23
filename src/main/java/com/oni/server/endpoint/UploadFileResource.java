package com.oni.server.endpoint;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oni.server.model.User;
import com.oni.server.service.FileReadingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value = "/api", description = " It will provide API to read excel file and save its contents to databse")
@CrossOrigin(origins = "*")
public class UploadFileResource {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileResource.class);

	@Autowired
	private FileReadingService fileReadingService;

//	@ApiIgnore
	@ApiOperation(value = "Upload the excel file, process it and save its contents to database", notes = "Upload the excel file, process it and save its contents to database", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 400, message = "Invalid Data supplied"),
			@ApiResponse(code = 404, message = "No data found"),
			@ApiResponse(code = 500, message = "Interal processing error") })
	@PostMapping("/excel-upload")
	public ResponseEntity<List<User>> uploadPdfFile(@RequestParam("file") MultipartFile pdfFile)
			throws InstantiationException, IllegalAccessException, IOException, ParseException {

		logger.info("/api/excel-upload url called with uploadPdfFile() method");
		List<User> list = fileReadingService.readFile(pdfFile.getInputStream());
		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<>(list, HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
