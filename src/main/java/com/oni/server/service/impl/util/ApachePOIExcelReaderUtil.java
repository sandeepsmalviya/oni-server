package com.oni.server.service.impl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oni.server.model.PersonalDetails.Gender;
import com.oni.server.model.User;
import com.oni.server.repository.UserRepository;

@Service
public class ApachePOIExcelReaderUtil {

	private static Logger logger = LoggerFactory.getLogger(ApachePOIExcelReaderUtil.class);
	public static final String FILE_NAME = "/home/aarti/Development/projects/oni_applications/oni-server/src/main/resources/Oni-Register-Book-040519.xlsx";

	@Autowired
	private UserRepository userRepository;
//
//	public static void main(String[] args) throws IOException {
//
//		ApachePOIExcelReaderUtil readerUtil = new ApachePOIExcelReaderUtil();
//		readerUtil.reloadExcelData();
//	}

	public void reloadExcelData() throws IOException {

		FileInputStream excelFileInputStream = new FileInputStream(new File(FILE_NAME));
		this.processExcelFile(excelFileInputStream);

	}

	public List<User> processExcelFile(InputStream excelFileInputStream) throws IOException {

		logger.info("Processing Excel Row File from path...  "+ FILE_NAME );
		List<User> list = this.parseExcelFile(excelFileInputStream);
		logger.info("Total number of records extracted from excel file =" + (list == null ? "null" : list.size()));
		logger.debug("Excel file proocessing is successful");
		logger.debug("Trying to insert records into the database ...");
		saveToDatabase(list);

		// Java object to JSON string
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//		System.out.println(jsonString);

		return list;

	}

	private void saveToDatabase(List<User> list) {
		userRepository.saveAll(list);
	}

	public List<User> parseExcelFile(InputStream excelFileInputStream) throws IOException {

		List<User> list = new ArrayList<>();

		Workbook workbook = new XSSFWorkbook(excelFileInputStream);
		Sheet datatypeSheet = workbook.getSheetAt(2);
		Iterator<Row> rowIterator = datatypeSheet.iterator();

		while (rowIterator.hasNext()) {

			Row currentRow = rowIterator.next();
			if (currentRow.getRowNum() < 3) {
				continue;
			}
			// logger.debug("Processing Excel Row Number = " + (currentRow.getRowNum() +
			// 1));
			User user = procesCurrentRow(currentRow);

			if (user == null) {
				continue;
			}

			list.add(user);
		}

		return list;

	}

	private User procesCurrentRow(Row currentRow) {
		User user = User.createEmptyUser();

		Iterator<Cell> cellIterator = currentRow.iterator();

		while (cellIterator.hasNext()) {

			Cell currentCell = cellIterator.next();

			int columnIndex = currentCell.getColumnIndex();
			String cellValue = currentCell.toString();
			if (cellValue == null || cellValue.isEmpty()) {
				cellValue = "";
			}
			try {
				cellValue = cellValue.trim();
			} catch (Exception ex) {
				// ex.printStackTrace();
			}

			try {

				ProcessMessage processMessage = fillCellDataToObject(currentRow, columnIndex, cellValue, user);
				if (processMessage == ProcessMessage.SKIP_THIIS_ROW) {
					user = null;
					break;
				}

			} catch (Exception exception) {

				logger.info("Processing rowIndex=" + currentRow.getRowNum() + " " + "columnIndex = " + columnIndex
						+ "   cellValue=" + cellValue);
				exception.printStackTrace();
			}
		}

		return user;
	}

	public enum ProcessMessage {
		ERROR, SUCCESS, SKIP_THIIS_ROW;
	}

	private ProcessMessage fillCellDataToObject(Row currentRow, int columnIndex, String cellValue, User user) {

		switch (columnIndex) {
		case 0:
			try {
				user.setUserId((int) Double.parseDouble(cellValue));
			} catch (Exception exception) {
				// logger.debug("Paring For UserId = "+ exception.getMessage());
				return ProcessMessage.SKIP_THIIS_ROW;
			}
			break;
		case 1:
			if (cellValue.isEmpty()) {
				return ProcessMessage.SKIP_THIIS_ROW;
			}
			myCustomNameProcessor(cellValue, user);
			break;
		case 2:
			user.setJoiningDate(getMyDateInFormat(currentRow, cellValue, columnIndex));
			break;
		case 3:
			user.getPersonalDetails().getPermanentAddress().setLine1(cellValue);
			break;
		case 4:
			user.getPersonalDetails().setContactNumber(cellValue);
			break;
		case 5:
			user.getPersonalDetails().setEmergencyContactNumber(cellValue);
			break;
		case 6:
			user.getPersonalDetails().setEmail(cellValue);
			break;
		case 7:
			user.getPersonalDetails().setGender(getMyGender(cellValue));
			break;
		case 8:
			user.getAccomodationDetails().setBedNumber(cellValue);
			break;
		case 9:
			user.getAccomodationDetails().setRegistrationNumber(cellValue);
			break;

		case 10:
			user.getAccomodationDetails().setAccomodationType(getMyAccomodationType(cellValue));
			break;

		case 11:
			if (cellValue == null || cellValue.isEmpty()) {
				user.getAccomodationDetails().setDepositAmount(0.0);
			} else {
				user.getAccomodationDetails().setDepositAmount(Double.parseDouble(cellValue));
			}
			break;
		case 12:
			if (cellValue == null || cellValue.isEmpty()) {
				user.getAccomodationDetails().setRentAmount(0.0);
			} else {
				user.getAccomodationDetails().setRentAmount(Double.parseDouble(cellValue));
			}
			break;
		case 13:
			user.getAccomodationDetails().setPreferredMealTypeVegNonVeg(getMyPreferredMeaTypel(cellValue));
			break;
		case 14:
			user.getOccupationDetails().setCompanyName(cellValue);
			break;
		case 15:
			user.getOccupationDetails().setDesignation(cellValue);
			break;
		case 16:
			user.getOccupationDetails().setLinksToCompanyLetter(cellValue);
			break;
		case 17:
			user.getOccupationDetails().setLinksToCompanyId(cellValue);
			break;
		case 18:
			user.getGovernmentIdDetails().setGovernmentIdType(cellValue);
			break;
		case 19:
			user.getGovernmentIdDetails().setGovernmentIdNumber(cellValue);
			break;

		case 20:
			user.getGovernmentIdDetails().setLinksToGovernmentId(cellValue);
			break;
		case 21:
			user.getGovernmentIdDetails().setPoliceVerfication(cellValue);
			break;
		case 22:
			user.setLeavingDate(getMyDateInFormat(currentRow, cellValue, columnIndex));
			break;
		default:
			break;
		}

		return ProcessMessage.SUCCESS;

//		0 Srl #
//		1 Full Name
//		2 Date of Joining
//		3 Permanent Address
//		4 Contact Number
//		5 Emergency Contact Number
//		6 Email ID
//		7 Gender
//		8 Bed No.
//		9 Regn. No.
//		10 Accommodation Type
//		11 Deposit Amt.
//		12 Rent Amt.
//		13 Preferable Meal Veg/Non Veg
//		14 Company Name
//		15 Designation
//		16 Links to Company Letter
//		17 Links to Company ID
//		18 Govt. ID Type
//		19 Govt ID Number
//		20 Links to Govt ID
//		21 Police Verification
//		22 Date of Leaving
//		23 Recpt #

	}

	private void myCustomNameProcessor(String cellValue, User user) {
		String[] sa = cellValue.split("\\s+");
		if (sa.length == 1) {
			user.getPersonalDetails().setFirstName(sa[0]);
		}
		if (sa.length == 2) {
			user.getPersonalDetails().setFirstName(sa[0]);
			user.getPersonalDetails().setLastName(sa[1]);
		}
		if (sa.length == 3) {
			user.getPersonalDetails().setFirstName(sa[0]);
			user.getPersonalDetails().setMiddleName(sa[1]);
			user.getPersonalDetails().setLastName(sa[2]);
		}

	}

	private String getMyPreferredMeaTypel(String cellValue) {
		return cellValue;
	}

	private String getMyAccomodationType(String cellValue) {
		return cellValue;
	}

	private Gender getMyGender(String cellValue) {
		if (cellValue.equalsIgnoreCase("male")) {
			return Gender.MALE;
		} else if (cellValue.equalsIgnoreCase("female")) {
			return Gender.FEMALE;
		} else if (cellValue.equalsIgnoreCase("transgender")) {
			return Gender.TRANSGENDER;
		}
		return null;
	}

	private Date getMyDateInFormat(Row currentRow, String cellValue, int columnIndex) {

		try {

			if (cellValue == null || cellValue.isEmpty()) {
				return null;
			}

			String[] sa = cellValue.split("-");

			if (sa.length != 3) {
				throw new ParseException("Date cellValue=" + cellValue, 0);
			}
			String dd = sa[0];

			if (dd == null || dd.isEmpty()) {
				throw new ParseException("Date cellValue=" + cellValue, 0);
			}

			String mmm = sa[1];

			if (mmm == null || mmm.isEmpty()) {
				throw new ParseException("Date cellValue=" + cellValue, 0);
			}

			String MM = getMyCustomMonth(mmm);

			String yy = sa[2];

			if (yy == null || yy.isEmpty()) {
				throw new ParseException("Date cellValue=" + cellValue, 0);
			}
			// String yyyy = "20" + yy;
			String yyyy = yy;
			String myDateInFormat = dd + "-" + MM + "-" + yyyy;

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date myDate = new Date(sdf.parse(myDateInFormat).getTime());

			return myDate;
		} catch (ParseException parseException) {
			logger.info("Processing for date ==> rowIndex=" + currentRow.getRowNum() + " " + "columnIndex = "
					+ columnIndex + "   cellValue=" + cellValue);
			parseException.printStackTrace();

			return null;
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			logger.info("Processing for date ==> rowIndex=" + currentRow.getRowNum() + " " + "columnIndex = "
					+ columnIndex + "   cellValue=" + cellValue);
			arrayIndexOutOfBoundsException.printStackTrace();

			return null;
		}

	}

	private String getMyCustomMonth(String mmm) throws ParseException {

		if (mmm.toLowerCase().startsWith("jan")) {
			return "01";
		} else if (mmm.toLowerCase().startsWith("feb")) {
			return "02";
		} else if (mmm.toLowerCase().startsWith("mar")) {
			return "03";
		} else if (mmm.toLowerCase().startsWith("apr")) {
			return "04";
		} else if (mmm.toLowerCase().startsWith("may")) {
			return "05";
		} else if (mmm.toLowerCase().startsWith("jun")) {
			return "06";
		} else if (mmm.toLowerCase().startsWith("jul")) {
			return "07";
		} else if (mmm.toLowerCase().startsWith("aug")) {
			return "08";
		} else if (mmm.toLowerCase().startsWith("sep")) {
			return "09";
		} else if (mmm.toLowerCase().startsWith("oct")) {
			return "10";
		} else if (mmm.toLowerCase().startsWith("nov")) {
			return "11";
		} else if (mmm.toLowerCase().startsWith("dec")) {
			return "12";
		}

		if (Integer.parseInt(mmm) == 1) {
			return "01";
		} else if (Integer.parseInt(mmm) == 2) {
			return "02";
		} else if (Integer.parseInt(mmm) == 3) {
			return "03";
		} else if (Integer.parseInt(mmm) == 4) {
			return "04";
		} else if (Integer.parseInt(mmm) == 5) {
			return "05";
		} else if (Integer.parseInt(mmm) == 6) {
			return "06";
		} else if (Integer.parseInt(mmm) == 7) {
			return "07";
		} else if (Integer.parseInt(mmm) == 8) {
			return "08";
		} else if (Integer.parseInt(mmm) == 9) {
			return "09";
		} else if (Integer.parseInt(mmm) == 10) {
			return "10";
		} else if (Integer.parseInt(mmm) == 11) {
			return "11";
		} else if (Integer.parseInt(mmm) == 12) {
			return "12";
		}
		throw new ParseException("Invalid month=" + mmm + ", can not parse", 0);
	}
}
