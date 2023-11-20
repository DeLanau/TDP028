package com.nadla777.managers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UserManager {

    private Context context;
    private static String user_file = "local_user.json";

    public UserManager(Context context) {
        this.context = context;
    }

    public void create_new_user(String username) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("total_time", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream outputStream = context.openFileOutput(user_file, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(jsonObject.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject get_user_data() {
        try {
            FileInputStream inputStream = context.openFileInput(user_file);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder jsonAsString = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonAsString.append(line);
            }

            JSONObject jsonObject = new JSONObject(jsonAsString.toString());
            return jsonObject;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        File dir = context.getFilesDir();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

}
