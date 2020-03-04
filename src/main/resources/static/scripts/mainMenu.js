document.addEventListener("DOMContentLoaded", () => {
	const viewStartTransactionElement = getStartTransactionElement();
	const viewProductsElement = getViewProductsElement();
	const viewCreateEmployeeElement = getCreateEmployeeElement();
	const viewSalesReportElement = getSalesReportElement();
	const viewCashierReportElement = getCashierReportElement();
	const elevatedOptions = toggleButtonsElement();
	
	var displaySetting = elevatedOptions.style.display;
	if(displaySetting == "block") {
		elevatedOptions.style.desplay = "none";
	}
	
	viewStartTransactionElement().addEventListener("click", displayError(errorMessage));
	viewProductsElement().addEventListener("click", productsElementActionClickHandler);
	viewCreateEmployeeElement().addEventListener("click", createEmployeeElementActionClickHandler);
	viewSalesReportElement().addEventListener("click", displayError(errorMessage));
	viewCashierReportElement().addEventListener("click", displayError(errorMessage));

});	

// TODO:: Adding proper routing tonight during 2-8
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
function getViewProductsElement() {
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
