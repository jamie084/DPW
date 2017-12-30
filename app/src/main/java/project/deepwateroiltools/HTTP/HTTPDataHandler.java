package project.deepwateroiltools.HTTP;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by janos on 21/12/2017.
 */

public class HTTPDataHandler {
    static String stream=null;

    public HTTPDataHandler(){

    }

    public String GetHTTPData(String urlString){
        String stream=null;

        try{
            Log.d("URLstring", urlString);
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(10 *1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.connect();


            if(urlConnection.getResponseCode() == 200){
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line=r.readLine()) != null){
                    sb.append(line);
                    stream = sb.toString();
                    urlConnection.disconnect();
                }

            }
            else{
                //TODO error handling
                Log.d("Response CODE", "not 200");
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return stream;
    }



    public String PostHTTPData(String urlString, String json) {
          try{
             URL url = new URL(urlString);
             HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type","application/json; charset-UTF-8");

            urlConnection.connect();
            try(OutputStream os = urlConnection.getOutputStream())
            {
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();
              return response.toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
