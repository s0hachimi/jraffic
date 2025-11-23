import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private final int WIDTH = 1000;
    private final int HEIGHT = 800;

    private List<Car> cars = new ArrayList<>();

     @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Scene scene = new Scene(new StackPane(canvas));
        stage.setTitle("Jraffic");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) Car.spawnUp(cars);       // Spawn car from bottom going up
            if (e.getCode() == KeyCode.DOWN) Car.spawnDown(cars);   // Spawn car from top going down
            if (e.getCode() == KeyCode.LEFT) Car.spawnRight(cars);  // Spawn car from right going left
            if (e.getCode() == KeyCode.RIGHT) Car.spawnLeft(cars);  // Spawn car from left going right
            if (e.getCode() == KeyCode.R) Car.spawnRandom(cars);    // Spawn car from random direction
            if (e.getCode() == KeyCode.ESCAPE) stage.close();    
        });

         new AnimationTimer() {
            public void handle(long now) {
                Update.update(cars);   // Update game logic
                Update.draw(gc, cars);   // Draw everything
            }
        }.start();

    }

    public static void main(String[] args) {
        launch();
    }

}