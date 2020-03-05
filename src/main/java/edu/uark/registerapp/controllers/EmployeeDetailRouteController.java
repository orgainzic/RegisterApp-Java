package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.joran.conditional.ElseAction;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.commands.employees.EmployeeQuery;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		
		validateActiveUserCommand.setSessionKey(request.getSession().getId());
		if (!activeEmployeeExists() || this.isElevatedUser(this.getCurrentUser(request).get()))	{
			return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
		}
		else if(!validateActiveUser()) {
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute())).addObject(ViewModelNames.ERROR_MESSAGE.getValue(), "No active users");
		}
		else {
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute())).addObject(ViewModelNames.ERROR_MESSAGE.getValue(), "No active users");
		}
		//return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
		}
		else {
			employeeQuery.setEmployeeId(employeeId);
			Employee employee = employeeQuery.execute();
			if(this.isElevatedUser(activeUserEntity.get())){
				return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()).addObject(ViewModelNames.IS_ELEVATED_USER.getValue(), employee);
			}else{
				return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()).addObject(ViewModelNames.NOT_DEFINED.getValue(), employee);

			}
		}

		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
	}

	// Helper methods
	private boolean activeEmployeeExists() {
		try {
			activeEmployeeExistsQuery.execute();
		}
		catch (NotFoundException e) {
			return false;
		}
		return true;
	}

	private boolean validateActiveUser()	{
		try	{
			validateActiveUserCommand.execute();
		}
		catch (NotFoundException e)	{
			return false;
		}
		return true;
	}

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
	@Autowired
	private ValidateActiveUserCommand validateActiveUserCommand;
	@Autowired
	private EmployeeQuery employeeQuery;

}
