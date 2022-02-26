package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SRLooseAndCriteria implements SRCriteria {
  private ArrayList<SRCriteria> criteria;

  public SRLooseAndCriteria(SRCriteria... criteria) {
    this.criteria = new ArrayList<>(Arrays.asList(criteria));
    //    System.out.println("THIS.Crit: " + this.criteria);
    //    System.out.println("Crit: " + criteria);
  }

  public SRLooseAndCriteria(ArrayList<SRCriteria> in) {
    criteria = in;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> requests) {

    List<ServiceRequest> criteriaToReturn = new ArrayList<ServiceRequest>(requests);

    for (int i = 0; i < criteria.size(); i++) {
      if (criteria.get(i) != null)
        criteriaToReturn = criteria.get(i).meetCriteria(criteriaToReturn);
    }
    return criteriaToReturn;
  }
}
