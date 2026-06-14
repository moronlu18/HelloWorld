package com.moronlu18.helloworld

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * Actividad principal que muestra dos textos y un botón que lanza una excepción.
 *
 * Esta actividad demuestra conceptos básicos de desarrollo Android:
 * <ol>
 *     <li>Crear recursos en XML para la interfaz de usuario</li>
 *     <li>Instanciar y manipular objetos [TextView]</li>
 *     <li>Personalizar la imagen de la aplicación</li>
 *     <li>Crear una excepción con throws</li>
 *     <li>Utilizar el registro de sucesos LogCat</li>
 *     <li>Generar la documentación en formato html</li>
 *     <li>Inicialización tardía ([lateinit]) y perezosa (by lazy)</li>
 *     <li>A depurar con puntos de interrupción</li>
 *     <li>Crear regiones de métodos que tienen una relación</li>
 * </ol>
 *
 * Utiliza [findViewById](https://developer.android.com/reference/android/app/Activity#findViewById(int)) para acceder a las vistas del layout.
 *
 * @author Lourdes Rodríguez Morón
 * @version 1.0
 * @constructor Create empty Main activity
 * @see AppCompatActivity
 * @see TextView
 */
class MainActivity : AppCompatActivity() {

    /**
     * Etiqueta para los logs de depuración.
     */
    private val TAG = "MainActivity"

    /**
     * Mensaje que se muestra en la interfaz.
     * Se inicializa de forma tardía con [lateinit].
     */
    lateinit var message: String

    private lateinit var tvMessageStart: TextView
    private lateinit var tvMessageEnd: TextView
    private lateinit var btCrash: MaterialButton

    /**
     * Se llama cuando se crea la actividad por primera vez.
     * Inicializa la interfaz de usuario y configura los listeners de eventos.
     *
     * @param savedInstanceState Si la actividad se está recreando después de un cierre
     *                           previo, este contiene el estado guardado.
     * @throws RuntimeException Se lanza intencionalmente para probar Firebase Crashlytics.
     * @see [Documentación oficial de Android](https://developer.android.com/guide/components/activities/activity-lifecycle)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTextViewProperties()
    }

    /**
     * Configura las propiedades de los TextViews y el botón.
     * Establece los textos, colores y listeners de eventos.
     */
    private fun setTextViewProperties() {
        tvMessageStart = findViewById(R.id.tvMessageStart)
        tvMessageEnd = findViewById(R.id.tvMessageEnd)
        btCrash = findViewById(R.id.btCrash)

        message = "Hola Mundo"
        tvMessageStart.text = message
        tvMessageEnd.setText(R.string.messageOptimistic)

        btCrash.setOnClickListener {
            tvMessageEnd.text = getString(R.string.messagePessimistic)
            throw RuntimeException("Aquí hay un gran error") // Force a crash
        }

        Log.d(TAG, "MainActivity-> onCreate()")
    }

    //region Ciclo de Vida de una Activity
    /**
     * Se llama cuando la actividad se vuelve visible para el usuario.
     * Registra un mensaje en LogCat para seguimiento del ciclo de vida.
     *
     * @see [Documentación oficial de Android](https://developer.android.com/guide/components/activities/activity-lifecycle)
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity-> onStart()")
        //TODO comentari
        Log.d(TAG, "Texto del saludo: ${tvMessageStart.text}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity-> onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity-> onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity-> onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity-> onDestroy()")
    }

    //endregion
}
