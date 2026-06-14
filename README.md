# Hello World App

## Contenidos Aprendidos

En este proyecto, se han cubierto los siguientes conceptos clave del desarrollo en Android:

1. **Añadir dependencias al proyecto**: Se aprendió a gestionar las dependencias necesarias en el archivo `build.gradle` para incorporar bibliotecas externas y funcionalidades adicionales en el proyecto.

2. **Crear un layout en XML**: Se diseñó una interfaz de usuario en XML que incluye dos elementos de texto y un botón. Se trabajó con las vistas ([TextView](https://developer.android.com/reference/android/widget/TextView?hl=en) y [Button](https://developer.android.com/reference/android/widget/Button?hl=en)) y se estructuró el layout utilizando diferentes atributos y restricciones para garantizar una interfaz amigable para el usuario.

3. **Sincronizar el proyecto con Firebase**: Se integró Firebase al proyecto para habilitar diferentes servicios como Analytics, Firestore, o autenticación de usuarios. Incluye la configuración de dependencias y la sincronización del proyecto con Firebase.

4. **Crear una excepción con `throws`**: Se abordó el manejo de excepciones mediante el uso de `throws`. Este enfoque se utilizó para indicar que un método específico podría lanzar una excepción, mejorando la gestión de errores en la aplicación.

5. **Utilizar el registro de sucesos LogCat**: Se utilizó `LogCat` para registrar sucesos durante la ejecución de la aplicación, ayudando en el proceso de depuración y monitoreo del comportamiento de la aplicación. Se aprendió a utilizar los niveles de log como [Log.d](https://developer.android.com/reference/android/util/Log?hl=en#d(java.lang.String,%20java.lang.String)), [Log.e](https://developer.android.com/reference/android/util/Log?hl=en#e(java.lang.String,%20java.lang.String)), [Log.i](https://developer.android.com/reference/android/util/Log?hl=en#i(java.lang.String,%20java.lang.String)), entre otros, para proporcionar diferentes tipos de información.

6. **Generar la documentación en formato HTML**: Se generó documentación del código utilizando [Dokka](https://kotlinlang.org/docs/dokka-get-started.html), la herramienta de documentación para Kotlin. La documentación se encuentra en la carpeta `docs/` y contiene enlaces internos, enlaces externos a [Android Developers](https://developer.android.com) y utiliza formato Markdown.

7. **Inicialización tardía (`lateinit`) y perezosa (`by lazy`)**: Se estudió el uso de `lateinit` y `by lazy` en Kotlin. `lateinit` permite inicializar propiedades no nulas después de la declaración, mientras que `by lazy` permite la inicialización perezosa de una propiedad solo cuando se utiliza por primera vez, optimizando así el uso de recursos.


## Instalación y configuración
Para ejecutar este proyecto, necesitarás:

1. Android Studio.
2. Clonar este repositorio en tu máquina local usando `git clone URL_DEL_REPOSITORIO`.
3. Abrir el proyecto en Android Studio.

## Ejecución
Para ejecutar la aplicación en un emulador o dispositivo físico:

1. Abre el proyecto en Android Studio.
2. Ejecuta el proyecto usando 'Run > Run 'app''.
3. La aplicación debería iniciar en el dispositivo o emulador seleccionado mostrando los mensajes configurados.

## Estructura del proyecto
El proyecto contiene las siguientes partes clave:

- `MainActivity.kt`: Clase que extiende `AppCompatActivity` y configura los mensajes de `TextView`.
- `activity_main.xml`: Archivo XML que define la UI de la aplicación.
- `colors.xml` y `strings.xml`: Archivos de recursos que definen los colores y cadenas utilizadas en la aplicación.
- `docs/`: Documentación generada por Dokka en formato HTML.

## Documentación
La documentación del proyecto se ha generado con [Dokka](https://kotlinlang.org/docs/dokka-get-started.html) y está disponible en `docs/`. Para regenerar la documentación:

```bash
./gradlew dokkaHtml
```

La documentación incluye:
- Etiquetas HTML para estructura
- Enlaces internos entre clases y métodos
- Enlaces externos a [Android Developers](https://developer.android.com)
- Formato Markdown para estilos

## GitHub Actions

El proyecto incluye dos workflows automatizados:

### Despliegue a GitHub Pages
- **Archivo:** `.github/workflows/deploy-docs.yml`
- **Trigger:** Push a `main`
- **Función:** Despliega la documentación Dokka a GitHub Pages
- **URL:** https://moronlu18.github.io/HelloWorld/
- **Requisito:** Ejecutar `./gradlew dokkaHtml` antes de hacer push

### Sincronización con Wiki
- **Archivo:** `.github/workflows/sync-wiki.yml`
- **Trigger:** Cambios en `README.md`
- **Función:** Sincroniza el README con la Wiki de GitHub
- **Genera:**
  - `Home.md` desde el README
  - `_Sidebar.md` con los headings (H1, H2, H3)
  - Copia imágenes de `img/`

### Configuración Requerida

1. **GitHub Pages:** Settings → Pages → Source: **"GitHub Actions"**
2. **Wiki:** Settings → General → Features → Marcar Wikis
3. **Token:** Crear PAT con permiso `repo` y guardar como secreto `WIKI_TOKEN`

Para más detalles, consulta:
- [GITHUB-PAGES-SETUP-GUIDE.md](GITHUB-PAGES-SETUP-GUIDE.md) - Despliegue de documentación Dokka
- [WIKI-SETUP-GUIDE.md](WIKI-SETUP-GUIDE.md) - Sincronización con Wiki

## Imagen de la Aplicación

<p align="center">
  <img src="img/aplicacion.png" width="200" alt="Captura de pantalla de la aplicación" />
</p>

## Autora
 Lourdes Rodríguez Morón :tada:

## Versión
1.0

## Licencia
Este proyecto está licenciado bajo la [Licencia MIT](https://opensource.org/licenses/MIT). Consulta el archivo `LICENSE` para más detalles.
