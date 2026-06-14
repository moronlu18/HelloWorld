# Guía: Wiki desde README con GitHub Actions

## Índice

1. [Resumen](#1-resumen)
2. [Configuración GitHub](#2-configuración-github)
3. [Workflows](#3-workflows)
4. [Estructura](#4-estructura)
5. [Solución de Problemas](#5-solución-de-problemas)

---

## 1. Resumen

Automatización que sincroniza el `README.md` con la Wiki de GitHub:
- Crea `Home.md` desde el README
- Genera `_Sidebar.md` con los headings del README
- Copia imágenes

---

## 2. Configuración GitHub

### 2.1. Habilitar Wiki

1. Repositorio → **Settings** → **General**
2. **Features** → Marcar **Wikis**
3. Save

### 2.2. Habilitar GitHub Pages

1. **Settings** → **Pages**
2. Source: **"Deploy from a branch"**
3. Branch: **main** / Folder: **/docs**
4. Save

### 2.3. Crear Token de Acceso

1. Ve a https://github.com/settings/tokens
2. **Generate new token (classic)**
3. Nombre: `Wiki Sync Token`
4. Permisos: **`repo`**
5. Generate token → **Copiar token**

### 2.4. Crear Secreto

1. Repositorio → **Settings** → **Secrets and variables** → **Actions**
2. **New repository secret**
3. Nombre: `WIKI_TOKEN`
4. Valor: token copiado
5. **Add secret**

---

## 3. Workflows

### 3.1. deploy-docs.yml

Despliega documentación Dokka a GitHub Pages.

**Archivo:** `.github/workflows/deploy-docs.yml`

```yaml
name: Deploy Documentation to GitHub Pages

on:
  push:
    branches: ["main"]
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

concurrency:
  group: "pages"
  cancel-in-progress: false

jobs:
  deploy:
    environment:
      name: github-pages
      url: ${{ steps.deployment.outputs.page_url }}
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - name: Setup Pages
        uses: actions/configure-pages@v5
      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: './docs'
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
```

### 3.2. sync-wiki.yml

Sincroniza README con Wiki de GitHub.

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
            SIDEBAR_CONTENT="${SIDEBAR_CONTENT}
${heading}"
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
          
          WIKI_URL="https://x-access-token:${WIKI_TOKEN}@github.com/${{ github.repository }}.wiki.git"
          
          git clone "$WIKI_URL" wiki-repo
          cd wiki-repo
          
          # Delete everything
          git rm -rf . 2>/dev/null || true
          git commit -m "cleanup" 2>/dev/null || true
          
          # Copy new content
          cp -r ../wiki-content/* .
          
          git config user.name "github-actions[bot]"
          git config user.email "github-actions[bot]@users.noreply.github.com"
          git add .
          git commit -m "docs: update wiki"
          git push
```

---

## 4. Estructura

### 4.1. Repositorio

```
HelloWorldKotlin/
├── .github/
│   └── workflows/
│       ├── deploy-docs.yml
│       └── sync-wiki.yml
├── app/
│   └── build.gradle.kts
├── docs/
│   └── (documentación Dokka)
├── img/
│   └── aplicacion.png
├── .nojekyll
├── README.md
├── WIKI-SETUP-GUIDE.md
└── gradlew
```

### 4.2. Wiki Generada

| Archivo | Contenido |
|---------|-----------|
| `Home.md` | README.md completo |
| `_Sidebar.md` | Headings del README |
| `img/` | Imágenes |

---

## 5. Solución de Problemas

### Error: "Failed to deploy - Jekyll"

**Causa:** GitHub Pages usa Jekyll.

**Solución:**
1. Verificar que existe `.nojekyll`
2. Settings → Pages → Source: "Deploy from a branch" → main → /docs

### Error: "Validation Failed (422)"

**Causa:** GitHub Pages no habilitado.

**Solución:**
1. Settings → Pages
2. Habilitar y configurar Source

### Error: "WIKI_TOKEN not configured"

**Causa:** Falta el token.

**Solución:**
1. Crear PAT con permiso `repo`
2. Agregar como secreto `WIKI_TOKEN`

### Sidebar no aparece

**Causa:** Archivo corrupto o mal formateado.

**Solución:**
1. Deshabilitar y rehabilitar Wiki en Settings
2. Ejecutar workflow manualmente

---

## Comandos Útiles

```bash
# Generar documentación Dokka
./gradlew dokkaHtml

# Verificar estado
git status

# Ejecutar push
git push origin main
```

---

**Autor:** Lourdes Rodríguez Morón  
**Fecha:** Junio 2026
