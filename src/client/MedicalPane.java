package client;

import javafx.scene.control.Label;
import javafx.scene.layout.*;


public class MedicalPane extends Pane{
    private Main main;
    public MedicalPane(Main main){
        this.main=main;
        this.getChildren().add(new Label("Medical!"));
    }
}