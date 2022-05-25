package beans;

import db.PointEntity;
import db.PointInsertRepository;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@ManagedBean
@SessionScoped
public class Point {
    private float xCoordinate;
    private float yCoordinate;
    private float rValue = 1.0f;
    private String result;
    private LocalDateTime time;

    private LinkedList<PointEntity> points = new LinkedList<>();
    private PointInsertRepository insertRepository = new PointInsertRepository();
    public Point() {

    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public float getrValue() {
        return rValue;
    }

    public void setrValue(float rValue) {
        this.rValue = rValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LinkedList<PointEntity> getPoints() {
        return points;
    }

    public void checkPoint() {
        PointEntity pointDB = new PointEntity();
        boolean isInFigure = isInTriangle() || isInRectangle() || isInCircle();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        pointDB.setTime(dateFormat.format(LocalDateTime.now()));
        pointDB.setX(this.xCoordinate);
        pointDB.setY(this.yCoordinate);
        pointDB.setR(this.rValue);
        if (isInFigure) pointDB.setResult("Попадание");
        else pointDB.setResult("Промах");
        insertRepository.insertPoint(pointDB);
        points.add(pointDB);
    }

    public boolean isInTriangle() {
        return xCoordinate >= 0 && yCoordinate <= 0 && yCoordinate >= xCoordinate - rValue / 2;
    }

    public boolean isInRectangle() {
        return xCoordinate <= 0 && yCoordinate >= 0 && yCoordinate <= rValue && xCoordinate >= -rValue;
    }

    public boolean isInCircle() {
        return xCoordinate <= 0 && yCoordinate <= 0 && xCoordinate * xCoordinate + yCoordinate * yCoordinate <= rValue * rValue;
    }

}
