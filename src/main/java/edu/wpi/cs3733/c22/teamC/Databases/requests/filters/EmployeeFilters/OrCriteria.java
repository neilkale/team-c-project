package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.EmployeeFilters;

import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import java.util.List;

/**
 * this class represents the filtering of employees based on two criteria, all employees from each
 * of these criteria are included in the filtered list
 */
public class OrCriteria implements Criteria {

  private Criteria criteria;
  private Criteria otherCriteria;

  public OrCriteria(Criteria criteria, Criteria otherCriteria) {
    this.criteria = criteria;
    this.otherCriteria = otherCriteria;
  }

  @Override
  public List<Employee> meetCriteria(List<Employee> employees) {
    List<Employee> firstCriteriaItems = criteria.meetCriteria(employees);
    List<Employee> otherCriteriaItems = otherCriteria.meetCriteria(employees);

    for (Employee e : otherCriteriaItems) {
      if (!firstCriteriaItems.contains(e)) {
        firstCriteriaItems.add(e);
      }
    }
    return firstCriteriaItems;
  }
}
