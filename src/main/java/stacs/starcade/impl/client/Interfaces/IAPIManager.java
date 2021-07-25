package stacs.starcade.impl.client.Interfaces;

import java.io.IOException;

/**
 * The Interface IAPIManager.
 */
public interface IAPIManager {

    /**
     * Call API.
     *
     * @param path the path
     * @param method the method
     * @return the string buffer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    StringBuffer callAPI(String path, String method) throws IOException;
}
