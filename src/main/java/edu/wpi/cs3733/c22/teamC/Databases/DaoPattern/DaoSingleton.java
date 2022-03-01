package edu.wpi.cs3733.c22.teamC.Databases.DaoPattern;

public class DaoSingleton {
  private static EmployeeDaoImpl employeeDao;
  private static EquipmentRequestDaoImpl equipmentRequestDao;
  private static InternalTransportRequestDaoImpl internalTransportRequestDao;
  private static ITRequestDaoImpl itRequestDao;
  private static LanguageRequestDaoImpl languageRequestDao;
  private static LaundryRequestDaoImpl laundryRequestDao;
  private static LocationDaoImpl locationDao;
  private static MaintenanceRequestDaoImpl maintenanceRequestDao;
  private static MedicineRequestDaoImpl medicineRequestDao;
  private static ReligiousRequestDaoImpl religiousRequestDao;
  private static SanitationRequestDaoImpl sanitationRequestDao;
  private static SecurityRequestDaoImpl securityRequestDao;
  private static EquipmentDaoImpl equipmentDao;
  private static MapDaoImpl mapDao;
  private static GiftRequestDaoImpl giftRequestDao;

  public static void makeDaoSingleton() {
    employeeDao = new EmployeeDaoImpl();
    equipmentDao = new EquipmentDaoImpl();
    equipmentRequestDao = new EquipmentRequestDaoImpl();
    internalTransportRequestDao = new InternalTransportRequestDaoImpl();
    itRequestDao = new ITRequestDaoImpl();
    languageRequestDao = new LanguageRequestDaoImpl();
    laundryRequestDao = new LaundryRequestDaoImpl();
    locationDao = new LocationDaoImpl();
    maintenanceRequestDao = new MaintenanceRequestDaoImpl();
    medicineRequestDao = new MedicineRequestDaoImpl();
    religiousRequestDao = new ReligiousRequestDaoImpl();
    sanitationRequestDao = new SanitationRequestDaoImpl();
    securityRequestDao = new SecurityRequestDaoImpl();
    giftRequestDao = new GiftRequestDaoImpl();
    mapDao = new MapDaoImpl();
  }

  public static EmployeeDaoImpl getEmployeeDao() {
    return employeeDao;
  }

  public static EquipmentRequestDaoImpl getEquipmentRequestDao() {
    return equipmentRequestDao;
  }

  public static InternalTransportRequestDaoImpl getInternalTransportRequestDao() {
    return internalTransportRequestDao;
  }

  public static ITRequestDaoImpl getItRequestDao() {
    return itRequestDao;
  }

  public static LanguageRequestDaoImpl getLanguageRequestDao() {
    return languageRequestDao;
  }

  public static LaundryRequestDaoImpl getLaundryRequestDao() {
    return laundryRequestDao;
  }

  public static LocationDaoImpl getLocationDao() {
    return locationDao;
  }

  public static MaintenanceRequestDaoImpl getMaintenanceRequestDao() {
    return maintenanceRequestDao;
  }

  public static MedicineRequestDaoImpl getMedicineRequestDao() {
    return medicineRequestDao;
  }

  public static ReligiousRequestDaoImpl getReligiousRequestDao() {
    return religiousRequestDao;
  }

  public static SanitationRequestDaoImpl getSanitationRequestDao() {
    return sanitationRequestDao;
  }

  public static SecurityRequestDaoImpl getSecurityRequestDao() {
    return securityRequestDao;
  }

  public static EquipmentDaoImpl getEquipmentDao() {
    return equipmentDao;
  }

  public static MapDaoImpl getMapDao() {
    return mapDao;
  }

  public static GiftRequestDaoImpl getGiftRequestDao() {
    return giftRequestDao;
  }
}
