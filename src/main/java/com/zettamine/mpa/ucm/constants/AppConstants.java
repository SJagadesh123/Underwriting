package com.zettamine.mpa.ucm.constants;

public interface AppConstants {

	String STATUS_201 = "201";
	String MESSAGE_201 = "Created successfully";

	/*
	 * String STATUS_204 ="204"; String MESSAGE_204 ="Updated successfully";
	 */

	String STATUS_200 = "200";
	String MESSAGE_200 = "Response processed successfully";

	String STATUS_417 = "417";
	String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact DEV team";

	String ZIPCODE_REGEX = "^([0-9]{5})?$";
	String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	String COMPANY_NAME_REGEX = "^(?=.*[A-Za-z])[A-Za-z\\s&.,'-]+$", PHONE_REGEX = "^\\d{3}-\\d{3}-\\d{4}$";
	String PLACE_REGEX = "^[A-Za-z\\s.-]+$";
	String NAME_REGEX = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*$";
	String LICENCE_ID_REGEX = "^[A-Za-z0-9]{10}$";

	String PROVIDE_VALUE = "Provide value";
	String VALID_NAME = "Provide valid name";
	String VALID_ZIPCODE = "Provide valid zipcode";
	String VALID_PHONE = "Provide valid phone number";
	String VALID_EMAIL = "Provide valid email";
	String NOT_NULL = "Should not be null";
	
}
