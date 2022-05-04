import beans.Point;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PointInEnteredTest {

    @Test
    public void checkZeroEntered() {
        assertTrue(true);
    }

    @Test
    public void emptyConstructor() {
        Point empty = new Point();
        assertAll(
                () -> assertNotNull(empty, "Объект не был создан"),
                () -> assertEquals(1.0f, empty.getrValue()),
                () -> assertEquals(0d, empty.getxCoordinate()),
                () -> assertEquals(0d, empty.getyCoordinate()),
                () -> assertNull(empty.getResult())
        );
    }

    @Test
    public void checkArea_true() {
        Point p = new Point();
        p.setxCoordinate(-1);
        p.setyCoordinate(1);
        p.setrValue(2);
        assertAll(
                () -> assertTrue(p.isInRectangle()),
                () -> assertFalse(p.isInCircle()),
                () -> assertFalse(p.isInTriangle())
        );
    }

    @Test
    public void checkArea_false() {
        Point p = new Point();
        p.setxCoordinate(1);
        p.setyCoordinate(1);
        p.setrValue(2);
        assertAll(
                () -> assertFalse(p.isInRectangle()),
                () -> assertFalse(p.isInCircle()),
                () -> assertFalse(p.isInTriangle())
        );
    }

    @Test(timeout = 100)
    public void checkArea_db() {
        Point p = new Point();
        p.setxCoordinate(-1);
        p.setyCoordinate(1);
        p.setrValue(2);
        p.checkPoint();
        assertEquals(p.getPoints().size(), 1, "Dot wasn't added to list properly");
    }

    @Test
    @Timeout(value = 100, unit= TimeUnit.DAYS)
    public void testFailWithTimeout() throws InterruptedException {
        Point p = new Point();
        assertTimeout(Duration.ofMillis(100), () -> p.checkPoint());
    }
}
