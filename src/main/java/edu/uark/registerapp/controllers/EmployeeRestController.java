package edu.uark.registerapp.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;

@RestController
public class EmployeeRestController extends BaseRestController {
	@RequestMapping(value = "/api/employee", method = { RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody ApiResponse createEmployee(
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {
		System.out.println("STEP 1");
		boolean isInitialEmployee = false;
		ApiResponse canCreateEmployeeResponse;

		try {
			System.out.println("STEP 2");
			// TODO: Query if any active employees exist
			activeEmployeeExistsQuery.execute();

			System.out.println("STEP 3");
			canCreateEmployeeResponse =
				this.redirectUserNotElevated(request, response);
			System.out.println("STEP 4");

		} catch (final NotFoundException e) {
			System.out.println("STEP 5");
			isInitialEmployee = true;
			canCreateEmployeeResponse = new ApiResponse();
			System.out.println("STEP 6");
		}

		System.out.println("STEP 7");
		if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			System.out.println("STEP 8");
			return canCreateEmployeeResponse;
		}

		// TODO: Create an employee
		
		System.out.println("STEP 9");
		final Employee createdEmployee = employeeCreateCommand.setApiEmployee(employee).execute();

		System.out.println("STEP 10");
		if (isInitialEmployee){
			System.out.println("STEP 11");
			createdEmployee
				.setRedirectUrl(
					ViewNames.SIGN_IN.getRoute().concat(
						this.buildInitialQueryParameter(
							QueryParameterNames.EMPLOYEE_ID.getValue(),
							createdEmployee.getEmployeeId())));
			System.out.println(createdEmployee.getRedirectUrl());
		}else{
			System.out.println("STEP 12");
			return createdEmployee;
		}
		System.out.println("STEP 13");
		return createdEmployee.setIsInitialEmployee(isInitialEmployee);
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
	public @ResponseBody ApiResponse updateEmployee(
		@PathVariable final UUID employeeId,
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {
				/*
		try{
			validateActiveUserCommand.setSessionKey(request.getSession().getId()).execute();
		} catch (UnauthorizedException e) {
			response.setStatus(302);
			return new ApiResponse().setRedirectUrl(ViewNames.SIGN_IN.getRoute().concat(this.buildAdditionalQueryParameter(QueryParameterNames.ERROR_CODE.getValue(), "StopitGetsomehelp")));
		}
		*/

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(request, response);
		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		// TODO: Update the employee
		Employee createdEmployee = employeeUpdateCommand.setApiEmployee(employee).setRecordId(employee.getId()).execute();
		return createdEmployee;
	}

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
	@Autowired
	private EmployeeCreateCommand employeeCreateCommand;
	@Autowired
	private EmployeeUpdateCommand employeeUpdateCommand;
}
