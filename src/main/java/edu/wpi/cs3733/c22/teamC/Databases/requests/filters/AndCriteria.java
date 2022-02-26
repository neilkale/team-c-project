package edu.wpi.cs3733.c22.teamC.Databases.requests.filters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.List;

/**
 * this class represents the filtering of employees based on two criteria, only common employees
 * from each of these criteria are included in the filtered list
 */
public class AndCriteria implements Criteria {

  private Criteria criteria;
  private Criteria otherCriteria;

  public AndCriteria(Criteria criteria, Criteria otherCriteria) {
    this.criteria = criteria;
    this.otherCriteria = otherCriteria;
  }

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {

    List<Employee> firstCriteriaPersons = criteria.meetCriteria(employees);
    return otherCriteria.meetCriteria(firstCriteriaPersons);
  }
}
