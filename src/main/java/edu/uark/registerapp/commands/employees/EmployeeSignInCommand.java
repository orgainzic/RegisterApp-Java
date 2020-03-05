package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Arrays;
import java.util.Optional;
import javax.transaction.Transactional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class EmployeeSignInCommand implements VoidCommandInterface {
    @Override
    public void execute() {

        this.validateSignInProperties();

        this.validateSignInRequest();

        this.queryActiveUser();


    }

    // Helper methods
    private void validateSignInProperties(){
        if (StringUtils.isBlank(this.employeeSignIn.getEmployeeId())){
            throw new UnprocessableEntityException("employeeId");
        }
        else if (!(StringUtils.isNumeric(this.employeeSignIn.getEmployeeId()))){
            throw new UnprocessableEntityException("employeeId");
        }

        if (StringUtils.isBlank(this.employeeSignIn.getPassword())){
            throw new UnprocessableEntityException("password");
        }
    }
    
    private void validateSignInRequest(){
        if (this.employeeRepository.existsByEmployeeId(Integer.parseInt(
                this.employeeSignIn.getEmployeeId()))){

            if (true/*EmployeeHelper.hashPassword(this.employeeSignIn.getPassword()).equals(
                this.employeeRepository.findByEmployeeId(Integer.parseInt(
                    EmployeeHelper.padEmployeeId(Integer.parseInt(
                        this.employeeSignIn.getEmployeeId())))).get().getPassword())*/){
                            // do this if password matches
                            System.out.println("Good PASSWORD!");
                }
            else {
                System.out.println("Passwords didn't match");
                throw new ConflictException("password");
            }    
        }
        else {
            throw new NotFoundException("Employee");
        }
    }

    @Transactional
    private void queryActiveUser(){

        final Optional<ActiveUserEntity> queriedActiveUser =
            this.activeUserRepository.findByEmployeeId(
                UUID.fromString(EmployeeHelper.padEmployeeId(
                    Integer.parseInt(this.employeeSignIn.getEmployeeId()))));
        
        if (queriedActiveUser.isPresent()){
            queriedActiveUser.get().setSessionKey(this.currentSessionKey);
            this.activeUserRepository.save(queriedActiveUser.get());
        }
        else{
            ActiveUserEntity newActiveUser = new ActiveUserEntity();
            newActiveUser.setSessionKey(this.currentSessionKey);
            newActiveUser.setEmployeeId(UUID.fromString(this.employeeSignIn.getEmployeeId()));
            newActiveUser.setClassification(this.employeeRepository.findByEmployeeId(
                Integer.parseInt(EmployeeHelper.padEmployeeId(Integer.parseInt(this.employeeSignIn.getEmployeeId())))
                ).get().getClassification());
            newActiveUser.setName(this.employeeRepository.findByEmployeeId(
                Integer.parseInt(EmployeeHelper.padEmployeeId(Integer.parseInt(this.employeeSignIn.getEmployeeId())))
                ).get().getLastName());
            this.activeUserRepository.save(newActiveUser);
        }
        return;
    }

    // Properties
    private EmployeeSignIn employeeSignIn;
    public void setEmployeeSignIn(EmployeeSignIn ESI){
        this.employeeSignIn = ESI;
    }
    private String currentSessionKey;
    public void setCurrentSessionKey(String CSK){
        this.currentSessionKey = CSK;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ActiveUserRepository activeUserRepository;
}
