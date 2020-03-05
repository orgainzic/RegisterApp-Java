document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {

	let empId = document.signInForm.empId.value;
	let empPsw = document.signInForm.empPsw.value;

	if (empId.length == 0)	{
		displayError("Employee ID should not be blank");
		return false;
	}

	if (typeof empId == 'number')	{
		displayError("Employee ID should be numeric");
		return false;
	}

	if (empPsw.length == 0)	{
		displayError("The password should not be blank");
		return false;
	}
	return true;
}
