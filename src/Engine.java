import java.awt.*;

/**
 * Engine is the interface that implement the method update that allows us to have an internal timer.
 */

public interface Engine {
    void Draw(Graphics g);

    void update();
}
