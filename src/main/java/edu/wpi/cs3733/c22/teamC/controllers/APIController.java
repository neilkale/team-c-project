package edu.wpi.cs3733.c22.teamC.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamC.Databases.Employee;
import edu.wpi.cs3733.c22.teamC.Databases.Location;
import edu.wpi.cs3733.c22.teamC.Databases.requests.InternalTransportRequest;
import edu.wpi.cs3733.c22.teamC.Databases.requests.ServiceRequest;
import edu.wpi.cs3733.c22.teamC.SQLMethods.EmployeeQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.LocationQuery;
import edu.wpi.cs3733.c22.teamC.SQLMethods.requests.InternalTransportRequestQuery;
import edu.wpi.cs3733.c22.teamC.controllers.AbstractController;
import edu.wpi.cs3733.c22.teamC.controllers.ControllerUtil;
import edu.wpi.cs3733.c22.teamC.controllers.DatabaseUtil;
import edu.wpi.cs3733.c22.teamC.controllers.ImageLoader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class APIController extends AbstractController {
    @FXML private JFXButton backButton;
    @FXML private JFXComboBox pickUpLocationComboBox;
    @FXML private JFXComboBox dropOffLocationComboBox;
    @FXML private JFXComboBox statusComboBox;
    @FXML private JFXComboBox assignmentComboBox;
    @FXML private JFXComboBox urgencyComboBox;
    @FXML private ImageView imageView;


    @FXML
    private void initialize() {
    }

    @FXML
    private void startTeamBAPI() throws IOException {

    }

    @FXML
    private void startTeamDAPI () throws IOException {

    }
}
