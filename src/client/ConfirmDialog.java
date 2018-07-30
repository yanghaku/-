package client;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.*;

/**
 * @author yangbo
 *
 * 确认框
 *
 */

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
        pane.setSpacing(15);
        Button btYes=new Button("确定");
        Button btNo=new Button("取消");
        HBox h=new HBox();
        h.getChildren().addAll(btYes,btNo);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(19);
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
        StackPane s=new StackPane();
        s.getChildren().add(pane);
        s.setStyle("-fx-font-size: 19px;-fx-padding: 20");
        window.setScene(new Scene(s));
        window.showAndWait();
    }
}