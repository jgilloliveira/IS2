package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import static is.proyecto.login.Path.path;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserView;
    private EditText mPasswordView;
    private Spinner spi_proyecto;
    private ArrayList<String> proyecto;
    private ArrayList<Integer> idproyecto;
    private ArrayAdapter<String> adapter;
    private String idGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        spi_proyecto = (Spinner)findViewById(R.id.spinner);
        proyecto = new ArrayList<String>();
        idproyecto = new ArrayList<Integer>();
        adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, proyecto);
        spi_proyecto.setAdapter(adapter);
        listarProyecto();
    }

    public void iniciarSesion(View view){
        obtenerProyecto();
    }

    public void obtenerProyecto() {
        int indexProyecto = spi_proyecto.getSelectedItemPosition();
        String URL = path + "usuario/grupo/"+idproyecto.get(indexProyecto);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    idGrupo = json.getString("idGrupo");
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void login() {
        String URL = path + "usuario/login";
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject grupo = new JSONObject();
        try {
            grupo.put("idGrupo", idGrupo);
            json.put("nombreUsuario", user);
            json.put("contrasenha", password);
            json.put("grupo", grupo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("idUsuario");
                    int rol = response.getInt("rol");
                    if(rol == 1){
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        Intent menu = new Intent(LoginActivity.this, MenuSrumActivity.class);
                        menu.putExtra("id", id);
                        menu.putExtra("grupo", idGrupo);
                        startActivity(menu);
                    }else if(rol == 4){
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        Intent menu = new Intent(LoginActivity.this, MenuClienteActivity.class);
                        menu.putExtra("id", id);
                        menu.putExtra("grupo", idGrupo);
                        startActivity(menu);
                    }else if(rol == 0){
                        Toast.makeText(LoginActivity.this, "Este usuario no existe", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        Intent menu = new Intent(LoginActivity.this, MenuMiembroEquipoActivity.class);
                        menu.putExtra("id", id);
                        menu.putExtra("grupo", idGrupo);
                        startActivity(menu);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void iniciarProyecto(View view) {
        Intent iniciar = new Intent(this, ProyectoActivity.class);
        startActivity(iniciar);
    }

    public void listarProyecto(){
        String URL = path + "proyecto/listar";
        RequestQueue queue = Volley.newRequestQueue(this);
        adapter.clear();
        idproyecto.clear();
        proyecto.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idproyecto.add(Integer.parseInt(json.getString("idProyecto")));
                        proyecto.add(json.getString("nombre"));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}