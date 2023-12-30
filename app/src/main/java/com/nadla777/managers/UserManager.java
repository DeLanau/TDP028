package com.nadla777.managers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class UserManager {

    private Context context;
    private static final String user_file = "local_user.json";
    private static final int list_size = 10;
    public UserManager(Context context) {
        this.context = context;
    }

    public void create_new_user(String username) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("total_time", 0);
            jsonObject.put("points", 0);
            jsonObject.put("list", new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        save_user(jsonObject);
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

    public List<Integer> get_list() {
        JSONObject jsonObject = get_user_data();
        if (jsonObject != null) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                Integer[] array = new Integer[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    array[i] = jsonArray.getInt(i);
                }

                //convert Integer[] array to List<Integer> and return
                return Arrays.asList(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int get_points() {
        JSONObject jsonObject = get_user_data();
        if (jsonObject == null)
            return 0;

        return jsonObject.optInt("points", 0);
    }

    public void add_value(long value) {
        JSONObject jsonObject = get_user_data();
        if(jsonObject != null) {
            try {
                JSONArray list = jsonObject.getJSONArray("list");
                if(list.length() >= list_size) {
                    list.remove(0);
                }
                list.put(value);
                jsonObject.put("list", list);

                int total_time = jsonObject.optInt("total_time", 0);
                jsonObject.put("total_time", total_time + value);

                int min = (int) (value / (1000 * 60));
                int points = jsonObject.optInt("points", 0);
                jsonObject.put("points", points + (min + (min / 3)));

                save_user(jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void save_user(JSONObject obj) {
        try {
            FileOutputStream outputStream = context.openFileOutput(user_file, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(obj.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
