package edu.wpi.cs3733.c22.teamC.controllers.windowControllers;

import static javafx.scene.control.TabPane.TabDragPolicy.FIXED;
import static javafx.scene.control.TabPane.TabDragPolicy.REORDER;

import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TabPaneController extends AbstractController {
  @FXML private AnchorPane anchorPane;
  @FXML private TabPane tabPane;
  private Tab addTab = new Tab(); // You can replace the text with an icon

  @FXML
  public void initialize() throws IOException {
    addTab.setGraphic(new Label("New Tab"));
    addTab.setClosable(false); // prevent closing of the add tab

    tabPane.getTabs().add(createTab()); // add the first tab when page loads
    tabPane.getTabs().add(newTabButton()); // add the addTab for adding new tabs

    // prevents addTab from being dragged
    addTab
        .getGraphic()
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue) {
                tabPane.setTabDragPolicy(FIXED);
              } else {
                tabPane.setTabDragPolicy(REORDER);
              }
            });
  }

  private Tab newTabButton() {
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
    Tab tab = new Tab();
    tab.setGraphic(new Label("Default Page"));
    tab.setClosable(true);
    FXMLLoader newDefaultPageLoader = getLoader("DefaultPage.fxml");
    Pane newDefaultPage = newDefaultPageLoader.load();
    controllerMediator.addDefaultController(
        Integer.toString(tab.hashCode()), newDefaultPageLoader.getController());
    tab.setContent(newDefaultPage);

    // remove the tab's associated default controller from the hashmap which keeps track of it
    tab.setOnCloseRequest(
        event -> {
          controllerMediator.removeDefaultController(Integer.toString(tab.hashCode()));
        });

    tab.getGraphic()
        .hoverProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue && tabPane.getTabs().indexOf(tab) == tabPane.getTabs().size() - 1){

              }
                //tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTabButton());
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
