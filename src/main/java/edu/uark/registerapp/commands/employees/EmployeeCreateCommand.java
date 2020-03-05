package edu.uark.registerapp.commands.employees;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
	@Override
	public Employee execute() {
        this.validateProperties();
        if (this.checkIsInitialEmployee())  {
            this.apiEmployee.setClassification(701);
        }

        
        EmployeeEntity employeeEntity = new EmployeeEntity(apiEmployee);
        //employeeEntity.setPassword(EmployeeHelper.hashPassword(apiEmployee.getPassword()));
        
		// Synchronize information generated by the database upon INSERT.
        this.employeeRepository.save(employeeEntity);
		return this.apiEmployee;
	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.apiEmployee.getFirstName())) {
			throw new UnprocessableEntityException("first name");
        }
        
        if (StringUtils.isBlank(this.apiEmployee.getLastName())) {
			throw new UnprocessableEntityException("last name");
        }

        if (StringUtils.isBlank(this.apiEmployee.getPassword())) {
			throw new UnprocessableEntityException("password");
        }
        
	}

	// Properties
	private Employee apiEmployee;
	public Employee getApiEmployee() {
		return this.apiEmployee;
	}
	public EmployeeCreateCommand setApiEmployee(final Employee apiEmployee) {
		this.apiEmployee = apiEmployee;
		return this;
    }   
    
    private boolean isInitialEmployee;
    public boolean checkIsInitialEmployee()   {
        if (this.apiEmployee.getIsInitialEmployee() == true) {
            isInitialEmployee = true;
        }
        else    {
            isInitialEmployee = false;
        }
        return isInitialEmployee;
    }
    @Autowired
	private EmployeeRepository employeeRepository;
}
