package util;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 自定义拖动窗口事件
 *
 * @author zjw
 * @createTime 2020/8/17 09:54
 */
public class DragListener implements EventHandler<MouseEvent> {

    private double xOffset;
    private double yOffset;
    private final Stage stage;

    public DragListener(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }
}
