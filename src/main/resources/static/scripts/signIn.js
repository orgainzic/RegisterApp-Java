document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {

	let empId = document.signInForm.empId.value;
	let empPsw = document.signInForm.empPsw.value;

	if (empId.length == 0)	{
		alert("Employee ID should not be blank");
		return false;
	}

	if (typeof empId == 'number')	{
		alert("Employee ID should be numeric");
		return false;
	}

	if (empPsw.length == 0)	{
		alert("The password should not be blank");
		return false;
	}

	return true;
}
