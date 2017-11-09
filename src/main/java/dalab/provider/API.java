package dalab.provider;

import dalab.service.Service;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */

public interface API {
    /**
     *  Registration default dalab.service of api
     * @param s - default dalab.service
     */
    void registrationDefaultService(final Service s);

    /**
     *  Registration dalab.service of api
     * @param serviceName - dalab.service name
     * @param s - dalab.service
     */
    void registrationService(final String serviceName, final Service s);

    /**
     *
     * @return Default dalab.service
     */
    Service getService();

    /**
     *  Return dalab.service by dalab.service name
     * @param serviceName - dalab.service
     * @return
     */
    Service getService(final String serviceName);

    /**
     * Execute default dalab.service
     */
    void executeService();

    /**
     * Execute dalab.service by dalab.service name
     * @param serviceName - dalab.service name for executing dalab.service
     */
    void executeService(final String serviceName);
}
