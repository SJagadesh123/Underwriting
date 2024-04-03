package com.zettamine.mpa.ucm.exception;

public class CompanyAlreadyExists extends RuntimeException {

	public CompanyAlreadyExists() {
		super();
	}

	public CompanyAlreadyExists(String message) {
		super(message);
	}

}
