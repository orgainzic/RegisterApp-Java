package edu.uark.registerapp.commands.employees.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class EmployeeHelper {
	public static String padEmployeeId(final int employeeId) {
		final String employeeIdAsString = Integer.toString(employeeId);

		return ((employeeIdAsString.length() < EMPLOYEE_ID_MAXIMUM_LENGTH)
				? StringUtils.leftPad(employeeIdAsString, EMPLOYEE_ID_MAXIMUM_LENGTH, "0")
				: employeeIdAsString);
	}

	public static byte[] hashPassword(final String password) {
		// Hash password and return the bytes
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hashedPassword = messageDigest.digest(password.getBytes("UTF-8"));
			return hashedPassword;
		} catch (UnsupportedEncodingException e) {
			// UnsupportedEncodingException handled here
		} catch (NoSuchAlgorithmException e) {
			// NoSuchAlgorithmException handled here
		}
		return new byte[0];
	}

	private static final int EMPLOYEE_ID_MAXIMUM_LENGTH = 5;
}
