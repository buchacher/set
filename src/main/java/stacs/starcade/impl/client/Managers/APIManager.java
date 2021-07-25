package stacs.starcade.impl.client.Managers;

import stacs.starcade.impl.client.Interfaces.IAPIManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The Class APIManager.
 */
public class APIManager implements IAPIManager {

    /**
     * Call API.
     *
     * @param path the path
     * @param method the method
     * @return the string buffer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    //    https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
    public StringBuffer callAPI(String path, String method) throws IOException {
        URL urlForGetRequest = new URL("http://localhost:8080/" + path);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod(method);

        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
            return response;
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println(method + " NOT WORKED");
            return null;
        }
    }
}
