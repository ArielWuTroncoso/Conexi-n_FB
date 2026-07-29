import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// outDir se controla con la variable BUILD_TARGET:
//   - "spring" (usada por `npm run build:spring`) empaqueta la SPA dentro del backend
//     para desplegar un único JAR con `./mvnw clean package`.
//   - cualquier otro valor (default de `npm run build`) genera `dist/`, el formato
//     que esperan Render (Static Site), Vercel y Netlify al desplegar el frontend
//     como sitio estático independiente.
const outDir = process.env.BUILD_TARGET === 'spring' ? 'src/main/resources/static' : 'dist';

export default defineConfig({
  plugins: [react()],
  build: {
    outDir,
    emptyOutDir: true,
  },
  server: {
    port: 5173,
    proxy: {
      '/auth': { target: 'http://localhost:8080', changeOrigin: true },
      '/usuarios': { target: 'http://localhost:8080', changeOrigin: true },
    },
  },
});
