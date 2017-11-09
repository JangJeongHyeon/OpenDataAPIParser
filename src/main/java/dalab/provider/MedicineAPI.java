package dalab.provider;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import dalab.service.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public class MedicineAPI implements API {
    private  Map<String, Service> services = new ConcurrentHashMap<String, Service>();
    private  String DEFAULT_SERVICE_NAME = "<Not define>";
    private  HttpClient defaultClient = HttpClients.createDefault();

    static {
        Logger.getLogger("org.apache.http").setLevel(Level.OFF);
    }

    public void registrationDefaultService(final Service s) {
        registrationService(DEFAULT_SERVICE_NAME,s);
    }

    public void registrationService(final String serviceName, final Service s) {
        services.put(serviceName,s);
    }

    public Service getService() {
        return getService(DEFAULT_SERVICE_NAME);
    }

    public Service getService(final String serviceName) {
        Service service = services.get(serviceName);
        if(service == null)
            throw new IllegalArgumentException("Not registered dalab.service with name : "+serviceName);
        return service;
    }

    public void executeService() {
        executeService(DEFAULT_SERVICE_NAME);
    }

    public void executeService(final String serviceName) {
        Service service = getService(serviceName);
        service.run(defaultClient);
    }
}
