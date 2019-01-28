package com.ajubia.Network.Parser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ajubia.Activities.MainLayout;
import com.ajubia.Models.MenuModel;
import com.ajubia.Models.OtaModel;
import com.ajubia.R;
import com.ajubia.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;

import static com.ajubia.Activities.SplashActivity.menuModels;
import static com.ajubia.utality.Extra.Constant.otaModel;


public class MainMenuParse extends AsyncTask<Object,Void,String[]> {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String MyPREFERENCES = "MyPrefs" ;
    MenuModel menuModel=new MenuModel();
    private String[] lang_arry={"en","ar","fr","es","it","ru",""};
    Context c;
    private Locale myLocale;
    Intent intent;
    String s[];
    public  MainMenuParse(Context c)
    {
        s= new String[2];
        this.c=c;
        sharedPreferences = c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        intent=new Intent(c,MainLayout.class);
        intent.putExtra("CheckLayout","MainList");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    }
    @Override
    protected String[] doInBackground(Object... params) {

        try {
            JSONObject main_json = new JSONObject((String) params[0]);
            s[0]="nothing";
            if(main_json.getString("status").equals("success")) {
                s[0] = "true";
                s[1] = main_json.getString("code");

                JSONObject dataObject = main_json.getJSONObject("data");
                JSONObject ota = dataObject.getJSONObject("ota");
                JSONObject main_modulesObject = dataObject.getJSONObject("main_modules");
                JSONObject ota_modules = dataObject.getJSONObject("ota_modules");
                otaModel.setOta_id(ota.getString("ota_id"));


                for (Iterator<String> iter = main_modulesObject.keys(); iter.hasNext(); ) {
                    String key = iter.next();
                    JSONObject mainObject = main_modulesObject.getJSONObject(key);
                    JSONObject otaObject = ota_modules.getJSONObject(key);
                    if ((mainObject.getInt("is_active") == 1) && (otaObject.getInt("is_active") == 1) && otaObject.getInt("is_show_home") == 1) {
                        menuModel = new MenuModel();
                        menuModel.setDescription(R.string.hotesDescription);
                        menuModel.setType(otaObject.getString("nic_name"));
                        menuModels.add(menuModel);
                    }

                }
                return  s;
            }

        }catch (JSONException e) {
            e.printStackTrace();

            s[0]="false";
            s[1]=c.getString(R.string.error_api);
            return s;
        }
        return s;
    }

    @Override
    protected void onPostExecute(String[] aVoid) {
        super.onPostExecute(aVoid);
         if(aVoid[0].equals("false"))
        {
            Toast.makeText(c,s[1],Toast.LENGTH_LONG).show();
        }else
        {
            if(aVoid[0].equals("true"))
                changeLang(s[1]);

            c.startActivity(intent);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    public void changeLang(String lang)
    {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        c.getResources().updateConfiguration(config, c.getResources().getDisplayMetrics());
        Constant.default_lang=lang;
    }
    public void saveLocale(String lang, int id)
    {
        String langPref = "Language";
        editor.putString(langPref, lang);
        editor.putInt("check_rtl", id);
        editor.apply();
    }

}
