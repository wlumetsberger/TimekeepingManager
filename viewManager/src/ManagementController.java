import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;



/**
 * Created by Wolfgang on 30.07.2015.
 */
public class ManagementController {

    @FXML
    Pane myPane;

    @FXML
    private void addPersonAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("personView.fxml"));
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateBack(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
