package com.pushkar.teachme;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Login extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Button sign_in = (Button)findViewById(R.id.signin_button);

        //Button login_button = new Button(findViewById(R.id.login_button));

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread send_data = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendStrem();
                    }
                });send_data.start();
            }
        });
    }

    public class Get_Data extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (Exception e)
            {
                System.out.println("Data:"+e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("Response", "" + server_response);


        }
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
                System.out.println("Data::"+line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    private void sendStrem(){
        try
        {
            EditText usr_email = (EditText)findViewById(R.id.user_name);
            EditText usr_password = (EditText)findViewById(R.id.password);

            String User_id = usr_email.getText().toString();
            String mPassword = usr_password.getText().toString();

            //sending user data to server
            String data = URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(User_id, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                    + URLEncoder.encode(mPassword, "UTF-8");

            URL url = new URL("http://pushkar2gr8.000webhostapp.com/dbconnect.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            //reading server responce
            String text = "";
            BufferedReader reader=null;
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
                //Toast toast = Toast.makeText(getApplicationContext(),""+line, Toast.LENGTH_LONG);
                //toast.show();
                System.out.println("Server Responce::"+line);
                if(line=="valid"){
                    System.out.printf("good");
                    usr_email.setText("");
                    usr_password.setText("");
                }else {
                    System.out.printf("not good");
                    usr_email.setText("");
                    usr_password.setText("");
                }
            }
        }catch (Exception e){
            System.out.println("Exception sending data::"+e);
        }
    }

}
