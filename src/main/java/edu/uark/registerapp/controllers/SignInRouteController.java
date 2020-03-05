package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(@RequestParam final Map<String, String> queryParameters) {
		ModelAndView modelAndView = new ModelAndView();
		try{
			employeeQuery.execute();
			modelAndView.setViewName(ViewNames.SIGN_IN.getViewName());
		}catch(NotFoundException e){
			modelAndView.setViewName(ViewNames.EMPLOYEE_DETAIL.getViewName());
			modelAndView.addObject("employee", new Employee());
		}
		return modelAndView;
		
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		EmployeeSignIn employeeSignIn, HttpServletRequest request, @RequestParam Map<String, String> body
	) {
		//request.getSession().getId();
		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user
		
		//employeeSignIn.setEmployeeId(request.getParameter("enpId"));
		//employeeSignIn.setPassword(request.getParameter("enpPsw"));
		System.out.println(body);
		System.out.println("TESTEING: " + employeeSignIn.getEmployeeId());
		System.out.println("TESTING: " + employeeSignIn.getPassword());

		ModelAndView modelAndView = new ModelAndView();
		try{
			employeeSignInCommand.setCurrentSessionKey(request.getSession().getId());
			employeeSignInCommand.setEmployeeSignIn(employeeSignIn);
			employeeSignInCommand.execute();
		}catch(Exception e){
			modelAndView.setViewName(ViewNames.SIGN_IN.getViewName());
			modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), e.getMessage());
			return modelAndView;
		}

		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}

	@Autowired
	private ActiveEmployeeExistsQuery employeeQuery;
	@Autowired
	private EmployeeSignInCommand employeeSignInCommand;
}
