package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeUpdateCommand implements ResultCommandInterface<Employee> {

	@Override
	public Employee execute() {
        this.validateProperties();
        this.apiEmployee = this.updateEmployee();
        return this.apiEmployee;    
    }
    
    @Transactional
    public Employee updateEmployee()    {

        final Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(recordId);
        if (!employeeEntity.isPresent()) {
            throw new NotFoundException("Employee");
        }
        employeeEntity.get().synchronize(apiEmployee);
        employeeRepository.save(employeeEntity.get());
        return this.apiEmployee;    //return data of updated Employee object
    }

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.apiEmployee.getFirstName())) {
			throw new UnprocessableEntityException("first name");
        }
        
        if (StringUtils.isBlank(this.apiEmployee.getLastName())) {
			throw new UnprocessableEntityException("last name");
        }
	}

	// Properties
	private UUID recordId;
	public UUID getRecordId() {
		return this.recordId;
	}
	public EmployeeUpdateCommand setRecordId(final UUID recordId) {
		this.recordId = recordId;
		return this;
	}

	private Employee apiEmployee;
	public Employee getApiEmployee() {
		return this.apiEmployee;
	}
	public EmployeeUpdateCommand setApiEmployee(final Employee apiEmployee) {
		this.apiEmployee = apiEmployee;
		return this;
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;
}
