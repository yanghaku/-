package client;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author yangbo
 *
 * 警告框
 *
 */

public class AlertDialog {
    public static void display(String title,String message){
        Stage window=new Stage();
        window.initStyle(StageStyle.UTILITY);
        VBox pane=new VBox();
        Button btOk=new Button("Ok");
        Label label=new Label(message);
        window.setTitle(title);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(label,btOk);
        btOk.setOnAction(e->window.close());
        window.setResizable(false);
        StackPane s=new StackPane();
        s.getChildren().add(pane);
        pane.setStyle("-fx-font-size: 17;-fx-padding:30");
        pane.setSpacing(14);
        window.setScene(new Scene(s));
        window.showAndWait();
    }
}
