package beans;

import db.PointEntity;
import db.PointInsertRepository;
import mbeans.CountMBean;
import mbeans.MBeanSessionLocalAgent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class CountBean extends NotificationBroadcasterSupport implements Serializable, CountMBean {

    private final PointInsertRepository insertRepository = new PointInsertRepository();
    private long sequenceNumber = 0;

    @PostConstruct
    public void init() {
        MBeanSessionLocalAgent.registerMBean(this, "CountBeanName");
    }

    @PreDestroy
    public void preDestroy() {
        MBeanSessionLocalAgent.unregisterMBean(this, "CountBeanName");
    }

    @Override
    public long getAllPointsNumber() {
        List<PointEntity> points = insertRepository.getAllPoints();
        if (lastNPointsIsFailed(3))
            sendNotification(new Notification("Три промаха подряд", this.getClass().getName(), sequenceNumber++, "Было совершено три промаха подряд"));
        return points.size();
    }

    @Override
    public boolean lastNPointsIsFailed(int n) {
        return insertRepository.getLastPoints(n).stream().allMatch(p -> "Промах".equals(p.getResult()));
    }

    @Override
    public long getFailedPointsNumber() {
        List<PointEntity> failedPoints = insertRepository.getFailedPoints();
        return failedPoints.size();
    }

    //todo:???
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "Три промаха подряд";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }
}
