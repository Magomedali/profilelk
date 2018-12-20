package ru.web_ali.profilelk.RestClient;

import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.lang.String;
import java.nio.charset.StandardCharsets;

public class Request {
    final String LOG_ID = "Request";
        private final String USER_AGENT = "GoBonus Android Client 1.0";
        private final String API_SERVER = "http://api.tcrm.web-ali.ru/";

        public String Content;


        public Request (String method, String uri, String params) {

            String urlParameters  = params; // url params
            byte[] postData       = new byte[0];

            System.setProperty("http.agent", USER_AGENT); // user agent truck

            postData = urlParameters.getBytes(Charset.forName("UTF-8"));

            int postDataLength = postData.length;
            URL url = null;

            try {
                url = new URL( API_SERVER + uri);
            } catch (
                MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(LOG_ID,"url: "+url.toString());

            HttpURLConnection conn= null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(method == "POST"){
                conn.setDoOutput( true );
            }

            conn.setInstanceFollowRedirects( false );
            try {
                Log.d(LOG_ID,"Method: "+method);
                conn.setRequestMethod(method);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            conn.setUseCaches( false );


                try {
                    if(method == "POST") {
                        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                        wr.write(postData);
                        wr.flush();
                        wr.close();
                    }
                    conn.connect();
                    Content = ConvertData(conn);
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }

        protected String ConvertData (HttpURLConnection url) throws IOException {
            // local variables
            String line;
            StringBuffer text = new StringBuffer();

            InputStreamReader in = new InputStreamReader((InputStream) url.getContent());
            BufferedReader buff = new BufferedReader(in);
            do {
                line = buff.readLine();
                text.append(line + "\n");
            } while (line != null);
            return text.toString();
        }

}
