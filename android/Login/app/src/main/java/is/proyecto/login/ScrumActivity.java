package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class ScrumActivity extends AppCompatActivity {

    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_usuario;
    private EditText et_contrasenha;
    private String bandera;
    private String idGrupo;
    private String idScrum;
    private int ultimoUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrum);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_apellido = (EditText) findViewById(R.id.txt_apellido);
        et_usuario = (EditText) findViewById(R.id.txt_usuario);
        et_contrasenha = (EditText) findViewById(R.id.txt_contrasenha);
        bandera = getIntent().getStringExtra("bandera");
        if(bandera.equals("1")){
            idGrupo = getIntent().getStringExtra("idGrupo");
        }else if(bandera.equals("2")){
            idScrum = getIntent().getStringExtra("idScrum");
            cargarUsuario();
        }
    }

    public void aceptar(View view) {
        if(bandera.equals("1")){
            ultimoUsuario();
        }else if(bandera.equals("2")){
            modificarUsuario();
        }
    }

    public void ultimoUsuario() {
        String URL = path + "usuario/ultimo";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    ultimoUser = json.getInt("ultimo");
                    agregarScrum();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ScrumActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void agregarScrum() {
        String URL = path + "usuario/add";
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String usuario = et_usuario.getText().toString();
        String contrasenha = et_contrasenha.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject rol = new JSONObject();
        JSONObject grupo = new JSONObject();
        try {
            rol.put("idRol", 1);
            grupo.put("idGrupo", idGrupo);
            json.put("idUsuario", ultimoUser+1);
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
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(ScrumActivity.this, "Bienvenido Scrum Master", Toast.LENGTH_SHORT).show();
                        Intent menu = new Intent(ScrumActivity.this, MenuSrumActivity.class);
                        menu.putExtra("id", (ultimoUser+1)+"");
                        menu.putExtra("grupo", idGrupo);
                        startActivity(menu);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ScrumActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void cargarUsuario() {
        String URL = path + "usuario/obtener/"+idScrum;
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ScrumActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void modificarUsuario() {
        String URL = path + "usuario/edit";
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String usuario = et_usuario.getText().toString();
        String contrasenha = et_contrasenha.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject jsonRol = new JSONObject();
        try {
            jsonRol.put("idRol", 1);
            json.put("idUsuario", idScrum);
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
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(ScrumActivity.this, "Usuario modificado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ScrumActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(solicitud);
    }
}
