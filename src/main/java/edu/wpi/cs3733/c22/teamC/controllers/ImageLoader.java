package edu.wpi.cs3733.c22.teamC.controllers;

import edu.wpi.cs3733.c22.teamC.MainFinal;
import java.util.HashMap;
import javafx.scene.image.Image;

public class ImageLoader {
  private static HashMap<String, String> imageFileNames = new HashMap<>();
  private static HashMap<String, Image> images = new HashMap<>();

  public ImageLoader() {

    if (imageFileNames.size() != 0) {
      return;
    }

    imageFileNames.put("L2", "Maps/00_thelowerlevel2.png");
    images.put("L2", null);
    imageFileNames.put("L1", "Maps/00_thelowerlevel1.png");
    images.put("L1", null);
    imageFileNames.put("0", "Maps/00_thegroundfloor.png");
    images.put("0", null);
    imageFileNames.put("1", "Maps/01_thefirstfloor.png");
    images.put("1", null);
    imageFileNames.put("2", "Maps/02_thesecondfloor.png");
    images.put("2", null);
    imageFileNames.put("3", "Maps/03_thethirdfloor.png");
    images.put("3", null);

    imageFileNames.put("SideView", "Maps/theSideView.png");
    images.put("SideView", null);

    imageFileNames.put("Location", "Icons/MapLocationIcon.png");
    images.put("Location", null);
    imageFileNames.put("New", "Icons/MapSelectionIcon.png");
    images.put("New", null);

    imageFileNames.put("XRAY", "Icons/xRayGreen.png");
    images.put("XRAY", null);
    imageFileNames.put("BEDS", "Icons/BedGreen.png");
    images.put("BEDS", null);
    imageFileNames.put("RECL", "Icons/ReclinerGreen.png");
    images.put("RECL", null);
    imageFileNames.put("PUMP", "Icons/PumpGreen.png");
    images.put("PUMP", null);

    imageFileNames.put("XRAYDIRTY", "Icons/xRayRed.png");
    images.put("XRAYDIRTY", null);
    imageFileNames.put("BEDSDIRTY", "Icons/BedRed.png");
    images.put("BEDSDIRTY", null);
    imageFileNames.put("RECLDIRTY", "Icons/ReclinerRed.png");
    images.put("RECLDIRTY", null);
    imageFileNames.put("PUMPDIRTY", "Icons/PumpRed.png");
    images.put("PUMPDIRTY", null);

    imageFileNames.put("Service Request", "Icons/ServiceRequestIcon.png");
    images.put("Service Request", null);

    imageFileNames.put("XRAYBLUE", "Icons/XRayBlueIcon.png");
    images.put("XRAYBLUE", null);
    imageFileNames.put("BEDSBLUE", "Icons/BedBlueIcon.png");
    images.put("BEDSBLUE", null);
    imageFileNames.put("RECLBLUE", "Icons/ReclinerBlueIcon.png");
    images.put("RECLBLUE", null);
    imageFileNames.put("PUMPBLUE", "Icons/PumpBlueIcon.png");
    images.put("PUMPBLUE", null);

    imageFileNames.put("EquipDelivery", "Icons/EquipDeliveryIcon.png");
    images.put("EquipDelivery", null);

    imageFileNames.put("Gifts", "Icons/GiftsIcon.png");
    images.put("Gifts", null);

    imageFileNames.put("Transport", "Icons/TransportBlueIcon.png");
    images.put("Transport", null);

    imageFileNames.put("IT", "Icons/TechBlueIcon.png");
    images.put("IT", null);

    imageFileNames.put("Language", "Icons/InterpreterIcon.png");
    images.put("Language", null);

    imageFileNames.put("Laundry", "Icons/LaundryIcon.png");
    images.put("Laundry", null);

    imageFileNames.put("Maintenance", "Icons/MaintenanceIcon.png");
    images.put("Maintenance", null);

    imageFileNames.put("Meds", "Icons/MedsIcon.png");
    images.put("Meds", null);

    imageFileNames.put("Religion", "Icons/ReligionIcon.png");
    images.put("Religion", null);

    imageFileNames.put("Sanitation", "Icons/SanitationIcon.png");
    images.put("Sanitation", null);

    imageFileNames.put("Security", "Icons/SecurityIcon.png");
    images.put("Security", null);

    imageFileNames.put("End", "Icons/EndIcon.png");
    images.put("End", null);

    imageFileNames.put("Start", "Icons/StartIcon.png");
    images.put("Start", null);

    // Profile Pics
    imageFileNames.put("DefaultProfile", "ProfilePics/DefaultUserIcon.png");
    images.put("DefaultProfile", null);

    imageFileNames.put("Kevin", "ProfilePics/kevin.png");
    images.put("Kevin", null);

    imageFileNames.put("Bear", "ProfilePics/bear-pfp.png");
    images.put("Bear", null);

    imageFileNames.put("Bunny", "ProfilePics/bunny-pfp.png");
    images.put("Bunny", null);

    imageFileNames.put("Cat", "ProfilePics/cat-pfp.png");
    images.put("Cat", null);

    imageFileNames.put("Deer", "ProfilePics/deer-pfp.png");
    images.put("Deer", null);

    imageFileNames.put("Dog", "ProfilePics/dog-pfp.png");
    images.put("Dog", null);

    imageFileNames.put("Duck", "ProfilePics/duck-pfp.png");
    images.put("Duck", null);

    imageFileNames.put("Fox", "ProfilePics/fox-pfp.png");
    images.put("Fox", null);

    imageFileNames.put("Pig", "ProfilePics/pig-pfp.png");
    images.put("Pig", null);

    imageFileNames.put("Sheep", "ProfilePics/sheep-pfp.png");
    images.put("Sheep", null);

    imageFileNames.put("Skunk", "ProfilePics/skunk-pfp.png");
    images.put("Skunk", null);
  }

  public static Image loadImage(String name) {
    try {
      if (images.get(name) != null) return images.get(name);
      else {
        images.put(
            name,
            new Image(
                MainFinal.class
                    .getResource("/edu/wpi/cs3733.c22.teamC/Views/" + imageFileNames.get(name))
                    .toExternalForm()));
        return images.get(name);
      }
    } catch (Exception e) {
      System.out.println("Image load failed: " + imageFileNames.get(name));
    }
    return null;
  }
}
