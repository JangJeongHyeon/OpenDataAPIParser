package provider;

import service.Service;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public interface API {
    /**
     *  Registration Default Service of api
     * @param s - default service
     */
    void registrationDefaultService(final Service s);

    /**
     *  Registration service of api
     * @param serviceName - service name
     * @param s - service
     */
    void registrationService(final String serviceName, final Service s);

    /**
     *
     * @return Default Service
     */
    Service getService();

    /**
     *  Return Service by service name
     * @param serviceName - service
     * @return
     */
    Service getService(final String serviceName);

}
