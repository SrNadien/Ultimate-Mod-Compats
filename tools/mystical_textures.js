const fs = require('fs');
const path = require('path');
const { readPng, writePng } = require('./mystical_png');

const catalogFile = process.argv[2];
const maTextures = process.argv[3];
const customTextures = process.argv[4];
const outRoot = process.argv[5];

const catalog = JSON.parse(fs.readFileSync(catalogFile, 'utf8'));

function hex(c) {
  return [parseInt(c.slice(0, 2), 16), parseInt(c.slice(2, 4), 16), parseInt(c.slice(4, 6), 16)];
}

function shade(rgb, f) {
  return rgb.map(v => Math.max(0, Math.min(255, Math.round(v * f))));
}

function colorize(base, original) {
  const lift = Math.max(0, 80 - Math.max(...original));
  const rgb = original.map(v => Math.min(255, v + lift));
  const { width, height, data } = base;
  const out = Buffer.alloc(data.length);
  for (let i = 0; i < width * height; i++) {
    const a = data[i * 4 + 3];
    if (a === 0) continue;
    const gray = (data[i * 4] + data[i * 4 + 1] + data[i * 4 + 2]) / 3;
    const f = gray / 185;
    const c = shade(rgb, f);
    out[i * 4] = c[0];
    out[i * 4 + 1] = c[1];
    out[i * 4 + 2] = c[2];
    out[i * 4 + 3] = a;
  }
  const edge = shade(original, 0.32);
  const res = Buffer.from(out);
  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      const i = (y * width + x) * 4;
      if (out[i + 3] !== 0) continue;
      const near = [[1, 0], [-1, 0], [0, 1], [0, -1]].some(([dx, dy]) => {
        const nx = x + dx, ny = y + dy;
        return nx >= 0 && ny >= 0 && nx < width && ny < height && out[(ny * width + nx) * 4 + 3] !== 0;
      });
      if (near) {
        res[i] = edge[0];
        res[i + 1] = edge[1];
        res[i + 2] = edge[2];
        res[i + 3] = 255;
      }
    }
  }
  return { width, height, data: res };
}

function ensure(file) {
  fs.mkdirSync(path.dirname(file), { recursive: true });
}

function write(file, img) {
  ensure(file);
  writePng(file, img);
}

function copy(src, dst) {
  ensure(dst);
  fs.copyFileSync(src, dst);
  if (fs.existsSync(src + '.mcmeta')) fs.copyFileSync(src + '.mcmeta', dst + '.mcmeta');
}

const seedsBase = readPng(path.join(maTextures, 'item', 'mystical_seeds.png'));
const essenceBase = {};
const flowerBase = {};
for (const crop of catalog.crops) {
  if (crop.custom) continue;
  if (!essenceBase[crop.essence]) essenceBase[crop.essence] = readPng(path.join(maTextures, 'item', 'essence_' + crop.essence + '.png'));
  if (!flowerBase[crop.flower]) flowerBase[crop.flower] = readPng(path.join(maTextures, 'block', 'flower_' + crop.flower + '.png'));
}

let made = 0;
for (const crop of catalog.crops) {
  const item = path.join(outRoot, 'item', 'mystical');
  const block = path.join(outRoot, 'block', 'mystical');
  if (crop.custom) {
    const name = crop.id;
    copy(path.join(customTextures, 'item', 'seeds', name + '_seeds.png'), path.join(item, name + '_seeds.png'));
    copy(path.join(customTextures, 'item', 'essence', name + '_essence.png'), path.join(item, name + '_essence.png'));
    copy(path.join(customTextures, 'block', 'flower', name + '_flower.png'), path.join(block, name + '_flower.png'));
    continue;
  }
  const rgb = hex(crop.color);
  write(path.join(item, crop.id + '_seeds.png'), colorize(seedsBase, rgb));
  write(path.join(item, crop.id + '_essence.png'), colorize(essenceBase[crop.essence], rgb));
  write(path.join(block, crop.id + '_flower.png'), colorize(flowerBase[crop.flower], rgb));
  made += 3;
}
console.log('generated ' + made + ' textures');
