package edu.uark.registerapp.models.api;

public class EmployeeSignIn{

    private String employeeId;
    private String password;

    public EmployeeSignIn(){
        employeeId = "";
        password = "";
    }

    public EmployeeSignIn(String id, String password){
        this.employeeId = id;
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmployeeId(){
        return this.employeeId;
    }

    public void setEmployeeId(String id){
        this.employeeId = id;
    }

    public void setPassword(String pass){
        this.password = pass;
    }
}