package edu.wpi.cs3733.c22.teamC.Databases;

import edu.wpi.cs3733.c22.teamC.Databases.requests.filters.CriteriaUserSpecific;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.sql.SQLException;
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
    signedInUser =
        (new CriteriaUserSpecific(employeeIn))
            .meetCriteria((new EmployeeQuery().getAllNodeData()))
            .get(0);
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
      EmployeeQuery employeeQuery = new EmployeeQuery();
      employeeQuery.editNode(signedInUser);
    } catch (SQLException e) {
      System.out.println("Failed to set profile pic");
    }
  }
}
