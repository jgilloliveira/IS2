package is.proyecto.login;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static is.proyecto.login.Path.path;

public class MenuMiembroEquipoActivity extends AppCompatActivity {

    private String idUser;
    private String idGrupo;
    private String idProyecto;
    private String rol;
    private TextView text_nombre;
    private TextView text_estado;
    private ArrayList<String> idSprints;
    private ArrayList<String> nombresSprints;
    private ArrayList<String> fechasSprints;
    private Date date;
    private String fecha;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_miembro_equipo);
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        idUser = getIntent().getStringExtra("id");
        idGrupo = getIntent().getStringExtra("grupo");
        rol = getIntent().getStringExtra("rol");
        text_nombre = (TextView) findViewById(R.id.tv_nombre);
        text_estado = (TextView) findViewById(R.id.tv_estado);
        idSprints = new ArrayList<String>();
        nombresSprints = new ArrayList<String>();
        fechasSprints = new ArrayList<String>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
        date = new Date();
        fecha = new SimpleDateFormat("yyyy-MM-dd").format(date);
        obtenerSprint();
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
                Toast.makeText(MenuMiembroEquipoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void obtenerSprint(){
        String URL = path + "sprint/listar/fecha/"+idUser;
        RequestQueue queue = Volley.newRequestQueue(this);
        idSprints.clear();
        nombresSprints.clear();
        fechasSprints.clear();
        StringRequest solicitud = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i=0;i<=jsonArray.length();i++){
                        JSONObject json = new JSONObject(jsonArray.get(i).toString());
                        idSprints.add(json.getString("idSprint"));
                        nombresSprints.add(json.getString("nombre"));
                        fechasSprints.add(json.getString("fechaFin"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                verificarFecha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuMiembroEquipoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(solicitud);
    }

    public void verificarFecha() {
        String[] fechaActual = fecha.split("-");
        String añoActual = fechaActual[0];
        String mesActual = fechaActual[1];
        String diaActual = fechaActual[2];
        for (int i=0;i<fechasSprints.size();i++){
            String[] fechaFin = fechasSprints.get(i).split("-");
            String añoFin = fechaFin[0];
            String mesFin = fechaFin[1];
            String diaFin = fechaFin[2];
            if(añoActual.equals(añoFin)){
                if(mesActual.equals(mesFin)){
                    int diaA = Integer.parseInt(diaActual);
                    int diaF = Integer.parseInt(diaFin);
                    if(diaA == diaF-1){
                        notificacion(nombresSprints.get(i), idSprints.get(i));
                    }
                }else{
                    if(diaFin.equals("01")){
                        if(diaActual.equals("31")){
                            if(
                                    mesActual.equals("01") && mesFin.equals("02") ||
                                            mesActual.equals("03") && mesFin.equals("04") ||
                                            mesActual.equals("05") && mesFin.equals("06") ||
                                            mesActual.equals("07") && mesFin.equals("08") ||
                                            mesActual.equals("08") && mesFin.equals("09") ||
                                            mesActual.equals("10") && mesFin.equals("11")
                                    ){
                                notificacion(nombresSprints.get(i), idSprints.get(i));
                            }
                        }else if(diaActual.equals("30")){
                            if(
                                    mesActual.equals("04") && mesFin.equals("05") ||
                                            mesActual.equals("06") && mesFin.equals("07") ||
                                            mesActual.equals("09") && mesFin.equals("10") ||
                                            mesActual.equals("11") && mesFin.equals("12")
                                    ){
                                notificacion(nombresSprints.get(i), idSprints.get(i));
                            }
                        }else if(diaActual.equals("28")){
                            if(mesActual.equals("02") && mesFin.equals("03")){
                                notificacion(nombresSprints.get(i), idSprints.get(i));
                            }
                        }
                    }
                }
            }else {
                int añoA = Integer.parseInt(añoActual);
                int añoF = Integer.parseInt(añoFin);
                if(
                        diaFin.equals("01") &&
                                mesFin.equals("01") &&
                                diaActual.equals("31") &&
                                mesActual.equals("12") &&
                                añoA == (añoF-1)
                        ){
                    notificacion(nombresSprints.get(i), idSprints.get(i));
                }
            }
        }
    }

    public void notificacion(String nombreSprint, String idSprint){
        notification.setSmallIcon(R.drawable.ic_stat_name);
        notification.setTicker("Esta es la notificacion");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Fecha Fin Próxima");
        notification.setContentText(nombreSprint);
        notification.setVibrate(new long[] {100, 250, 100, 500});
        Intent intent = new Intent(this, USUsuarioActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("idSprint", idSprint);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }

    public void cuenta(View view){
        Intent dato = new Intent(this, CuentaActivity.class);
        dato.putExtra("bandera", "2");
        dato.putExtra("idUser", idUser);
        dato.putExtra("idGrupo", idGrupo);
        dato.putExtra("rol", rol);
        dato.putExtra("ban", "2");
        startActivity(dato);
        finish();
    }

    public void misTarea(View view){
        Intent sprint = new Intent(this, SprintUsuarioActivity.class);
        sprint.putExtra("idUser", idUser);
        startActivity(sprint);
    }

    public void visualizarTarea(View view){
        Intent sprint = new Intent(this, SprintProyectoActivity.class);
        sprint.putExtra("idProyecto", idProyecto);
        startActivity(sprint);
    }

    public void cerrarSesion(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}
