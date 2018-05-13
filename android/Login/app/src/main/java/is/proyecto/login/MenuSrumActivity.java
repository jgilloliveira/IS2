package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static is.proyecto.login.Path.path;

public class MenuSrumActivity extends AppCompatActivity {

    private String idUser;
    private String idGrupo;
    private String idProyecto;
    private TextView text_nombre;
    private TextView text_estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_srum);
        idUser = getIntent().getStringExtra("id");
        idGrupo = getIntent().getStringExtra("grupo");
        text_nombre = (TextView) findViewById(R.id.tv_nombre);
        text_estado = (TextView) findViewById(R.id.tv_estado);
        cargarDatos();
    }

    public void cargarDatos(){
        String URL = path + "proyecto/obtener/nombre/estado/"+idGrupo;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    idProyecto = json.getString("idProyecto");
                    text_nombre.setText("Nombre Proyecto: "+json.getString("nombre"));
                    text_estado.setText("Estado Proyecto: "+json.getString("estado"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuSrumActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void usuario(View view){
        Intent user = new Intent(this, UsuarioActivity.class);
        user.putExtra("idScrum", idUser);
        user.putExtra("idGrupo", idGrupo);
        startActivity(user);
    }

    public void cuenta(View view){
        Intent dato = new Intent(this, ScrumActivity.class);
        dato.putExtra("bandera", "2");
        dato.putExtra("idScrum", idUser);
        startActivity(dato);
    }

    public void tarea(View view){
        Intent sprint = new Intent(this, SprintActivity.class);
        sprint.putExtra("idScrum", idUser);
        startActivity(sprint);
    }

    public void misTarea(View view){
        Intent sprint = new Intent(this, SprintUsuarioActivity.class);
        sprint.putExtra("idUser", idUser);
        startActivity(sprint);
    }
}
