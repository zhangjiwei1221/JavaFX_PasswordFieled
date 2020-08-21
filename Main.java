import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.DragListener;

/**
 * 解决 JavaFX PasswordField 无法显示密码问题
 *
 * @author zjw
 * @createTime 2020/8/18 15:06
 */
public class Main extends Application {

    private String password = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        AnchorPane root = new AnchorPane();

        // 自定义关闭窗口按钮
        Button close = initCloseButton();

        // 初始化隐藏密码框设置
        PasswordField hidPwd = new PasswordField();
        initField(hidPwd);
        hidPwd.setId("hidPwd");

        // 初始化显示密码框设置
        TextField disPwd = new TextField();
        initField(disPwd);
        disPwd.setId("disPwd");

        // 设置两种密码框(显示/隐藏)的切换监听
        setOnClickAction(root, hidPwd, disPwd);
        setOnClickAction(root, disPwd, hidPwd);

        root.getChildren().addAll(close, hidPwd);

        Scene scene = new Scene(root, 400, 270);
        scene.setFill(Paint.valueOf("#ffffff00"));

        setDragListener(stage, scene);
        scene.getStylesheets().add(Main.class.getResource("css/index.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private Button initCloseButton() {
        Button close = new Button();
        close.setId("close");
        close.setOnMouseClicked(event -> System.exit(0));
        close.setLayoutX(370);
        close.setLayoutY(10);
        return close;
    }

    private void setCursorStyle(TextField disPwd, MouseEvent event) {
        // 当鼠标移动到 👁 附近时修改鼠标指针样式，否则为默认的 I 样式
        double x = disPwd.getWidth() + 70;
        if (event.getSceneX() > x) {
            disPwd.setStyle("-fx-cursor: hand;");
        } else {
            disPwd.setStyle("-fx-cursor: text;");
        }
    }

    private void setDragListener(Stage stage, Scene scene) {
        // 自定义拖动窗体事件
        DragListener listener = new DragListener(stage);
        scene.setOnMouseDragged(listener);
        scene.setOnMousePressed(listener);
    }

    private void initField(TextField field) {
        // PasswordField 继承自 TextField 故也可使用该方法
        field.setPromptText("请输入密码");
        field.setLayoutX(100);
        field.setLayoutY(115);
        // 设置鼠标移动时图标的样式
        field.setOnMouseMoved(event -> setCursorStyle(field, event));
        // 添加输入域值改变事件
        field.textProperty().addListener((observable, oldValue, newValue) -> password = newValue);
    }

    private void setOnClickAction(AnchorPane root, TextField removeFiled, TextField addField) {
        // 设置点击 👁 时图标及输入框的切换，以便显示/隐藏密码
        removeFiled.setOnMouseClicked(event -> {
            double x = removeFiled.getWidth() + 70;
            if (event.getSceneX() > x) {
                root.getChildren().remove(removeFiled);
                root.getChildren().add(addField);
                addField.setText(password);
            }
        });
    }

}
