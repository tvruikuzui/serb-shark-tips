package com.example.Service;

import com.example.Entity.User;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by User on 26/04/2017.
 */
public class MailchampThread extends Thread {
    private User user;
    Response response;
    boolean isSent = false;

    public boolean isSent() {
        return isSent;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        JSONObject merge = new JSONObject();
        try {
            merge.put("FNAME",user.getName());
            merge.put("LNAME",user.getLastName());
            merge.put("MMERGE3",user.getCountryCode());
            merge.put("MMERGE4",user.getCountry());
            jsonObject.put("email_address",user.getEmail());
            jsonObject.put("status","subscribed");
            jsonObject.put("merge_fields",merge);

        }  catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("", jsonObject.toString())
//                .build();
        RequestBody requestBody = RequestBody.create(JSON,jsonObject.toString());

        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Authorization", Credentials.basic("doesntmatter", ""))
                .url("https://us10.api.mailchimp.com/3.0/lists/5f91e654f5/members")
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            //System.out.println(response.toString());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        if (response.toString().contains("message=OK")){
            isSent = true;
        }
        stop();
    }

}
