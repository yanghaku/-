package client;

import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class SocialPane extends Pane{
    private Main main;
    public SocialPane(Main main){
        this.main=main;
        this.getChildren().add(new Label("SocialInformation"));
    }
}
