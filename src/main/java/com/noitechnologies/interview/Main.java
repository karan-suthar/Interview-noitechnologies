package com.noitechnologies.interview;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.noitechnologies.interview.dao.DaoClass;
import com.noitechnologies.interview.model.Address;
import com.noitechnologies.interview.model.MultiKeyMap;
import com.noitechnologies.interview.model.User;

public class Main {

	// constants
	private static final String DELIMITER = ",";
	private static final String FILE_PATH = "";
	private static final MultiKeyMap<String, Long, User> USERS = new MultiKeyMap<>();
	private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String MOBILE_NUMBER_REGEX = "[9876]\\d{9}";
	private static final String NAME_REGX = "[a-zA-Z]{2,15}$";
	private static final String AGE_REGX = "\\d{2}";
	private static final String ADDRESS_REGX = "[a-zA-Z0-9, ]{5,50}$";
	private static final String CITY_REGX = "[a-zA-Z0-9]{2,25}$";
	private static final String PINCODE_REGX = "\\d{6}";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public static void main(String[] args) throws IOException {
		try (BufferedReader csvReader = new BufferedReader(new FileReader(FILE_PATH))) {
			String row = "";
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(DELIMITER);
				if (data.length == 12 && validateData(data)) {
					User user = createUser(data);
					USERS.put(user.getEmail(), user.getMobileNumber(), user);
				}
			}

			if (DaoClass.insertUserDataIntoDB(USERS) && DaoClass.insertAddressDataIntoDB(USERS))
				System.out.println("Success");
			else
				System.out.println("Error Occured");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found");
		}

	}

	// create user object from string array
	private static User createUser(String[] data) {
		User user = new User();
		user.setFirstName(data[0]);
		user.setLastName(data[1]);
		user.setAge(Integer.parseInt(data[2]));
		user.setDob(LocalDate.parse(data[3].replace('-', '/'), FORMATTER));
		user.setEmail(data[4]);
		user.setMobileNumber(Long.parseLong(data[5]));
		Address address = new Address();
		address.setAddressLine1(data[6]);
		address.setAddressLine2(data[7]);
		address.setCity(data[8]);
		address.setState(data[9]);
		address.setCountry(data[10]);
		address.setPinCode(Integer.parseInt(data[11]));
		user.setAddress(address);
		return user;
	}

	// return true if all constraints are satisfied
	private static boolean validateData(String[] data) {

		if (chekNullAndSize(data[0]) || checkData(data[0], NAME_REGX))
			return false;
		if (chekNullAndSize(data[1]) || checkData(data[1], NAME_REGX))
			return false;
		if (chekNullAndSize(data[2]) || checkData(data[2], AGE_REGX) || !checkAge(data[2]))
			return false;
		if (chekNullAndSize(data[3]) || !checkDate(data[3]))
			return false;
		if (chekNullAndSize(data[4]) || checkData(data[4], EMAIL_REGEX))
			return false;
		if (chekNullAndSize(data[5]) || checkData(data[5], MOBILE_NUMBER_REGEX))
			return false;
		if (chekNullAndSize(data[6]) || checkData(data[6], ADDRESS_REGX))
			return false;
		if (chekNullAndSize(data[7]) || checkData(data[7], ADDRESS_REGX))
			return false;
		if (chekNullAndSize(data[8]) || checkData(data[8], CITY_REGX))
			return false;
		if (chekNullAndSize(data[9]) || checkData(data[9], CITY_REGX))
			return false;
		if (chekNullAndSize(data[10]) || checkData(data[10], CITY_REGX))
			return false;
		if (chekNullAndSize(data[11]) || checkData(data[11], PINCODE_REGX))
			return false;

		return true;

	}

	private static boolean chekNullAndSize(String data) {
		return data == null || data.trim().length() == 0;
	}

	private static boolean checkData(String data, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(data);
		return !m.matches();
	}

	private static boolean checkDate(String date) {
		try {
			LocalDate.parse(date.replace('-', '/'), FORMATTER);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	private static boolean checkAge(String age) {
		try {
			int temp = Integer.parseInt(age);
			return temp >= 18 && temp <= 35;
		} catch (NumberFormatException e) {
			return false;
		}

	}

}
