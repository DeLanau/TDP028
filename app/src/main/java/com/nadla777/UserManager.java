package com.nadla777;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;

public class UserManager {

    private Context context;
    private static String user_file = "local_user.json";

    public UserManager(Context context) {
        this.context = context;
    }

    public boolean exist(String username) {
        try {
            String json = read_json(user_file);
            JSONObject user_data = new JSONObject(json);
            return user_data.has(username);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String read_json(String file_name) throws IOException {
        InputStream inputStream = context.getAssets().open(file_name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder j_string = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            j_string.append(line);
        }
        reader.close();
        return j_string.toString();
    }

}
