document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	
	var empIdIn = document.getElementById("empId").value;
	var pswIn = document.getElementById("psw").value;
	
	alert("employee id " + empIdIn);
	alert("password " + pswIn);
			
	if (empIdIn.length == 0)	{
		alert("Employee ID should not be blank");
	}
	
	if (typeof empIdIn == 'number')	{
		alert("Employee ID should be numeric");
	}
	
	if (pswIn.length == 0)	{
		alert("The password should not be blank");
	}
	
	else	{
		alert("Correct");
	}
	
	return true;
}
