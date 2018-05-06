package is.proyecto.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class DatoUSActivity extends AppCompatActivity {

    private EditText et_descripcion;
    private Spinner spi_estado;
    private Spinner spi_usuario;
    private Button btn_eliminar;
    private String id;
    private String bandera;
    private String idSprint;
    private int ultimoUS = 0;
    private ArrayList<String> estado;
    private ArrayList<Integer> idestado;
    private ArrayList<String> usuario;
    private ArrayList<Integer> idusuario;
    private ArrayAdapter<String> adapterEstado;
    private ArrayAdapter<String> adapterUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dato_us);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        spi_estado = (Spinner)findViewById(R.id.sp_estado);
        spi_usuario = (Spinner)findViewById(R.id.sp_usuario);
        btn_eliminar = (Button)findViewById(R.id.btn_borrar);
        estado = new ArrayList<String>();
        idestado = new ArrayList<Integer>();
        usuario = new ArrayList<String>();
        idusuario = new ArrayList<Integer>();
        adapterEstado = new ArrayAdapter<String>(DatoUSActivity.this, android.R.layout.simple_spinner_item, estado);
        adapterUsuario = new ArrayAdapter<String>(DatoUSActivity.this, android.R.layout.simple_spinner_item, usuario);
        spi_estado.setAdapter(adapterEstado);
        spi_usuario.setAdapter(adapterUsuario);
        idSprint = getIntent().getStringExtra("idSprint");
        listarEstado();
    }

    public void aceptar(View view) {
        if(bandera.equals("1")){
            ultimoUS();
        }else if(bandera.equals("2")){
            modificarUS();
        }
    }

    public void eliminar(View view) {
        eliminarUS();
    }

    public void ultimoUS() {
        String URL = path + "userhistories/ultimo";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    ultimoUS = json.getInt("ultimo");
                    agregarUS();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void agregarUS() {
        String URL = path + "userhistories/add";
        String descripcion = et_descripcion.getText().toString();
        int indexEstado = spi_estado.getSelectedItemPosition();
        int indexUsuario = spi_usuario.getSelectedItemPosition();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject jEstado = new JSONObject();
        JSONObject jSprint = new JSONObject();
        JSONObject jUsuario = new JSONObject();
        try {
            jEstado.put("idEstadoKanban", idestado.get(indexEstado));
            jSprint.put("idSprint", idSprint);
            jUsuario.put("idUsuario", idusuario.get(indexUsuario));
            json.put("idUs", ultimoUS+1);
            json.put("descripcion", descripcion);
            json.put("estadoKanban", jEstado);
            json.put("usSprint", jSprint);
            json.put("usuario", jUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(DatoUSActivity.this, "User Story creado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(solicitud);
    }

    public void cargarUS() {
        String URL = path + "userhistories/obtener/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int idEstado, idUsuario, indexEstado, indexUsuario;
                    JSONObject json = new JSONObject(response);
                    et_descripcion.setText(json.getString("descripcion"));
                    idEstado = json.getInt("estadoKanban");
                    idUsuario = json.getInt("usuario");
                    indexEstado = idestado.indexOf(idEstado);
                    indexUsuario = idusuario.indexOf(idUsuario);
                    spi_estado.setSelection(indexEstado);
                    spi_usuario.setSelection(indexUsuario);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void eliminarUS() {
        String URL = path + "userhistories/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DatoUSActivity.this, "User Story eliminado", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void modificarUS() {
        String URL = path + "userhistories/edit";
        String descripcion = et_descripcion.getText().toString();
        int indexEstado = spi_estado.getSelectedItemPosition();
        int indexUsuario = spi_usuario.getSelectedItemPosition();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject jEstado = new JSONObject();
        JSONObject jUsuario = new JSONObject();
        try {
            jEstado.put("idEstadoKanban", idestado.get(indexEstado));
            jUsuario.put("idUsuario", idusuario.get(indexUsuario));
            json.put("idUs", id);
            json.put("descripcion", descripcion);
            json.put("estadoKanban", jEstado);
            json.put("usuario", jUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.PUT, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(DatoUSActivity.this, "User Story modificado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(solicitud);
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
                listarUsuario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void listarUsuario(){
        String URL = path + "usuario/listar/todos/"+idSprint;
        RequestQueue queue = Volley.newRequestQueue(this);
        adapterUsuario.clear();
        idusuario.clear();
        usuario.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idusuario.add(Integer.parseInt(json.getString("idUsuario")));
                        usuario.add(json.getString("nombre") + " " + json.getString("apellido"));
                        adapterUsuario.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bandera = getIntent().getStringExtra("bandera");
                if(bandera.equals("1")){
                    btn_eliminar.setVisibility(View.INVISIBLE);
                }else if(bandera.equals("2")){
                    id = getIntent().getStringExtra("id");
                    cargarUS();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUSActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
