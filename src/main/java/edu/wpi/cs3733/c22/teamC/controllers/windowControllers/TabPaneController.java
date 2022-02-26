package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import static javafx.scene.control.TabPane.TabDragPolicy.REORDER;

import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TabPaneController extends AbstractController {
  @FXML private AnchorPane anchorPane;
  @FXML private TabPane tabPane;

  @FXML
  public void initialize() throws IOException {
    tabPane.setTabDragPolicy(REORDER);

    Tab firstTab = createTab();
    tabPane.getTabs().add(firstTab);

    tabPane.getTabs().add(newTabButton());
  }

  private Tab newTabButton() {
    Tab addTab = new Tab("New Tab"); // You can replace the text with an icon
    addTab.setClosable(false);
    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldTab, newTab) -> {
              if (newTab == addTab) {
                Tab addedTab = null;
                try {
                  addedTab = createTab();
                } catch (IOException e) {
                  e.printStackTrace();
                }
                tabPane
                    .getTabs()
                    .add(
                        tabPane.getTabs().size() - 1,
                        addedTab); // Adding new tab before the "button" tab
                tabPane
                    .getSelectionModel()
                    .select(
                        tabPane.getTabs().size()
                            - 2); // Selecting the tab before the button, which is the newly created
                // one
              }
            });
    return addTab;
  }

  private Tab createTab() throws IOException {
    Tab tab = new Tab("Default Page");
    tab.setClosable(true);
    FXMLLoader newDefaultPageLoader = getLoader("DefaultPage.fxml");
    Pane newDefaultPage = newDefaultPageLoader.load();
    controllerMediator.addDefaultController(
        Integer.toString(tab.hashCode()), newDefaultPageLoader.getController());
    tab.setContent(newDefaultPage);
    tab.setOnCloseRequest(
        event -> {
          controllerMediator.removeDefaultController(Integer.toString(tab.hashCode()));
        });

    return tab;
  }

  public TabPane getTabPane() {
    return tabPane;
  }

  public AnchorPane getAnchorPane() {
    return anchorPane;
  }
}
