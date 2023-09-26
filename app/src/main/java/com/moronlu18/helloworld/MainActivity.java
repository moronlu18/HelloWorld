package com.moronlu18.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Lourdes Rodríguez Morón
 * @version 1.0
 * <br />Aplicación que muestra el típico ejemplo de Hola Mundo
 * <ol>
 *     <li>Se ha visto cómo se crean los recursos en XML</li>
 *     <li>Se ha instanciado en Java un objeto TextView</li>
 *     <li>Se ha personalizado la imagen de la aplicación</li>
 * </ol>
 */
//TODO Tengo que mirar el temario explicado en clase sobre las Activity
public class MainActivity extends AppCompatActivity {
    private TextView tvMessageStart;
    private TextView tvMessageEnd;

    //Método que se llama en la creación de una Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessageStart=findViewById(R.id.tvMessageStart);
        tvMessageEnd=findViewById(R.id.tvMessageEnd);
        tvMessageStart.setTextColor(getColor(R.color.white));
        tvMessageEnd.setText(R.string.messageOptimist);

    }
}