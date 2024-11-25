package com.mango.customer.domain;

public class ValidationConstants {
	public static final int NAME_MIN_LENGTH = 2;
	public static final int NAME_MAX_LENGTH = 100;
	public static final int LASTNAME_MIN_LENGTH = 2;
	public static final int LASTNAME_MAX_LENGTH = 50;
	public static final int ADDRESS_MAX_LENGTH = 100;
	public static final int CITY_MIN_LENGTH = 2;
	public static final int CITY_MAX_LENGTH = 50;
	public static final String ONLY_ALPHABET_CHARACTERS = "^[a-zA-Z]+( [a-zA-Z]+)*$";
	public static final String ALPHANUMERIC_WITH_PUNCTUATION_REGEX = "^[a-zA-Z0-9\\s,.-]*$";

	public static final int MAX_USER_SLOGANS = 3;
}
