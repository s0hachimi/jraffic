import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class TrafficLights {

    private boolean top = false, down = false, left = false, right = false;

    private Direction current = Direction.RIGHT;
    private boolean state = false; // true = green light on, false = all lights red
    private long lastSwitch = System.currentTimeMillis();

    /**
     * Updates traffic light states - cycles through directions
     */
   public void update() {
        long elapsed = System.currentTimeMillis() - lastSwitch;
        long greenDuration = 3000;  // How long light stays green (3 seconds)
        long offDuration = 100;     // Brief pause between switches

        // If light has been green long enough, turn all lights red
        if (state && elapsed >= greenDuration) {
            top = down = left = right = false;
            state = false;
            lastSwitch = System.currentTimeMillis();
        } 
        // After brief pause, switch to next direction
        else if (!state && elapsed >= offDuration) {
            switch (current) {
                case RIGHT -> { current = Direction.TOP; top = true; }
                case TOP -> { current = Direction.LEFT; left = true; }
                case LEFT -> { current = Direction.DOWN; down = true; }
                case DOWN -> { current = Direction.RIGHT; right = true; }
            }
            state = true;
            lastSwitch = System.currentTimeMillis();
        }
    }

    /**
     * Checks if a car should stop at the red light
     * If light is red and car is at the stop line, car must stop
     */
    public void applyRules(Car car) {
        double x = car.getX();
        double y = car.getY();
        Direction d = car.getDir();

        // Check if car is at a red light stop position
        boolean shouldStop = (!down && d == Direction.DOWN && y == 490) ||
                (!top && d == Direction.TOP && y == 270) ||
                (!right && d == Direction.RIGHT && x == 370) ||
                (!left && d == Direction.LEFT && x == 590);

        if (shouldStop) {
            car.setMoving(false); // Stop the car
        } else {
            car.setMoving(true); // Allow car to move
        }
    }

    public void draw(GraphicsContext gc) {

        // Top light (controls cars going up)
        gc.setFill(top ? Color.GREEN : Color.RED);
        gc.fillRect(370, 270, 49, 49);

        // Right light (controls cars going right)
        gc.setFill(right ? Color.GREEN : Color.RED);
        gc.fillRect(370, 481, 49, 49);
        
        // Down light (controls cars going down)
        gc.setFill(down ? Color.GREEN : Color.RED);
        gc.fillRect(581, 481, 49, 49);
        
        // Left light (controls cars going left)
        gc.setFill(left ? Color.GREEN : Color.RED);
        gc.fillRect(581, 270, 49, 49);
       
    }
}
