package mbeans;

import db.PointEntity;
import db.PointInsertRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class Area implements Serializable, AreaMBean {

    private final PointInsertRepository insertRepository = new PointInsertRepository();

    @Override
    public double getAreaSize() {
        float r = 1; //todo: check default r
        List<PointEntity> points = insertRepository.getLastPoints(1);
        if (!points.isEmpty()) {
            r = points.get(0).getR();
        }
        return (r * r + Math.pow(r / 2, 2) / 2 + (Math.PI * Math.pow(r / 2, 2) / 4));
    }

    @PostConstruct
    public void init() {
        MBeanSessionLocalAgent.registerMBean(this, "AreaBeanName");
    }

    @PreDestroy
    public void preDestroy() {
        MBeanSessionLocalAgent.unregisterMBean(this, "AreaBeanName");
    }
}
