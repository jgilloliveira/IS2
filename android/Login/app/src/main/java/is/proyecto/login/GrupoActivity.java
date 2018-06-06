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

public class GrupoActivity extends AppCompatActivity {

    private String idProyecto;
    private String idUser;
    private String rol;
    private String idGrupo;
    private EditText et_nombre;
    private EditText et_cantidad;
    private int ultimoGrupo = 0;
    private String bandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_cantidad = (EditText) findViewById(R.id.txt_cantidad);
        bandera = getIntent().getStringExtra("bandera");
        if(bandera.equals("1")){
            idProyecto = getIntent().getStringExtra("idProyecto");
        }else if(bandera.equals("2")){
            idProyecto = getIntent().getStringExtra("idProyecto");
            idUser = getIntent().getStringExtra("id");
            idGrupo = getIntent().getStringExtra("idGrupo");
            rol = getIntent().getStringExtra("rol");
            cargarGrupo();
        }
    }

    public void aceptar(View view){
        if(bandera.equals("1")){
            ultimoGrupo();
        }else if(bandera.equals("2")){
            modificarGrupo();
        }
    }

    public void ultimoGrupo() {
        String URL = path + "grupodetrabajo/ultimo";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    ultimoGrupo = json.getInt("ultimo");
                    crearGrupo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GrupoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void crearGrupo() {
        String URL = path + "grupodetrabajo/add";
        String nombre = et_nombre.getText().toString();
        Integer cantidad = Integer.parseInt(et_cantidad.getText().toString());
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        JSONObject proyecto = new JSONObject();
        try {
            proyecto.put("idProyecto", idProyecto);
            json.put("idGrupo", ultimoGrupo+1);
            json.put("cantidad", cantidad);
            json.put("nombre", nombre);
            json.put("proyectoGrupo", proyecto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(GrupoActivity.this, "Grupo de trabajo creado", Toast.LENGTH_SHORT).show();
                        Intent scrum = new Intent(GrupoActivity.this, CuentaActivity.class);
                        scrum.putExtra("bandera", "1");
                        scrum.putExtra("idGrupo", (ultimoGrupo+1)+"");
                        scrum.putExtra("idProyecto", idProyecto);
                        startActivity(scrum);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GrupoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void cargarGrupo() {
        String URL = path + "grupodetrabajo/obtener/"+idGrupo;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    et_nombre.setText(json.getString("nombre"));
                    et_cantidad.setText(json.getString("cantidad"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GrupoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void modificarGrupo() {
        String URL = path + "grupodetrabajo/edit";
        String nombre = et_nombre.getText().toString();
        String cantidad = et_cantidad.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        try {
            json.put("idGrupo", idGrupo);
            json.put("nombre", nombre);
            json.put("cantidad", cantidad);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.PUT, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        Toast.makeText(GrupoActivity.this, "Grupo modificado", Toast.LENGTH_SHORT).show();
                        Intent proyecto = new Intent(GrupoActivity.this, ProyectoActivity.class);
                        proyecto.putExtra("bandera", "2");
                        proyecto.putExtra("idProyecto", idProyecto);
                        proyecto.putExtra("id", idUser);
                        proyecto.putExtra("idGrupo", idGrupo);
                        proyecto.putExtra("rol", rol);
                        startActivity(proyecto);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GrupoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(solicitud);
    }

    @Override
    public void onBackPressed() {
        if(bandera.equals("1")){
            eliminarProyecto();
            Intent proyecto = new Intent(GrupoActivity.this, ProyectoActivity.class);
            proyecto.putExtra("bandera", "1");
            startActivity(proyecto);
        }else if(bandera.equals("2")){
            Intent proyecto = new Intent(GrupoActivity.this, ProyectoActivity.class);
            proyecto.putExtra("bandera", "2");
            proyecto.putExtra("idProyecto", idProyecto);
            proyecto.putExtra("id", idUser);
            proyecto.putExtra("idGrupo", idGrupo);
            proyecto.putExtra("rol", rol);
            startActivity(proyecto);
        }
        finish();
    }

    public void eliminarProyecto() {
        String URL = path + "proyecto/"+idProyecto;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GrupoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }
}
