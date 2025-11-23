
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Update {

    private static TrafficLights lights = new TrafficLights();
    private static RoadIntersection road = new RoadIntersection();

    public static void update(List<Car> cars) {

        lights.update();

        List<Car> copy = new ArrayList<>(cars);
        
        // Update each car
        for (Car car : cars) {
            // Check if traffic light allows car to move
            lights.applyRules(car);
            
            // Move car if it's allowed and no car is ahead
            if (car.isMoving() && !car.hasNextCar(copy)) {
                car.move();
            }
            
            // Make car turn at intersection if it hasn't turned yet
            if (!car.hasTurned()) {
                car.redirect();
            }
        }

        cars.removeIf(c -> c.getX() < -30 || c.getX() > 1030 || c.getY() < -30 || c.getY() > 830);

    }

    public static void draw(GraphicsContext gc, List<Car> cars) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 1000, 800);

        road.draw(gc);
        lights.draw(gc);

        for (Car c : cars) {
            c.draw(gc);
        }

        gc.setFill(Color.WHITE);
        gc.fillText("Arrows, R to spawn | Esc to quit", 12, 24);
    }

}
