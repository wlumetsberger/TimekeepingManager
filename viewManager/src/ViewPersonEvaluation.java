import at.wlumetsberger.viewManager.Person;
import at.wlumetsberger.viewManager.Race;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class ViewPersonEvaluation implements Initializable{
    @FXML
    private Pane myPane;

    @FXML
    private TextField position_gruppe_text;

    @FXML
    private TextField raceId_text;

    @FXML
    private TextField name_text;
    @FXML
    private TextField startnummer_text;
    @FXML
    private TextField position_text;
    @FXML
    private TextField runden_text;

    private Session session;
    private Race r;

    private void init(){
        try {
            r = WebServiceHelper.getInstance().getActiveRace();
            this.raceId_text.setText(r.getName());
        }catch(Exception ex){
            ex.printStackTrace();
            this.disconnectAction(null);
        }
    }

    public void initialize(final URL url, final ResourceBundle bundle) {
        this.init();
        this.connectToWebSocket();
    }
    private void connectToWebSocket(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try{
            URI uri = URI.create("ws://10.0.0.82:4711/");
            this.session = container.connectToServer(this,uri);
            session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    System.out.println("Received message: " + message);
                    JSONParser parser = new JSONParser();
                    try {
                        System.out.println(parser.parse(message));
                        JSONObject obj =(JSONObject) parser.parse(message);
                        if (obj.get("command").equals("singleScan")) {
                            Person p = WebServiceHelper.getInstance().getPerson(r.getId(), obj.get("tagId"));
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    name_text.setText(p.getName());
                                    startnummer_text.setText("" + p.getNumber());
                                    position_text.setText("" + WebServiceHelper.getInstance().getPosition(p));
                                    position_gruppe_text.setText("" + WebServiceHelper.getInstance().getPositionByGroup(p));
                                    runden_text.setText("" + WebServiceHelper.getInstance().getLapCount(p));
                                }
                            });

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("connected");
            JSONObject obj = new JSONObject();
            obj.put("command", "readSingle");
            obj.put("raceId",r.getId());
            this.session.getBasicRemote().sendText(obj.toJSONString());
        }catch(Exception e){
            e.printStackTrace();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                myPane.getChildren().clear();
                myPane.getChildren().add(loader.load());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    @FXML
    private void renewAction(ActionEvent event){
        JSONObject obj = new JSONObject();
        obj.put("command", "readSingle");
        obj.put("raceId",r.getId());
        try {
            this.session.getBasicRemote().sendText(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void disconnectAction(ActionEvent event){
        System.out.println("Beenden");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            myPane.getChildren().clear();
            myPane.getChildren().add(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            this.session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
