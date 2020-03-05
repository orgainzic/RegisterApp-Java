let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	const employeeIdElement = getEmployeeIdElement();

	getEmployeeIdElement().addEventListener("keypress", employeeIdKeypress);
		
	getSaveActionElement().addEventListener("click", saveActionClick);
	
	if (!employeeIdElement.disabled) {
		employeeIdElement.focus();
		employeeIdElement.select();
	}
});

function employeeIdKeypress(event) {
	if(event.which !== 13) {
		return;
	}
	saveActionClick();
}

// Save
function saveActionClick(event) {
	alert("Before if");
	if (!validateSave()) {
		return;
	}
	alert("After if");

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const employeeId = getEmployeeId();
	const employeeIdIsDefined = ((employeeId != null) && (employeeId.trim() !== ""));
	const saveActionUrl = "/api/employee";
		//+ (employeeIdIsDefined ? employeeId : ""));
	const saveEmployeeRequest = {
		id: employeeId,
		firstName: getFirstName(),
		lastName: getLastName(),
		title: getClassification(),
		password: getPassword(),
		passwordVerify: getPasswordVerify()
	};

	if (employeeIdIsDefined) {
		ajaxPatch(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();

				if ((callbackResponse.data != null)
					&& (callbackResponse.data.id != null)
					&& (callbackResponse.data.id.trim() !== "")) {


					setElementId(callbackResponse.data.id.trim());
				}
			}
		});
	} 
};

function validateSave(){

	const firstName = getFirstNameElement().value;
	if(firstName.length == 0) {
		displayError("Please provide a valid first name.")
		return false;
	}

	const lastName = getLastNameElement().value;
	if(lastName.length == 0) {
		displayError("Please provide a valid last name.");
		return false;
	}

	const password = getPassword();
	if((password == null) || (password.length < 10)) {
		displayError("Please enter a password with at least 10 characters.")
		return false;
	}

	const passwordVerify = getPasswordVerify();
	if(passwordVerify != password) {
		displayError("Passwords don't match.")
		return false;
	}

	const classification = getClassification();
	if(classification == null) {
		displayError("Please select a title.")
		return false
	}

	return true;
}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters
function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}

function getEmployeeId() {
	return getEmployeeIdElement().value;
}

function setEmployeeId() {
	getEmployeeIdElement().value = employeeId;
}

function getEmployeeIdElement() {
	return document.getElementById("employeeId");
}

function getFirstName() {
	return getFirstNameElement().value;
}

function setFirstName() {
	return getFirstNameElement().value = firstName;
}

function getFirstNameElement() {
	return document.getElementById("employeeFirstName");
}

function getLastName() {
	return getLastNameElement().value;
}

function setLastName() {
	return getLastNameElement().value = lastName;
}

function getLastNameElement() {
	return document.getElementById("employeeLastName");
}

function getPassword() {
	return getPasswordElement().value;
}

function setPassword() {
	return getPasswordElement().value = password;
}

function getPasswordElement() {
	return document.getElementById("employeePassword");
}

function getPasswordVerify() {
	return getPasswordVerifyElement.value;
}

function setPasswordVerify() {
	return getPasswordVerifyElement.value = passwordVerify;
}

function getPasswordVerifyElement(){
	return document.getElementById("employeePasswordVerify");
}

function getClassification() {
	return getClassificationElement().value;
}

function setClassification() {
	return getClassificationElement().value = classification;
}

function getClassificationElement() {
	return document.getElementById("employeeClassification");
}
//End getters and setters
