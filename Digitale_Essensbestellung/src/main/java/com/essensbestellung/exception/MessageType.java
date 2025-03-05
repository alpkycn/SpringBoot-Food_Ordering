package com.essensbestellung.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST("1001","Couldn't be found."),
	GENERAL_EXCEPTION("9999","An error occurred");
	
    private String code;
	
	private String message;
	
	MessageType(String code, String message) {
		this.code = code;
		this.message = message;
	}



}
