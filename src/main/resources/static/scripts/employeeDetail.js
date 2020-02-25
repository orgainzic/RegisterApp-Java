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
	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const employeeId = getEmployeeId();
	const employeeIdIsDefined = ((employeeId != null) && (employeeId.trim() !== ""));
	const saveActionUrl = ("/api/employee/"
		+ (employeeIdIsDefined ? employeeId : ""));
	const saveEmployeeRequest = {
		id: employeeId,
		firstName: getFirstName(),
		lastName: getLastName(),
		title: getTitle(),
		password: getPassword(),
	};

	if (employeeIdIsDefined) {
		ajaxPut(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
			}
		});
	} 
};

function validateSave(){
	const lastname = getLastName();
	if(lastName == null) {
		displayError("Please provide a valid last name.");
		return false;
	}

	const firstName = getFirstName();
	if(firstName == null) {
		displayError("Please provide a valid first name.")
		return false;
	}

	const password = getPassword();
	if((password == null) || (password.length < 10)) {
		displayError("Please enter a password with at least 10 characters.")
		return false;
	}

	const title = getTitle();
	if(title == null) {
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
	return document.getElementById("firstName");
}


function getLastName() {
	return getLastNameElement().value;
}

function setLastName() {
	return getLastNameElement().value = lastName;
}

function getLastNameElement() {
	return document.getElementById("lastName");
}

function getPassword() {
	return getPasswordElement().value;
}

function setPassword() {
	return getPasswordElement().value = password;
}

function getPasswordElement() {
	return document.getElementById("password");
}

function getTitle() {
	return getTitleElement().value;
}

function setTitle() {
	return getTitleElement().value = title;
}

function getTitleElement() {
	return document.getElementById("title");
}
//End getters and setters
