package is.proyecto.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuSrumActivity extends AppCompatActivity {

    private String id;
    private String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_srum);
        id = getIntent().getStringExtra("id");
        grupo = getIntent().getStringExtra("grupo");
    }

    public void usuario(View view){
        Intent user = new Intent(this, UsuarioActivity.class);
        user.putExtra("idScrum", id);
        user.putExtra("idGrupo", grupo);
        startActivity(user);
    }

    public void cuenta(View view){
        Intent dato = new Intent(this, ScrumActivity.class);
        dato.putExtra("bandera", "2");
        dato.putExtra("idScrum", id);
        startActivity(dato);
    }

    public void tarea(View view){
        Intent sprint = new Intent(this, SprintActivity.class);
        sprint.putExtra("idScrum", id);
        startActivity(sprint);
    }
}
