# Guía: Configuración de Wiki con GitHub Actions

> **Ver también:** [README.md](README.md) | [GITHUB-PAGES-SETUP-GUIDE.md](GITHUB-PAGES-SETUP-GUIDE.md)

## Índice

1. [Resumen](#1-resumen)
2. [Prerequisitos](#2-prerequisitos)
3. [Configuración en GitHub](#3-configuración-en-github)
4. [Workflow de sincronización](#4-workflow-de-sincronización)
5. [Estructura generada](#5-estructura-generada)
6. [Solución de Problemas](#6-solución-de-problemas)

---

## 1. Resumen

Automatización que sincroniza el [`README.md`](README.md) con la **Wiki de GitHub**:
- Crea `Home.md` desde el README
- Genera `_Sidebar.md` con los headings del README
- Copia imágenes de la carpeta `img/`

---

## 2. Prerequisitos

- Repositorio en GitHub con el [`README.md`](README.md) actualizado
- Permisos de administrador en el repositorio
- Wiki habilitada (ver paso 3.1)

---

## 3. Configuración en GitHub

### 3.1. Habilitar Wiki

1. Ve a tu repositorio en GitHub
2. **Settings** → **General**
3. **Features** → Marcar **Wikis**
4. **Save**

> **Nota:** La wiki debe tener al menos una página creada desde el navegador para que el endpoint `.wiki.git` esté disponible.

### 3.2. Crear Token de Acceso Personal (PAT)

1. Ve a https://github.com/settings/tokens
2. **Generate new token (classic)**
3. Nombre: `Wiki Sync Token`
4. Expiración: según tu preferencia
5. Permisos: **`repo`** (acceso completo al repositorio)
6. **Generate token**
7. **Copia el token** (solo se muestra una vez)

### 3.3. Crear Secreto en el Repositorio

1. Ve a tu repositorio en GitHub
2. **Settings** → **Secrets and variables** → **Actions**
3. **New repository secret**
4. Nombre: `WIKI_TOKEN`
5. Valor: el token copiado en el paso anterior
6. **Add secret**

---

## 4. Workflow de sincronización

El workflow `sync-wiki.yml` se ejecuta automáticamente cuando:
- Se hace push a `main` con cambios en [`README.md`](README.md)
- Se ejecuta manualmente desde **Actions**

### 4.1. Contenido del workflow

**Archivo:** `.github/workflows/sync-wiki.yml`

```yaml
name: Sync README to Wiki

on:
  push:
    branches: ["main"]
    paths:
      - 'README.md'
  workflow_dispatch:

permissions:
  contents: read

jobs:
  update-wiki:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Generate wiki pages
        run: |
          mkdir -p wiki-content
          mkdir -p wiki-content/img
          
          cp README.md wiki-content/Home.md
          
          if [ -d "img" ]; then
            cp -r img/* wiki-content/img/ 2>/dev/null || true
          fi
          
          # Create _Sidebar.md from README headings
          SIDEBAR_CONTENT="Home"
          while IFS= read -r line; do
            heading=$(echo "$line" | sed 's/^#* *//')
            SIDEBAR_CONTENT+=$'\n'"${heading}"
          done < <(grep -E "^#{1,3} " README.md)
          
          echo "$SIDEBAR_CONTENT" > wiki-content/_Sidebar.md

      - name: Push to Wiki
        env:
          WIKI_TOKEN: ${{ secrets.WIKI_TOKEN }}
        run: |
          if [ -z "$WIKI_TOKEN" ]; then
            echo "Error: WIKI_TOKEN secret not configured"
            exit 1
          fi
          
          echo "=== Setting up git ==="
          git config --global user.name "github-actions[bot]"
          git config --global user.email "github-actions[bot]@users.noreply.github.com"
          git config --global credential.helper store
          
          echo "https://${WIKI_TOKEN}@github.com" > ~/.git-credentials
          
          echo "=== Cloning wiki ==="
          git clone https://github.com/${{ github.repository }}.wiki.git wiki-repo
          cd wiki-repo
          
          # Delete everything
          git rm -rf . 2>/dev/null || true
          git commit -m "cleanup" 2>/dev/null || true
          
          # Copy new content
          cp -r ../wiki-content/* .
          
          echo "=== Files to upload ==="
          ls -la
          
          echo "=== Pushing to wiki ==="
          git add .
          git commit -m "docs: update wiki"
          git push
```

---

## 5. Estructura generada

### 5.1. Archivos en la Wiki

| Archivo | Contenido |
|---------|-----------|
| `Home.md` | Contenido completo del [`README.md`](README.md) |
| `_Sidebar.md` | Menú lateral con los headings del README |
| `img/` | Imágenes copiadas del repositorio |

### 5.2. Sidebar generado

El sidebar se genera automáticamente a partir de los headings del [`README.md`](README.md):

```markdown
Home
Contenidos Aprendidos
Instalación y configuración
Ejecución
Estructura del proyecto
Documentación
GitHub Actions
Imagen de la Aplicación
Autora
Versión
Licencia
```

---

## 6. Solución de Problemas

### Error: "WIKI_TOKEN not configured"

**Causa:** El secreto `WIKI_TOKEN` no existe o está mal configurado.

**Solución:**
1. Verificar que el token existe en **Settings → Secrets and variables → Actions**
2. Verificar que el nombre del secreto es exactamente `WIKI_TOKEN`
3. Generar un nuevo token si expiró

### Error: "Repository not found"

**Causa:** La wiki no está habilitada o no tiene páginas creadas.

**Solución:**
1. Habilitar Wiki en Settings → General → Features
2. Crear al menos una página desde el navegador
3. Verificar que el token tiene permiso **`repo`**

### Error: "Permission denied"

**Causa:** El token no tiene permisos suficientes.

**Solución:**
1. Verificar que el token tiene permiso **`repo`**
2. Generar un nuevo token con los permisos correctos

### Wiki no se actualiza

**Causa:** El workflow no se ejecuta o falla silenciosamente.

**Solución:**
1. Ve a **Actions** en el repositorio
2. Busca el workflow **"Sync README to Wiki"**
3. Revisa los logs del último intento
4. Ejecuta el workflow manualmente con **"Run workflow"**

### Sidebar muestra contenido incorrecto

**Causa:** El [`README.md`](README.md) tiene un formato inesperado.

**Solución:**
1. Verificar que los headings del README usan `#`, `##` o `###`
2. No usar headings más profundos (####) para el sidebar

### Imágenes no aparecen en la Wiki

**Causa:** La carpeta `img/` no existe o está vacía.

**Solución:**
1. Verificar que las imágenes están en `img/`
2. Verificar que el [`README.md`](README.md) referencia las imágenes correctamente

---

## Comandos Útiles

```bash
# Verificar estado del repositorio
git status

# Subir cambios del README
git add README.md
git commit -m "docs: update README"
git push origin main

# Ejecutar workflow manualmente
# → GitHub → Actions → Sync README to Wiki → Run workflow
```

---

**Autor:** Lourdes Rodríguez Morón
**Fecha:** Junio 2026

> **Documentación relacionada:**
> - [README.md](README.md) - Documentación principal del proyecto
> - [GITHUB-PAGES-SETUP-GUIDE.md](GITHUB-PAGES-SETUP-GUIDE.md) - Guía para GitHub Pages con Dokka
