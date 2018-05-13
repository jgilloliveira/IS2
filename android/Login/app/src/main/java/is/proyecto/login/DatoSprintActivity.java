package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import static is.proyecto.login.Path.path;

public class DatoSprintActivity extends AppCompatActivity {

    private EditText et_nombre;
    private EditText dt_inicial;
    private EditText dt_final;
    private Button btn_us;
    private String id;
    private String bandera;
    private String idScrum;
    private int idProyecto;
    int ultimoSprint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dato_sprint);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        dt_inicial = (EditText) findViewById(R.id.date_inicio);
        dt_final = (EditText) findViewById(R.id.date_fin);
        btn_us = (Button)findViewById(R.id.btn_us);
        bandera = getIntent().getStringExtra("bandera");
        if(bandera.equals("1")){
            btn_us.setVisibility(View.INVISIBLE);
            idScrum = getIntent().getStringExtra("idScrum");
        }else if(bandera.equals("2")){
            id = getIntent().getStringExtra("id");
            cargarSprint();
        }
    }

    public void aceptar(View view) {
        if(bandera.equals("1")){
            ultimoSprint();
        }else if(bandera.equals("2")){
            modificarUsuario();
        }
    }

    public void userStory(View view){
        Intent us = new Intent(this, USActivity.class);
        us.putExtra("idSprint", id);
        startActivity(us);
    }

    public void ultimoSprint() {
        String URL = path + "sprint/ultimo";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    ultimoSprint = json.getInt("ultimo");
                    obtenerProyecto();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoSprintActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void obtenerProyecto() {
        String URL = path + "sprint/proyecto/"+idScrum;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    idProyecto = json.getInt("idProyecto");
                    agregarSprint();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoSprintActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void agregarSprint() {
        String URL = path + "sprint/add";
        String nombre = et_nombre.getText().toString();
        String fecha_inicio = dt_inicial.getText().toString();
        String fecha_fin = dt_final.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject proyecto = new JSONObject();
        try {
            proyecto.put("idProyecto", idProyecto);
            json.put("idSprint", ultimoSprint+1);
            json.put("nombre", nombre);
            json.put("fechaInicio", fecha_inicio+"T00:00:00-00:00");
            json.put("fechaFin", fecha_fin+"T00:00:00-00:00");
            json.put("proyecto", proyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(DatoSprintActivity.this, "Sprint creado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoSprintActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void cargarSprint() {
        String URL = path + "sprint/obtener/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    et_nombre.setText(json.getString("nombre"));
                    dt_inicial.setText(json.getString("fechaInicio"));
                    dt_final.setText(json.getString("fechaFin"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoSprintActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void modificarUsuario() {
        String URL = path + "sprint/edit";
        String nombre = et_nombre.getText().toString();
        String fecha_inicio = dt_inicial.getText().toString();
        String fecha_fin = dt_final.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject proyecto = new JSONObject();
        try {
            json.put("idSprint", id);
            json.put("nombre", nombre);
            json.put("fechaInicio", fecha_inicio+"T00:00:00-00:00");
            json.put("fechaFin", fecha_fin+"T00:00:00-00:00");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.PUT, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(DatoSprintActivity.this, "Sprint modificado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoSprintActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
