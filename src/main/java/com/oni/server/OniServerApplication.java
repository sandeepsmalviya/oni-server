package com.oni.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import com.oni.server.repository.UserRepository;
import com.oni.server.service.impl.util.ApachePOIExcelReaderUtil;

@SpringBootApplication
public class OniServerApplication {

	private static Logger logger = LoggerFactory.getLogger(OniServerApplication.class);

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(OniServerApplication.class, args);
		logger.info("Oni Application Started ...");

		insertSampleRecords(context);
		logger.info("Oni Application ready listen requests ..");

	}

	private static void insertSampleRecords(ApplicationContext context) {

		logger.info("Inserting sample data into database for testing from file path = "
				+ ApachePOIExcelReaderUtil.FILE_NAME);
		ApachePOIExcelReaderUtil apachePOIExcelReaderUtil = context.getBean(ApachePOIExcelReaderUtil.class);
		UserRepository userRepository = context.getBean(UserRepository.class);
		long count = userRepository.count(); 
		if (count > 0) {
			logger.debug("Sample records are already present  ="+ count);
			logger.debug("Sample records insertion from excel file is being skipped");
			return;
		}
		
		try {
			logger.debug("Trying to insert sample records into database from excel file ..");
			apachePOIExcelReaderUtil.reloadExcelData();
			logger.debug("Insert sample records into database from excel file is succeeded ..");
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
			System.exit(1);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
			System.exit(1);
		}

	}

}