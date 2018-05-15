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

    private String id;
    private EditText et_nombre;
    private EditText et_cantidad;
    private int ultimoGrupo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_cantidad = (EditText) findViewById(R.id.txt_cantidad);
        id = getIntent().getStringExtra("id");
    }

    public void aceptar(View view){
        ultimoGrupo();
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
            proyecto.put("idProyecto", id);
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
                        Intent scrum = new Intent(GrupoActivity.this, ScrumActivity.class);
                        scrum.putExtra("bandera", "1");
                        scrum.putExtra("idGrupo", (ultimoGrupo+1)+"");
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
}
