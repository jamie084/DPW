package project.deepwateroiltools.HTTP;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by janos on 02/04/2018.
 */
public class HTTPDataHandlerTest {

    @Mock
    String urlString ;
    HTTPDataHandler httpDataHandler;

    @Before
    public void setUp(){
        httpDataHandler = new HTTPDataHandler();
        urlString =  Common.getUrlUser() + Common.getApiKey();
    }

    @Test
    public void testHTTPConnection() throws  Exception{
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setConnectTimeout(10 *1000);
        urlConnection.setReadTimeout(10 * 1000);
        urlConnection.connect();
        assertEquals(200, urlConnection.getResponseCode());
    }

}