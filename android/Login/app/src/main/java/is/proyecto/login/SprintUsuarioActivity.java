package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static is.proyecto.login.Path.path;

public class SprintUsuarioActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> sprint;
    private ArrayList<Integer> idsprint;
    private ArrayAdapter<String> adapter;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_usuario);
        lv = (ListView)findViewById(R.id.listSprint);
        idUser = getIntent().getStringExtra("idUser");
        sprint = new ArrayList<String>();
        idsprint = new ArrayList<Integer>();
        adapter = new ArrayAdapter<String>(SprintUsuarioActivity.this, R.layout.list_usuario, sprint);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent us = new Intent(SprintUsuarioActivity.this, USUsuarioActivity.class);
                us.putExtra("idUser", idUser);
                us.putExtra("idSprint", idsprint.get(i).toString());
                startActivity(us);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarSprint();
    }

    public void listarSprint(){
        String URL = path + "sprint/listar/miembro/"+idUser;
        RequestQueue queue = Volley.newRequestQueue(this);
        adapter.clear();
        idsprint.clear();
        sprint.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idsprint.add(Integer.parseInt(json.getString("idSprint")));
                        sprint.add(json.getString("nombre"));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SprintUsuarioActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
