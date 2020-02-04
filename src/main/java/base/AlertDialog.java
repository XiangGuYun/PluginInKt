package base;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertDialog {

    public static void display(String title, String msg){
        Stage window = new Stage();
        //初始化形式(形态)
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label label = new Label(msg);

        Button closeBtn = new Button("关闭");
        closeBtn.setOnAction(e->{
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeBtn);
        label.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}