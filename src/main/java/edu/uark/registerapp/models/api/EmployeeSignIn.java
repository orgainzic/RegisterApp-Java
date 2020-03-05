package edu.uark.registerapp.models.api;

public class EmployeeSignIn{

    private String empId;
    private String empPsw;

    public EmployeeSignIn(){
        empId= "";
        empPsw = "";
    }

    public EmployeeSignIn(String empId, String empPsw){
        this.empId = empId;
        this.empPsw = empPsw;
    }

    public String getPassword(){
        return this.empPsw;
    }

    public String getEmployeeId(){
        return this.empId;
    }

    public void setEmployeeId(String id){
        this.empId = id;
    }

    public void setPassword(String pass){
        this.empPsw = pass;
    }
}