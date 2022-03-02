package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ServiceRequestDashboardController extends AbstractController {
  @FXML private JFXButton equipDelivButton;
  @FXML private JFXButton saniServButton;
  @FXML private JFXButton launServButton;
  @FXML private JFXButton medDelivButton;
  @FXML private JFXButton langInterpButton;
  @FXML private JFXButton maintReqButton;
  @FXML private JFXButton itButton;
  @FXML private JFXCheckBox showNamesCheckBox;
  @FXML private FlowPane flowPane;
  @FXML private JFXButton transportButton;
  @FXML private JFXButton giftButton;
  @FXML private JFXButton religiousButton;
  @FXML private JFXButton securityButton;
  @FXML private JFXButton APIButton;

  @FXML ImageView LaundryServiceButtonIcon;
  @FXML ImageView langInterpButtonIcon;
  @FXML ImageView ITHelpButtonIcon;
  @FXML ImageView sanitationButtonIcon;
  @FXML ImageView equipDelivButtonIcon;
  @FXML ImageView maintenanceButtonIcon;
  @FXML ImageView medDeliveryButtonIcon;
  @FXML ImageView internalTransportButtonIcon;
  @FXML ImageView giftButtonIcon;
  @FXML ImageView religiousButtonIcon;
  @FXML ImageView securityButtonIcon;
  @FXML ImageView APIButtonIcon;

  @FXML private TextField searchBox;
  private ArrayList<JFXButton> jfxButtonList = new ArrayList<JFXButton>();

  @FXML
  public void initialize() {

    LaundryServiceButtonIcon.setImage(ImageLoader.loadImage("Laundry"));
    langInterpButtonIcon.setImage(ImageLoader.loadImage("Language"));
    ITHelpButtonIcon.setImage(ImageLoader.loadImage("IT"));
    sanitationButtonIcon.setImage(ImageLoader.loadImage("Sanitation"));
    equipDelivButtonIcon.setImage(ImageLoader.loadImage("PUMPBLUE"));
    maintenanceButtonIcon.setImage(ImageLoader.loadImage("Maintenance"));
    medDeliveryButtonIcon.setImage(ImageLoader.loadImage("Meds"));
    internalTransportButtonIcon.setImage(ImageLoader.loadImage("Transport"));
    giftButtonIcon.setImage(ImageLoader.loadImage("Gifts"));
    religiousButtonIcon.setImage(ImageLoader.loadImage("Religion"));
    securityButtonIcon.setImage(ImageLoader.loadImage("Security"));
    APIButtonIcon.setImage(ImageLoader.loadImage("IT"));

    // BE CAREFUL, ADDING BUTTONS THAT DON'T EXIST ON SlideNavMenu.stafffxml WILL BREAK SEARCH
    jfxButtonList.addAll(
        FXCollections.observableArrayList(
            saniServButton,
            launServButton,
            medDelivButton,
            langInterpButton,
            maintReqButton,
            equipDelivButton,
            itButton,
            transportButton,
            giftButton,
            religiousButton,
            securityButton,
            APIButton));
  }

  @FXML
  private void searchBoxEntered() {
    jfxButtonList.forEach(
        jfxButton -> {
          if (!jfxButton.getText().toUpperCase().contains(searchBox.getText().toUpperCase())) {
            jfxButton.getParent().setVisible(false);
            jfxButton.getParent().setManaged(false);
            jfxButton.setVisible(false);
            jfxButton.setManaged(false);
          } else {
            jfxButton.getParent().setVisible(true);
            jfxButton.getParent().setManaged(true);
            jfxButton.setVisible(true);
            jfxButton.setManaged(true);
          }
        });
  }

  @FXML
  private void equipDelivReqButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("EquipDeliveryRequest.fxml");
  }

  @FXML
  private void laundryServButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("LaundryServices.fxml");
  }

  @FXML
  private void saniServButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("SanitationServices.fxml");
  }

  @FXML
  private void medDelivButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("MedicineDelivery.fxml");
  }

  @FXML
  private void maintenanceButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("Maintenance.fxml");
  }

  @FXML
  private void langInterpButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("LangInterpreter.fxml");
  }

  @FXML
  private void itButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("ITReq.fxml");
  }

  @FXML
  private void transportButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("InternalTransport.fxml");
  }

  @FXML
  private void giftButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("GiftRequest.fxml");
  }

  @FXML
  private void religiousButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("ReligiousRequest.fxml");
  }

  @FXML
  private void securityButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("SecurityRequest.fxml");
  }

  @FXML
  private void APIButtonPressed() throws IOException {
    controllerMediator.setDefaultPageCenter("APIRequest.fxml");
  }
}
