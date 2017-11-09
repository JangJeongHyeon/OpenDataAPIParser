package dalab.service;

import org.apache.http.client.HttpClient;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public interface Service {
    /**
     *  Execute dalab.service request
     */
    void run(HttpClient  client);
}
