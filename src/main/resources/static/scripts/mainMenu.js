document.addEventListener("DOMContentLoaded", () => {
	const viewStartTransactionElement = getStartTransactionElement();
	const viewProductsElement = getProductsElement();
	const viewCreateEmployeeElement = getCreateEmployeeElement();
	const viewSalesReportElement = getSalesReportElement();
	const viewCashierReportElement = getCashierReportElement();
	const elevatedOptions = toggleButtonsElement();
	
	var displaySetting = elevatedOptions.style.display;
	if(displaySetting == "block") {
		elevatedOptions.style.desplay = "none";
	}
	
	getStartTransactionElement().addEventListener("click", startTransactionClick);
	getProductsElement().addEventListener("click", productsClick);
	getCreateEmployeeElement().addEventListener("click", createEmployeeClick);
	getSalesReportElement().addEventListener("click", salesReportClick);
	getCashierReportElement().addEventListener("click", cashierReportClick);

});


function startTransactionClick(event) {
		displayError("Functionality has not yet been implemented.");
}

function productsClick(event) {
	ajaxDelete("/api/products", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/");
		}
	});
}

function createEmployeeClick(event) {
	ajaxDelete("/api/employee", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/");
		}
	});
}

function salesReportClick(event) {
		displayError("Functionality has not yet been implemented.");
}

function cashierReportClick(event) {
		displayError("Functionality has not yet been implemented.");
}
	
function signOutActionClickHandler() {
	ajaxDelete("/api/signOut", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/");
		}
	});
}
function toggleButtonsElement() {
	return document.getElementById("elevatedAccess");
}
function getStartTransactionElement() {
	return document.getElementById("startTransaction");
}
function getProductsElement() {
	return document.getElementById("viewProducts");
}
function getCreateEmployeeElement() {
	return document.getElementById("createEmployee");
}
function getSalesReportElement() {
	return document.getElementById("salesReport");
}
function getCashierReportElement() {
	return document.getElementById("cashierReport");
}
