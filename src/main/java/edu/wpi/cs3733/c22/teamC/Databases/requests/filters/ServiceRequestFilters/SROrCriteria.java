package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.List;

public class SROrCriteria implements SRCriteria {
  private SRCriteria criteria;
  private SRCriteria otherCriteria;

  public SROrCriteria(SRCriteria criteria, SRCriteria otherCriteria) {
    this.criteria = criteria;
    this.otherCriteria = otherCriteria;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> requests) {
    List<ServiceRequest> firstCriteriaItems = criteria.meetCriteria(requests);
    List<ServiceRequest> otherCriteriaItems = otherCriteria.meetCriteria(requests);

    for (ServiceRequest e : otherCriteriaItems) {
      if (!firstCriteriaItems.contains(e)) {
        firstCriteriaItems.add(e);
      }
    }
    return firstCriteriaItems;
  }
}
