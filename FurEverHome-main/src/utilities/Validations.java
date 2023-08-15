package utilities;

import java.util.regex.Pattern;

public class Validations {
	
	/* Method will return true if the field is empty */
	public boolean isEmpty(String field) {
		if (field != null && !field.trim().equals(""))
			return false;
		return true;
	}
	
    public String validateEmail(String email) {
        final Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        String msg = null;
        if (isEmpty(email)) {
            msg = "Email cannot be empty";
        } else if( !pattern.matcher(email).matches()) {
            msg = "Invalid Email Id entered";
        } else {
        	return null;
        }
        return msg;
    }
    
    public String validatePassword(String password) {
        String msg = null;
        if (isEmpty(password)) {
            msg = "Password cannot be empty";
        } else if (password.length() < 4 ) {
            msg = "password should have atleat 4 characters";
        } else {
        	return null;
        }
        return msg;
    }
    
    public String validateConfirmPassword(String password, String confirmPassword) {
        String msg = null;
        if (isEmpty(confirmPassword)) {
            msg = "Confirm Password cannot be empty.";
        } else if (!password.trim().equals(confirmPassword.trim())) {
            msg = "Confirm Password and Password doesnot match";
        } else {
        	return null;
        }
        return msg;
    }
    
    public String validateName(String field, String fieldName) {
        String msg = null;
        final Pattern pattern = Pattern.compile("^[\sA-Za-z-\s]++$");
        if (isEmpty(field)) {
            msg = fieldName + " field cannot be empty";
        } else if (!pattern.matcher(field).matches()) {
            msg = fieldName + " field can contain only letters";
        } else {
        	return null;
        }
        return msg;
    }

}
