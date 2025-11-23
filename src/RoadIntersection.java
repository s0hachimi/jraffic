import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoadIntersection {
    
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);
        gc.strokeLine(420, 0, 420, 800);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);
        gc.strokeLine(500, 0, 500, 800);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);
        gc.strokeLine(580, 0, 580, 800);


        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);
        gc.strokeLine(0, 320, 1000, 320);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);
        gc.strokeLine(0, 400, 1000, 400);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.2);
        gc.strokeLine(0, 480, 1000, 480);

    }
}
