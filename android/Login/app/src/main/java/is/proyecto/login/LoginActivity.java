package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    private EditText mUserView;
    private EditText mPasswordView;
    private String URL = "http://192.168.0.4:8080/Proyecto_IS/webresources/proyecto.entities.usuario/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    public void login(View view) {
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("nombreUsuario", user);
        params.put("contrasenha", password);
        JSONObject json = new JSONObject(params);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, URL, json, this, this);
        queue.add(solicitud);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String id = response.getString("idUsuario");
            int rol = response.getInt("rol");
            if(rol == 1){
                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent menu = new Intent(this, MenuSrumActivity.class);
                menu.putExtra("id", id);
                startActivity(menu);
            }else if(rol > 1){
                Toast.makeText(LoginActivity.this, "Este usuario no es un Scrum Master", Toast.LENGTH_SHORT).show();
            }else if(rol == 0){
                Toast.makeText(LoginActivity.this, "Este usuario no existe", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(LoginActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
    }

    public void registrarScrum(View view) {
        Intent scrum = new Intent(this, ScrumActivity.class);
        scrum.putExtra("bandera", "1");
        startActivity(scrum);
    }
}