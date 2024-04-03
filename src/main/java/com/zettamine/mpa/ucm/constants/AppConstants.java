package com.zettamine.mpa.ucm.constants;

public interface AppConstants {

	String STATUS_201 ="201";
	String MESSAGE_201 ="Created successfully";
	
	/*
	 * String STATUS_204 ="204"; String MESSAGE_204 ="Updated successfully";
	 */
	
	String STATUS_200 ="200";
	String MESSAGE_200 ="Response processed successfully";

	String STATUS_417 ="417";
	String MESSAGE_417_UPDATE ="Update operation failed. Please try again or contact DEV team";
	
	String ZIPCODE_REGEX = "^([0-9]{5})?$";
	String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

}
