package project.deepwateroiltools_001;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;
import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;

import project.dto.service.ProcedureImg;
import project.dto.service.ProcedureSlide;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_001_ToolOverView;

public class SeaCure extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sea_cure);

        String url = Common.getUrlSeaCureProcedure() + Common.getApiKey();//+ "&q={\"password\":\"" + password.getText().toString() + "\",\"user\":\"" + email.getText().toString() + "\",\"isActive\":true}";
        new SeaCure.RunDbQuery(url).execute();
        loadFragment(new Fragment_001_ToolOverView());
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.crossfade_content, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private class RunDbQuery extends AsyncTask<String, Void, String> {
        String urlString= null;
        private ProgressDialog dialog;

        private RunDbQuery(String urlString){
            this.urlString = urlString;
            dialog = new ProgressDialog(SeaCure.this, R.style.DialogBoxStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Connecting to the server...");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HTTPDataHandler httpDataHandler = new HTTPDataHandler();
            String stream = httpDataHandler.GetHTTPData(urlString);
            Log.d("JSON data", stream);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<ProcedureSlide>>(){}.getType();
            List<ProcedureSlide> users = gson.fromJson(s, listType);


            GsonFireBuilder builder = new GsonFireBuilder()
                    .registerTypeSelector(ProcedureSlide.class, new TypeSelector<ProcedureSlide>() {
                        @Override
                        public Class<? extends ProcedureSlide> getClassForElement(JsonElement readElement) {
                            String type = readElement.getAsJsonObject().get("type").getAsString();
                            if(type.equals("ProcedureImg")){
                                return ProcedureImg.class;
                            } else if(type.equals("cow")) {
                                return ProcedureImg.class;
                            } else {
                                return null; //returning null will trigger Gson's default behavior
                            }
                        }
                    });

            Gson gson2 = builder.createGson();

            List<ProcedureSlide> users2 =  gson2.fromJson(s, listType);


            Log.d(" First class is: " + users2.get(0).getClass().toString(), "SEcond class type is:" + users2.get(1).getClass().toString());






            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!users.isEmpty()) {

            }
            else{

                Log.d("User","no entries");
            }
        }
    }
}
