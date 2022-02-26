package edu.wpi.cs3733.c22.teamC.Databases.requests.filters.ServiceRequestFilters;

import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import java.util.List;

public class SRAndCriteria implements SRCriteria {

  private SRCriteria criteria;
  private SRCriteria otherCriteria;

  public SRAndCriteria(SRCriteria criteria, SRCriteria otherCriteria) {
    this.criteria = criteria;
    this.otherCriteria = otherCriteria;
  }

  @Override
  public List<ServiceRequest> meetCriteria(List<ServiceRequest> requests) {

    List<ServiceRequest> firstCriteria = criteria.meetCriteria(requests);
    return otherCriteria.meetCriteria(firstCriteria);
  }
}
