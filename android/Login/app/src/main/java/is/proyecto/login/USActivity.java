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

public class USActivity extends AppCompatActivity {

    private ListView lv1;
    private ArrayList<String> us;
    private ArrayList<Integer> idus;
    private ArrayAdapter<String> adapter;
    private String idSprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);
        lv1 = (ListView)findViewById(R.id.listUS);
        idSprint = getIntent().getStringExtra("idSprint");
        us = new ArrayList<String>();
        idus = new ArrayList<Integer>();
        adapter = new ArrayAdapter<String>(USActivity.this, R.layout.list_usuario, us);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent dato = new Intent(USActivity.this, DatoUSActivity.class);
                dato.putExtra("bandera", "2");
                dato.putExtra("idSprint", idSprint);
                dato.putExtra("id", idus.get(i).toString());
                startActivity(dato);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarUS();
    }

    public void listarUS(){
        String URL = path + "userhistories/listar/"+idSprint;
        RequestQueue queue = Volley.newRequestQueue(this);
        adapter.clear();
        idus.clear();
        us.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idus.add(Integer.parseInt(json.getString("idUs")));
                        us.add(json.getString("descripcion"));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(USActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void agregar(View view){
        Intent dato = new Intent(this, DatoUSActivity.class);
        dato.putExtra("bandera", "1");
        dato.putExtra("idSprint", idSprint);
        startActivity(dato);
    }
}
