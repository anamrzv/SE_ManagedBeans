package mbeans;

public interface CountMBean {
    long getAllPointsNumber();
    long getFailedPointsNumber();
    boolean lastNPointsIsFailed(int n);
}
