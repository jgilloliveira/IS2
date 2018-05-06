package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static is.proyecto.login.Path.path;

public class ProyectoActivity extends AppCompatActivity {

    private EditText et_nombre;
    private EditText et_descripcion;
    private EditText dt_final;
    private EditText dt_inicial;
    private Spinner spi_estado;
    private ArrayList<String> estado;
    private ArrayList<Integer> idEstado;
    private ArrayAdapter<String> adapter;
    private int ultimoProyecto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        dt_final = (EditText) findViewById(R.id.date_final);
        dt_inicial = (EditText) findViewById(R.id.date_inicial);
        spi_estado = (Spinner)findViewById(R.id.spinner);
        estado = new ArrayList<String>();
        idEstado = new ArrayList<Integer>();
        adapter = new ArrayAdapter<String>(ProyectoActivity.this, android.R.layout.simple_spinner_item, estado);
        spi_estado.setAdapter(adapter);
        listarEstado();
    }

    public void listarEstado(){
        String URL = path + "estadoproyecto/listar";
        RequestQueue queue = Volley.newRequestQueue(this);
        adapter.clear();
        idEstado.clear();
        estado.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idEstado.add(Integer.parseInt(json.getString("idEstado")));
                        estado.add(json.getString("descripcion"));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProyectoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void aceptar(View view) {
        ultimoProyecto();
    }

    public void ultimoProyecto() {
        String URL = path + "proyecto/ultimo";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    ultimoProyecto = json.getInt("ultimo");
                    crearProyecto();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProyectoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void crearProyecto() {
        String URL = path + "proyecto/add";
        String nombre = et_nombre.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String fecha_inicio = dt_inicial.getText().toString();
        String fecha_fin = dt_final.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject estado = new JSONObject();
        try {
            estado.put("idEstado", spi_estado.getSelectedItemId()+1);
            json.put("idProyecto", ultimoProyecto+1);
            json.put("nombre", nombre);
            json.put("descripcion", descripcion);
            json.put("fechaInicio", fecha_inicio+"T00:00:00-00:00");
            json.put("fechaFin", fecha_fin+"T00:00:00-00:00");
            json.put("estado", estado);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(ProyectoActivity.this, "Proyecto creado", Toast.LENGTH_SHORT).show();
                        Intent grupo = new Intent(ProyectoActivity.this, GrupoActivity.class);
                        grupo.putExtra("id", (ultimoProyecto+1)+"");
                        startActivity(grupo);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProyectoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
