package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Arrays;
import java.util.Optional;

import javax.management.InvalidAttributeValueException;
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
        if(employeeRepository.existsByEmployeeId(Integer.parseInt(employeeSignIn.getEmployeeId()))){
            EmployeeEntity ee = employeeRepository.findByEmployeeId(Integer.parseInt(employeeSignIn.getEmployeeId())).get();
            System.out.println(ee.getPassword());
            System.out.println(EmployeeHelper.hashPassword(employeeSignIn.getPassword()));
            if(Arrays.equals(ee.getPassword(), EmployeeHelper.hashPassword(employeeSignIn.getPassword()))){

            }else{

                throw new NotFoundException("Password");
            }
        }else{
            throw new NotFoundException("Employee");
        }
    }

    @Transactional
    private void queryActiveUser(){
        System.out.println("TESTING 1");
        final Optional<ActiveUserEntity> queriedActiveUser =
            this.activeUserRepository.findByEmployeeId(
                UUID.fromString(EmployeeHelper.padEmployeeId(Integer.parseInt(employeeSignIn.getEmployeeId()))));
        System.out.println("TESTING 2");
        if (queriedActiveUser.isPresent()){
            System.out.println("TESTING 3");
            queriedActiveUser.get().setSessionKey(this.currentSessionKey);
            this.activeUserRepository.save(queriedActiveUser.get());
        }
        else{
            System.out.println("TESTING 4");
            ActiveUserEntity newActiveUser = new ActiveUserEntity();
            newActiveUser.setSessionKey(this.currentSessionKey);
            System.out.println("TESTING 5");
            newActiveUser.setEmployeeId(UUID.fromString(this.employeeSignIn.getEmployeeId()));
            System.out.println("TESTING 6");
            newActiveUser.setClassification(this.employeeRepository.findByEmployeeId(
                Integer.parseInt(this.employeeSignIn.getEmployeeId())
                ).get().getClassification());
            System.out.println("TESTING 7");
            newActiveUser.setName(this.employeeRepository.findByEmployeeId(
                Integer.parseInt(this.employeeSignIn.getEmployeeId())
                ).get().getLastName());
            System.out.println("TESTING 8");
            this.activeUserRepository.save(newActiveUser);
            System.out.println("TESTING 9");
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
