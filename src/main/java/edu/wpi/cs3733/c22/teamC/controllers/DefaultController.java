package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c22.teamC.Databases.LoggedInUser;
import edu.wpi.cs3733.c22.teamC.controllers.windowControllers.SlideNavMenuController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class DefaultController extends AbstractController {
  @FXML private AnchorPane anchorPane;
  @FXML private BorderPane borderPane;
  @FXML private Pane pane;
  @FXML private JFXDrawersStack drawerStack;
  @FXML private JFXButton navButton;
  @FXML private JFXButton exitButton;
  @FXML private JFXButton infoButton;
  @FXML private Circle profileCircle;
  @FXML private Label nameLabel;
  @FXML private JFXDrawer profileDrawer;

  private JFXDrawer navDrawer = new JFXDrawer();
  private JFXDrawer exitDrawer = new JFXDrawer();
  private JFXDrawer infoDrawer = new JFXDrawer();
  private ArrayList<JFXDrawer> drawerList = new ArrayList<JFXDrawer>();
  private Pane slideNavMenu;
  private Pane slideExitMenu;
  private Pane slideInfoMenu;

  private SlideNavMenuController slideNavMenuController;
  private ArrayList<String> prevPageList = new ArrayList<String>();

  @FXML
  public void initialize() throws IOException {
    profileCircle.setFill(new ImagePattern(LoggedInUser.getProfilePic()));
    nameLabel.setText(
        LoggedInUser.getCurrentUser().get_firstName()
            + " "
            + LoggedInUser.getCurrentUser().get_lastName());

    FXMLLoader slideNavLoader = getLoader("SlideNavMenu.fxml");
    slideNavMenu = slideNavLoader.load();
    slideNavMenuController = slideNavLoader.getController();

    slideExitMenu = (Pane) loadFxml("SlideExitMenu.fxml");
    slideInfoMenu = (Pane) loadFxml("SlideInfoMenu.fxml");

    drawerList.add(navDrawer);
    drawerList.add(exitDrawer);
    drawerList.add(infoDrawer);
    drawerStack.setContent(pane);

    AnchorPane.setTopAnchor(drawerStack, 0.0);
    AnchorPane.setBottomAnchor(drawerStack, 0.0);

    drawerStack
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Boolean drawerIsOpened = false;
              for (JFXDrawer drawer : drawerList) {
                if (drawer.isOpened()) drawerIsOpened = true;
              }
              if (newValue && !drawerIsOpened) drawerStack.toBack();
            });

    FXMLLoader profileLoader = getLoader("ProfilePage.fxml");
    Pane profilePane = profileLoader.load();
    profileDrawer.setSidePane(profilePane);

    profileDrawer.setOnMouseExited(
        evt -> {
          profileDrawer.close();
          profileCircle.setFill(new ImagePattern(LoggedInUser.getProfilePic()));
        });

    profileDrawer.close();

    ControllerUtil.addDrawerButtonHover(
        navButton, navDrawer, slideNavMenu, drawerList, drawerStack);
    ControllerUtil.addDrawerButtonHover(
        exitButton, exitDrawer, slideExitMenu, drawerList, drawerStack);
    ControllerUtil.addDrawerButtonHover(
        infoButton, infoDrawer, slideInfoMenu, drawerList, drawerStack);
  }

  public Label getNameLabel() {
    return nameLabel;
  }

  public ArrayList<String> getPrevPageList() {
    return prevPageList;
  }

  // sets the pane from the given fxml to the center of the given borderpane
  // fxml filename has to be file name of a PANE
  @FXML
  public void setCenter(String fxmlFileName) throws IOException {
    Pane root = (Pane) loadFxml(fxmlFileName);

    root.prefWidthProperty().bind(borderPane.widthProperty());
    root.prefHeightProperty().bind(borderPane.heightProperty());

    borderPane.setCenter(root);
    // borderPane.getLeft().toFront();

    AnchorPane.setTopAnchor(root, 0.0);
    AnchorPane.setBottomAnchor(root, 0.0);
    AnchorPane.setLeftAnchor(root, 0.0);
    AnchorPane.setRightAnchor(root, 0.0);

    String tabName = splitCamelCase(fxmlFileName.replace(".fxml", ""));
    controllerMediator.setTabName(tabName); // set tab text to tabName

    if (prevPageList.isEmpty() || prevPageList.get(prevPageList.size() - 1) != fxmlFileName)
      prevPageList.add(fxmlFileName); // add this page to tracking list of previous pages
  }
  // overloaded constructor
  // doesn't implement renaming of tab
  @FXML
  public void setCenter(Pane pane, String fxmlFileName) throws IOException {
    Pane root = pane;

    root.prefWidthProperty().bind(borderPane.widthProperty());
    root.prefHeightProperty().bind(borderPane.heightProperty());

    borderPane.setCenter(root);

    AnchorPane.setTopAnchor(root, 0.0);
    AnchorPane.setBottomAnchor(root, 0.0);
    AnchorPane.setLeftAnchor(root, 0.0);
    AnchorPane.setRightAnchor(root, 0.0);

    String tabName = splitCamelCase(fxmlFileName.replace(".fxml", ""));
    controllerMediator.setTabName(tabName); // set tab text to tabName

    if (prevPageList.isEmpty() || prevPageList.get(prevPageList.size() - 1) != fxmlFileName)
      prevPageList.add(fxmlFileName); // add this page to tracking list of previous pages
  }

  @FXML
  public void backButtonPressed() throws IOException {
    prevPageList.remove(prevPageList.size() - 1); // remove the current page from the tracking list

    if (prevPageList.isEmpty()) { // base case
      Pane emptyPane = new Pane();
      emptyPane.setVisible(false);
      setCenter(emptyPane, "DefaultPage.fxml");
    } else { // we get last page of list and go to that
      String prevPage = prevPageList.get(prevPageList.size() - 1);
      setCenter(prevPage);

      String tabName = splitCamelCase(prevPage.replace(".fxml", ""));
      controllerMediator.setTabName(tabName);
    }
  }

  @FXML
  private void orderTrackerButtonPressed() throws IOException {
    setCenter("OrderTracker.fxml");
  }

  @FXML
  private void mapEditorButtonPressed() throws IOException {
    setCenter("MapEditor.fxml");
  }

  @FXML
  private void sideViewButtonPressed() throws IOException {
    setCenter("SideView.fxml");
  }

  @FXML
  private void databasesButtonPressed() throws IOException {
    setCenter("DatabasesAll.fxml");
  }

  @FXML
  private void dashboardButtonPressed() throws IOException {
    setCenter("Dashboard.fxml");
  }

  // reformats camel cased strings, such as fxml file names; OOOH LOOK, REGEX!!! PURDY.
  private String splitCamelCase(String s) {
    return s.replaceAll(
        String.format(
            "%s|%s|%s",
            "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"),
        " ");
  }

  @FXML
  void profileButtonPressed() throws IOException {
    profileDrawer.open();
    profileDrawer.toFront();
  }
}
