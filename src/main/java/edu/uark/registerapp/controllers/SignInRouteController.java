package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(@RequestParam final Map<String, String> queryParameters) {
		try{
			employeeQuery.execute();
			ModelAndView modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());
		}catch(NotFoundException e){
			ModelAndView modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
		}
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		EmployeeSignIn employeeSignIn, HttpServletRequest request
	) {
		
		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user

		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}

	@Autowired
	private ActiveEmployeeExistsQuery employeeQuery;
}
