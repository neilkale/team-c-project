package edu.wpi.cs3733.c22.teamC.Databases;

import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.DaoSingleton;
import edu.wpi.cs3733.c22.teamC.Databases.DaoPattern.EmployeeDaoImpl;
import edu.wpi.cs3733.c22.teamC.Databases.requests.filters.EmployeeFilters.CriteriaUserSpecific;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import javafx.scene.image.Image;

public class LoggedInUser {
  private static Employee signedInUser;
  private static Image profilePic;

  static {
    signedInUser = null;
    profilePic = ImageLoader.loadImage("DefaultProfile");
  }

  public static void signInEmployee(String employeeIn) {
    signOutEmployee();
    EmployeeDaoImpl e = DaoSingleton.getEmployeeDao();
    signedInUser = (new CriteriaUserSpecific(employeeIn)).meetCriteria((e.getAllNodes())).get(0);
  }

  public static void signOutEmployee() {
    signedInUser = null;
    profilePic = ImageLoader.loadImage("DefaultProfile");
  }

  public static Employee getCurrentUser() {
    return signedInUser;
  }

  public static Image getProfilePic() {
    if (signedInUser.get_profilePicture().equals("null")
        || ImageLoader.loadImage(signedInUser.get_profilePicture()) == null)
      return ImageLoader.loadImage("DefaultProfile");
    return ImageLoader.loadImage(signedInUser.get_profilePicture());
  }

  public static void setProfilePic(String newPicName) {
    profilePic = ImageLoader.loadImage(newPicName);
    signedInUser.set_profilePicture(newPicName);
    // todo: set the new profile pic in DB
    try {
      DaoSingleton.getEmployeeDao().updateNode(signedInUser);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to set profile pic");
    }
  }
}
