import at.wlumetsberger.viewManager.Person;
import at.wlumetsberger.viewManager.Race;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Wolfgang on 30.07.2015.
 */
@ClientEndpoint
public class PersonController implements Initializable {

    @FXML
    private Pane myPane;

    @FXML
    TextField field_name;

    @FXML
    TextField field_club;

    @FXML
    TextField field_startnummer;

    @FXML
    TextField field_uhr;

    @FXML
    TextField field_gruppe;

    private Race activeRace;
    private Session session;

    private void connectToWebSocket(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        System.out.println("container: "+ container);
        try {
            URI uri = URI.create("ws://10.0.0.82:4711/");
            System.out.println("URI: "+ uri);
            this.session = container.connectToServer(this, uri);
            System.out.println("Session: "+ session);
            session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    System.out.println("Received message: " + message);
                    JSONParser parser = new JSONParser();
                    try {
                        System.out.println(parser.parse(message));
                        JSONObject obj = (JSONObject) parser.parse(message);
                        if (obj.get("command").equals("singleScan")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    field_uhr.setText((String) obj.get("tagId"));
                                }
                            });

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void initialize(final URL url, final ResourceBundle bundle) {
        this.connectToWebSocket();
        activeRace = WebServiceHelper.getInstance().getActiveRace();
    }
    @FXML
    private void navigateBack(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("management.fxml"));
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void scan(ActionEvent event){
        JSONObject obj = new JSONObject();
        obj.put("command", "readSingle");
        try {
            this.session.getBasicRemote().sendText(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveAction(ActionEvent event){
        Person p = new Person();
        p.setName(this.field_name.getText());
        p.setClub(this.field_club.getText());
        int startNummer = Integer.parseInt(this.field_startnummer.getText());
        p.setNumber(startNummer);
        p.setRace(activeRace);
        p.setTagId(this.field_uhr.getText());
        p.setClub(this.field_club.getText());

        if(WebServiceHelper.getInstance().addPerson(p)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Info");
            alert.setContentText("Teilnehmer wurde erfolgreich gespeichert");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Info");
            alert.setContentText("Teilnehmer wurde nicht gespeichert");
            alert.showAndWait();
        }

    }

}
