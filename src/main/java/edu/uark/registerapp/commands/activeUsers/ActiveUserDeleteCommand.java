package edu.uark.registerapp.commands.activeUsers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {
    @Override
    public void execute(){
        // Supposed to add functionality for validating Employee request object?
        // Not sure where I'm supposed to find this employee object or why it matters
        if(StringUtils.isBlank(employee.getFirstName()) || StringUtils.isBlank(employee.getLastName())){
            throw new UnprocessableEntityException("Name");
        }

        queryUserTable();  
    }

    @Transactional
    public void queryUserTable(){
    // Query and delete the active user
        final Optional<ActiveUserEntity> queriedActiveUser =
            this.activeUserRepository.findBySessionKey(this.currentSessionKey);
        
        if (queriedActiveUser.isPresent()){
            this.activeUserRepository.delete(queriedActiveUser.get());
        }
        else{
            throw new NotFoundException("ActiveUserEntity");
        }
    }
    // Properties
    private String currentSessionKey;
    public void setCurrentSessionKey(String key){
        this.currentSessionKey = key;
    }

    private Employee employee;
    public void setEmployee(Employee emp){
        this.employee = emp;
    }

    @Autowired
    private ActiveUserRepository activeUserRepository;
}