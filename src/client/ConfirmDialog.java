package client;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmDialog {
    public static enum Response{
        NO,YES
    };
    public static Response buttonSelected;
    public static void display(String title,String message){
        buttonSelected=Response.NO;
        Stage window=new Stage();
        window.initStyle(StageStyle.UTILITY);
        VBox pane=new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        Button btYes=new Button("确定");
        Button btNo=new Button("取消");
        HBox h=new HBox();
        h.getChildren().addAll(btYes,btNo);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(10);
        pane.getChildren().addAll(new Label(message),h);
        btYes.setOnAction(e->{
            buttonSelected=Response.YES;
            window.close();
        });
        btNo.setOnAction(e->{
            buttonSelected=Response.NO;
            window.close();
        });
        window.setTitle(title);
        window.setResizable(false);
        window.setScene(new Scene(pane));
        window.showAndWait();
    }
}