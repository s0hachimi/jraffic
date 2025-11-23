import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

public class Car {

    private double x, y;
    private Direction dir;
    private Color color;
    private boolean turned = false;  // Has the car turned at intersection yet?
    private boolean moving = false;  // Is the car allowed to move?
    
    // Constants
    private static final double SPEED = 0.5;              // How fast cars move
    private static final double SAFE_DISTANCE = 65;    // Distance to keep from other cars

    public Car(double x, double y, Direction dir, Color color) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.color = color;
    }

    /**
     * Moves the car in its current direction
     */
    public void move() {
        switch (dir) {
            case TOP -> y += SPEED;     // Moving up increases y
            case DOWN -> y -= SPEED;    // Moving down decreases y
            case LEFT -> x -= SPEED;    // Moving left decreases x
            case RIGHT -> x += SPEED;   // Moving right increases x
        }
    }

    /**
     * Makes cars turn at the intersection based on their color
     * Purple cars turn one way, Yellow cars turn the other way
     */
    public void redirect() {
        if (dir == Direction.TOP) {
            if (x == 440 && y == 335 && color == Color.PURPLE) { 
                dir = Direction.LEFT; turned = true; 
            }
            else if (x == 440 && y == 415 && color == Color.YELLOW) { 
                dir = Direction.RIGHT; turned = true; 
            }
        } else if (dir == Direction.DOWN) {
            if (y == 335 && x == 520 && color == Color.YELLOW) { 
                dir = Direction.LEFT; turned = true; 
            }
            else if (y == 415 && x == 520 && color == Color.PURPLE) { 
                dir = Direction.RIGHT; turned = true; 
            }
        } else if (dir == Direction.RIGHT) {
            if (x == 435 && y == 420 && color == Color.PURPLE) { 
                dir = Direction.TOP; turned = true; 
            }
            else if (x == 515 && y == 420 && color == Color.YELLOW) { 
                dir = Direction.DOWN; turned = true; 
            }
        } else if (dir == Direction.LEFT) {
            if (x == 435 && y == 340 && color == Color.YELLOW) { 
                dir = Direction.TOP; turned = true; 
            }
            else if (x == 515 && y == 340 && color == Color.PURPLE) { 
                dir = Direction.DOWN; turned = true; 
            }
        }
    }

    /**
     * Checks if there's another car too close ahead in the same direction
     * Returns true if we need to stop to avoid collision
     */
    public boolean hasNextCar(List<Car> cars) {
        for (Car other : cars) {
            // Skip checking against ourselves
            if (other == this) continue;
            
            // Only check cars going in the same direction
            if (other.dir == dir) {
                switch (dir) {
                    case TOP -> { 
                        // Check if other car is ahead (higher y) and too close
                        if (other.y > y && other.y - y <= SAFE_DISTANCE) return true; 
                    }
                    case DOWN -> { 
                        // Check if other car is ahead (lower y) and too close
                        if (other.y < y && y - other.y <= SAFE_DISTANCE) return true; 
                    }
                    case LEFT -> { 
                        // Check if other car is ahead (lower x) and too close
                        if (other.x < x && x - other.x <= SAFE_DISTANCE) return true; 
                    }
                    case RIGHT -> { 
                        // Check if other car is ahead (higher x) and too close
                        if (other.x > x && other.x - x <= SAFE_DISTANCE) return true; 
                    }
                }
            }
        }
        return false;  // No car too close ahead
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, 40, 40);
    }

    // Getters
    public boolean hasTurned() { return turned; }
    public boolean isMoving() { return moving; }
    public Direction getDir() { return dir; }
    public double getX() { return x; }
    public double getY() { return y; }
    
    // Setters
    public void setMoving(boolean moving) { this.moving = moving; }

    /**
     * Returns a random color for the car (Blue, Yellow, or Purple)
     */
    private static Color randomColor() {
        Color[] colors = {Color.BLUE, Color.YELLOW, Color.PURPLE};
        return colors[new Random().nextInt(colors.length)];
    }

    // ========== CAR SPAWNING METHODS ==========
    
    /**
     * Helper: Adds a car only if there aren't too many in that direction already
     */
    private static void pushCar(List<Car> cars, Car c, Direction dir) {
        long count = cars.stream().filter(car -> car.getDir() == dir).count();
        if (count < 4) {  // Limit to 4 cars per direction
            cars.add(c);
        }
    }

    /**
     * Spawns a car from the bottom of the screen, heading up/down
     */
    public static void spawnUp(List<Car> cars) {
        pushCar(cars, new Car(520, 800, Direction.DOWN, randomColor()), Direction.DOWN);
    }

    /**
     * Spawns a car from the top of the screen, heading down/up
     */
    public static void spawnDown(List<Car> cars) {
        pushCar(cars, new Car(440, -30, Direction.TOP, randomColor()), Direction.TOP);
    }

    /**
     * Spawns a car from the left side of the screen, heading right
     */
    public static void spawnLeft(List<Car> cars) {
        pushCar(cars, new Car(-30, 420, Direction.RIGHT, randomColor()), Direction.RIGHT);
    }

    /**
     * Spawns a car from the right side of the screen, heading left
     */
    public static void spawnRight(List<Car> cars) {
        pushCar(cars, new Car(1000, 340, Direction.LEFT, randomColor()), Direction.LEFT);
    }

    /**
     * Spawns a car from a random direction
     */
    public static void spawnRandom(List<Car> cars) {
        switch (new Random().nextInt(4)) {
            case 0 -> spawnUp(cars);
            case 1 -> spawnDown(cars);
            case 2 -> spawnLeft(cars);
            case 3 -> spawnRight(cars);
        }
    }
}
