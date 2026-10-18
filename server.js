import http from 'http';
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const PORT = 3000;
const APK_PATH = path.join(__dirname, 'app/build/outputs/apk/debug/app-debug.apk');

// Read index.html or fallback
let htmlContent = '';
try {
  htmlContent = fs.readFileSync(path.join(__dirname, 'index.html'), 'utf8');
} catch (e) {
  htmlContent = '<h1>Warkari Dev Server</h1>';
}

const server = http.createServer((req, res) => {
  const url = new URL(req.url, `http://${req.headers.host || 'localhost:3000'}`);

  // Handle APK Download
  if (url.pathname === '/download-apk') {
    if (fs.existsSync(APK_PATH)) {
      const stat = fs.statSync(APK_PATH);
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Length': stat.size,
        'Content-Disposition': 'attachment; filename="warkari-app-debug.apk"',
      });
      const stream = fs.createReadStream(APK_PATH);
      stream.pipe(res);
      return;
    } else {
      res.writeHead(404, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify({ error: 'APK not found. Run ./gradlew assembleDebug.' }));
      return;
    }
  }

  // APK info API
  if (url.pathname === '/api/apk-info') {
    const exists = fs.existsSync(APK_PATH);
    const size = exists ? (fs.statSync(APK_PATH).size / (1024 * 1024)).toFixed(1) + ' MB' : '0 MB';
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({
      exists,
      size,
      version: '1.0.0',
      packageName: 'com.example',
      appName: 'Warkari (वारकरी)',
      path: 'app/build/outputs/apk/debug/app-debug.apk',
      lastBuilt: exists ? fs.statSync(APK_PATH).mtime.toISOString() : null
    }));
    return;
  }

  // Health check
  if (url.pathname === '/health') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ status: 'ok', time: new Date().toISOString() }));
    return;
  }

  // Reload index.html on each request for fresh content during development
  try {
    const freshHtml = fs.readFileSync(path.join(__dirname, 'index.html'), 'utf8');
    res.writeHead(200, {
      'Content-Type': 'text/html; charset=utf-8',
      'Cache-Control': 'no-cache, no-store, must-revalidate',
    });
    res.end(freshHtml);
  } catch (err) {
    res.writeHead(500, { 'Content-Type': 'text/plain' });
    res.end('Error loading application: ' + err.message);
  }
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`[Warkari Dev Server] Listening on http://0.0.0.0:${PORT}`);
});
