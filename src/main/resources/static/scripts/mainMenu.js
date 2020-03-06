document.addEventListener("DOMContentLoaded", () => {
	const viewStartTransactionElement = getStartTransactionElement();
	const viewProductsElement = getProductsElement();
	const viewCreateEmployeeElement = getCreateEmployeeElement();
	const viewSalesReportElement = getSalesReportElement();
	const viewCashierReportElement = getCashierReportElement();
	
	
	
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
	ajaxDelete("/api/productListing", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/api/productListing");
		}
	});
}

function createEmployeeClick(event) {
	/*if(( ActiveUserEntity.classification == "Shift Manager" ) || ( ActiveUserEntity.classification == "General Manager" )) {
		$("div.elevatedAccess").show();
	}*/
	ajaxDelete("/api/employee", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/employee");
		}
	});
}

function salesReportClick(event) {
	if(( ActiveUserEntity.classification == "Shift Manager" ) || ( ActiveUserEntity.classification == "General Manager" )) {
		$("div.elevatedAccess").show();
	}
		displayError("Functionality has not yet been implemented.");
}

function cashierReportClick(event) {
	if(( ActiveUserEntity.classification == "Shift Manager" ) || ( ActiveUserEntity.classification == "General Manager" )) {
		$("div.elevatedAccess").show();
	}
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
