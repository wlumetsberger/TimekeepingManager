import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {

    @FXML
    private Pane myPane;

    @FXML
    private void testButtonPressed(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewPersonEvaluation.fxml"));
        try {
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void connectButtonPressed(ActionEvent event){
        System.out.println("Connect to at.wlumetsberger.viewManager.Race");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewStatus.fxml"));
        try {
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void managmentButtonPressed(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("management.fxml"));
        try {
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
