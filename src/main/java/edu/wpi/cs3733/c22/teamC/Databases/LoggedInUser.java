package edu.wpi.cs3733.c22.teamC.controllers.login;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.requests.filters.CriteriaUserSpecific;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;

public class LoggedInUser {
    private static Employee signedInUser;

    static{
        signedInUser = null;
    }

    public static void signInEmployee(String employeeIn){
        signOutEmployee();
        signedInUser = (new CriteriaUserSpecific(employeeIn)).meetCriteria((new EmployeeQuery().getAllNodeData())).get(0);
    }
    public static void signOutEmployee(){
        signedInUser = null;
    }
}
