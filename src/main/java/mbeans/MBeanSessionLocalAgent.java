package mbeans;


import javax.management.*;
import java.lang.management.ManagementFactory;

public class MBeanSessionLocalAgent {
    private static final MBeanServer server;

    static {
        server = ManagementFactory.getPlatformMBeanServer();
    }

    public static void registerMBean(Object o, String name) {
        try {
            ObjectName objectName = getObjectName(o, name);
            server.registerMBean(o, objectName);
            System.out.println("Register: " + objectName.getCanonicalName());
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    public static void unregisterMBean(Object object, String name) {
        try {
            ObjectName objectName = getObjectName(object, name);
            server.unregisterMBean(objectName);
            System.out.println("Unregister: " + objectName.getCanonicalName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static ObjectName getObjectName(Object object, String name) throws MalformedObjectNameException {
        String pckg = object.getClass().getPackage().getName();
        String clss = object.getClass().getSimpleName();
        String objname = pckg + ":type=" + clss + ",name=" + name;
        return new ObjectName(objname);
    }

}