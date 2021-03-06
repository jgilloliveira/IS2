package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

public class USUsuarioActivity extends AppCompatActivity {

    private Spinner spi_estado;
    private ListView lv;
    private String idSprint;
    private String idUser;
    private String idEstado;
    private ArrayList<String> estado;
    private ArrayList<Integer> idestado;
    private ArrayList<String> us;
    private ArrayList<Integer> idus;
    private ArrayAdapter<String> adapterEstado;
    private ArrayAdapter<String> adapterUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ususuario);
        spi_estado = (Spinner)findViewById(R.id.sp_estado);
        lv = (ListView)findViewById(R.id.listUS);
        estado = new ArrayList<String>();
        idestado = new ArrayList<Integer>();
        us = new ArrayList<String>();
        idus = new ArrayList<Integer>();
        adapterEstado = new ArrayAdapter<String>(USUsuarioActivity.this, android.R.layout.simple_spinner_item, estado);
        adapterUS = new ArrayAdapter<String>(USUsuarioActivity.this, R.layout.list_usuario, us);
        spi_estado.setAdapter(adapterEstado);
        lv.setAdapter(adapterUS);
        idSprint = getIntent().getStringExtra("idSprint");
        idUser = getIntent().getStringExtra("idUser");
        spi_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idEstado = idestado.get(i).toString();
                listarUS();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent us = new Intent(USUsuarioActivity.this, DatoUSActivity.class);
                us.putExtra("bandera", "3");
                us.putExtra("idUS", idus.get(i).toString());
                us.putExtra("idUser", idUser);
                startActivity(us);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarEstado();
    }

    public void listarEstado(){
        String URL = path + "estadokanban/listar";
        RequestQueue queue = Volley.newRequestQueue(this);
        adapterEstado.clear();
        idestado.clear();
        estado.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idestado.add(Integer.parseInt(json.getString("idEstadoKanban")));
                        estado.add(json.getString("descripcion"));
                        adapterEstado.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(USUsuarioActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void listarUS(){
        String URL = path + "userhistories/listar/"+idSprint+"/"+idUser+"/"+idEstado;
        RequestQueue queue = Volley.newRequestQueue(this);
        adapterUS.clear();
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
                        adapterUS.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(USUsuarioActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
