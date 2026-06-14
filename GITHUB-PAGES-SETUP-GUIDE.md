# Guía: Configuración de GitHub Pages con Dokka

## Índice

1. [Resumen](#1-resumen)
2. [Prerequisitos](#2-prerequisitos)
3. [Configuración en GitHub](#3-configuración-en-github)
4. [Generar documentación Dokka](#4-generar-documentación-dokka)
5. [Despliegue automático](#5-despliegue-automático)
6. [Solución de Problemas](#6-solución-de-problemas)

---

## 1. Resumen

Esta guía explica cómo desplegar la documentación generada con [Dokka](https://kotlinlang.org/docs/dokka-get-started.html) en **GitHub Pages** usando un workflow automatizado.

La documentación se genera localmente con `./gradlew dokkaHtml` y se despliega automáticamente al hacer push a `main`.

---

## 2. Prerequisitos

- [Android Studio](https://developer.android.com/studio) instalado
- Proyecto clonado localmente
- Git configurado con acceso al repositorio

---

## 3. Configuración en GitHub

### 3.1. Habilitar GitHub Pages

1. Ve a tu repositorio en GitHub
2. **Settings** → **Pages**
3. En **Source**, selecciona: **"GitHub Actions"**
4. No es necesario guardar, se aplica automáticamente

> **Nota:** El source debe ser "GitHub Actions" (no "Deploy from a branch") porque el workflow usa `actions/deploy-pages@v4`.

---

## 4. Generar documentación Dokka

**Paso obligatorio antes del despliegue.** La documentación debe existir en la carpeta `docs/` antes de hacer push.

### 4.1. Generar localmente

```bash
# Ejecutar desde la raíz del proyecto
./gradlew dokkaHtml
```

Esto crea la documentación HTML en `docs/`.

### 4.2. Verificar la generación

```bash
# Comprobar que existen los archivos
ls docs/
```

Deberías ver:
```
index.html
navigation.html
app/
images/
scripts/
styles/
```

### 4.3. Subir la documentación

```bash
git add docs/
git commit -m "docs: generate Dokka documentation"
git push origin main
```

---

## 5. Despliegue automático

Al hacer push a `main`, el workflow `deploy-docs.yml`:

1. **Checkout** del repositorio
2. **Configura** GitHub Pages
3. **Sube** la carpeta `docs/` como artifact
4. **Despliega** a GitHub Pages

### 5.1. Verificar el despliegue

1. Ve a **GitHub** → **Actions**
2. Busca el workflow **"Deploy Documentation to GitHub Pages"**
3. Espera a que termine (icono verde ✓)
4. La URL aparecerá en la sección **Pages** de Settings

### 5.2. URL de la documentación

```
https://<usuario>.github.io/<nombre-repositorio>/
```

Ejemplo: `https://moronlu18.github.io/HelloWorld/`

---

## 6. Solución de Problemas

### Error: "Validation Failed (422)"

**Causa:** GitHub Pages no está habilitado o mal configurado.

**Solución:**
1. Settings → Pages
2. Source: **"GitHub Actions"** (no "Deploy from a branch")

### Documentación no se actualiza

**Causa:** No se generó Dokka antes del push.

**Solución:**
```bash
./gradlew dokkaHtml
git add docs/
git commit -m "docs: update Dokka documentation"
git push origin main
```

### Workflow no aparece en Actions

**Causa:** El archivo `.github/workflows/deploy-docs.yml` no existe o tiene errores de YAML.

**Solución:**
1. Verificar que el archivo existe
2. Comprobar la sintaxis YAML
3. Hacer push de nuevo

---

## Comandos Útiles

```bash
# Generar documentación Dokka
./gradlew dokkaHtml

# Verificar documentación generada
ls docs/

# Subir cambios
git add docs/
git commit -m "docs: update Dokka documentation"
git push origin main

# Ver estado de GitHub Pages
# → GitHub → Settings → Pages
```

---

**Autor:** Lourdes Rodríguez Morón
**Fecha:** Junio 2026
