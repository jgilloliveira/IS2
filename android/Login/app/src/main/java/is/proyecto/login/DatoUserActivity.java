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

public class DatoUserActivity extends AppCompatActivity{

    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_usuario;
    private EditText et_contrasenha;
    private Spinner spi_rol;
    private Button btn_eliminar;
    private String id;
    private String bandera;
    private ArrayList<String> rol;
    private ArrayList<Integer> idRol;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dato_user);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_apellido = (EditText) findViewById(R.id.txt_apellido);
        et_usuario = (EditText) findViewById(R.id.txt_usuario);
        et_contrasenha = (EditText) findViewById(R.id.txt_contrasenha);
        spi_rol = (Spinner)findViewById(R.id.spinner);
        btn_eliminar = (Button)findViewById(R.id.btn_borrar);
        rol = new ArrayList<String>();
        idRol = new ArrayList<Integer>();
        adapter = new ArrayAdapter<String>(DatoUserActivity.this, android.R.layout.simple_spinner_item, rol);
        spi_rol.setAdapter(adapter);
        listarRol();
        bandera = getIntent().getStringExtra("bandera");
        if(bandera.equals("1")){
            btn_eliminar.setVisibility(View.INVISIBLE);
        }else if(bandera.equals("2")){
            id = getIntent().getStringExtra("id");
            cargarUsuario(id);
        }
    }

    public void aceptar(View view) {
        if(bandera.equals("1")){
            ultimoUsuario();
        }else if(bandera.equals("2")){
            modificarUsuario();
        }
    }

    public void eliminar(View view) {
        eliminarUsuario();
    }

    public void agregarUsuario(int ultimo) {
        String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.usuario/add";
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String usuario = et_usuario.getText().toString();
        String contrasenha = et_contrasenha.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject rol = new JSONObject();
        JSONObject grupo = new JSONObject();
        try {
            rol.put("idRol", spi_rol.getSelectedItemId()+1);
            grupo.put("idGrupo", 1);
            json.put("idUsuario", ultimo+1);
            json.put("nombre", nombre);
            json.put("apellido", apellido);
            json.put("nombreUsuario", usuario);
            json.put("contrasenha", contrasenha);
            json.put("rol", rol);
            json.put("grupo", grupo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUserActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(solicitud);
    }

    public void ultimoUsuario() {
        String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.usuario/ultimo";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int ultimoUser = 0;
                    JSONObject json = new JSONObject(response);
                    ultimoUser = json.getInt("ultimo");
                    agregarUsuario(ultimoUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUserActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void cargarUsuario(String id) {
        String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.usuario/listar/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    et_nombre.setText(json.getString("nombre"));
                    et_apellido.setText(json.getString("apellido"));
                    et_usuario.setText(json.getString("nombreUsuario"));
                    et_contrasenha.setText(json.getString("contrasenha"));
                    spi_rol.setSelection(json.getInt("rol")-2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUserActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void eliminarUsuario() {
        String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.usuario/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DatoUserActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUserActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void modificarUsuario() {
        String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.usuario/edit";
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String usuario = et_usuario.getText().toString();
        String contrasenha = et_contrasenha.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject jsonRol = new JSONObject();
        try {
            jsonRol.put("idRol", spi_rol.getSelectedItemId()+2);
            json.put("idUsuario", id);
            json.put("nombre", nombre);
            json.put("apellido", apellido);
            json.put("nombreUsuario", usuario);
            json.put("contrasenha", contrasenha);
            json.put("rol", jsonRol);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.PUT, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUserActivity.this, "Usuario modificado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(solicitud);
    }

    public void listarRol(){
        String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.rolsistema/listar";
        RequestQueue queue = Volley.newRequestQueue(this);
        adapter.clear();
        idRol.clear();
        rol.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idRol.add(Integer.parseInt(json.getString("idRol")));
                        rol.add(json.getString("descripcion"));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatoUserActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
