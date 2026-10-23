const fs = require('fs');
const path = require('path');

const instance = process.argv[2];
const out = process.argv[3];

const names = {
  allthemodium: ['Allthemodium', 'Allthemodium'],
  andesite_alloy: ['Andesite Alloy', 'Aleación de andesita'],
  arcane_gold_ingot: ['Arcane Gold', 'Oro arcano'],
  black_quartz: ['Black Quartz', 'Cuarzo negro'],
  blaze_gold: ['Blaze Gold', 'Oro de blaze'],
  cajita_feliz: ['Happy Box', 'Cajita feliz'],
  celestigem: ['Celestigem', 'Gema celestial'],
  chaos_shard: ['Chaos Shard', 'Fragmento del caos'],
  chromium: ['Chromium', 'Cromo'],
  cognition: ['Cognitive Alloy', 'Aleación cognitiva'],
  crystal_matrix: ['Crystal Matrix', 'Matriz de cristal'],
  cuarzorosa: ['Rose Quartz', 'Cuarzo rosa'],
  dark_gem: ['Dark Gem', 'Gema oscura'],
  deorum_ingot: ['Deorum', 'Deorum'],
  eclipse_alloy: ['Eclipse Alloy', 'Aleación de eclipse'],
  elevatium_ingot: ['Elevatium', 'Elevatium'],
  ender_ingot: ['Ender Ingot', 'Lingote de ender'],
  entro: ['Entro', 'Entro'],
  ferricore: ['FerriCore', 'FerriCore'],
  flux_dust: ['Flux', 'Flux'],
  imperium_essence: ['Imperium Essence', 'Esencia de imperium'],
  insanium_essence: ['Insanium Essence', 'Esencia de insanium'],
  kivi: ['Kivi', 'Kivi'],
  nadienite_ingot: ['Nadienite', 'Nadienita'],
  naquadah: ['Naquadah', 'Naquadah'],
  pink_slime: ['Pink Slime', 'Slime rosa'],
  predicciondematrix: ['Prediction Matrix', 'Matriz de predicción'],
  prometheum: ['Prometheum', 'Prometeo'],
  prosperity_shard: ['Prosperity Shard', 'Fragmento de prosperidad'],
  prudentium_essence: ['Prudentium Essence', 'Esencia de prudentium'],
  redstoneingot: ['Redstone Ingot', 'Lingote de redstone'],
  sky_steel_ingot: ['Sky Steel', 'Acero celeste'],
  source_gem: ['Source Gem', 'Gema de fuente'],
  spirit_gem: ['Spirit Gem', 'Gema espiritual'],
  super_cacotas: ['Super Cacotas', 'Súper cacotas'],
  supremium_essence_esc: ['Supremium Essence', 'Esencia de supremium'],
  tertium_essence: ['Tertium Essence', 'Esencia de tertium'],
  thaumaturge_alumentum: ['Alumentum', 'Alumentum'],
  thaumaturge_amber: ['Amber', 'Ámbar'],
  thaumaturge_brass: ['Thaumaturge Brass', 'Latón taumatúrgico'],
  thaumaturge_crystal_aer: ['Aer Crystal', 'Cristal de Aer'],
  thaumaturge_crystal_aqua: ['Aqua Crystal', 'Cristal de Aqua'],
  thaumaturge_crystal_ignis: ['Ignis Crystal', 'Cristal de Ignis'],
  thaumaturge_crystal_ordo: ['Ordo Crystal', 'Cristal de Ordo'],
  thaumaturge_crystal_perditio: ['Perditio Crystal', 'Cristal de Perditio'],
  thaumaturge_crystal_terra: ['Terra Crystal', 'Cristal de Terra'],
  thaumaturge_crystal_vitium: ['Vitium Crystal', 'Cristal de Vitium'],
  thaumaturge_quicksilver: ['Quicksilver', 'Azogue'],
  thaumaturge_salis_mundus: ['Salis Mundus', 'Salis Mundus'],
  thaumaturge_thaumium: ['Thaumium', 'Taumio'],
  thaumaturge_void_metal: ['Void Metal', 'Metal del vacío'],
  unobtainium: ['Unobtainium', 'Unobtainium'],
  vibranium: ['Vibranium', 'Vibranio'],
  xychorium_gem: ['Xychorium Gem', 'Gema de xychorium'],
};

const tagMods = {
  'c:gems/black_quartz': { any: ['actuallyadditions', 'occultism'] },
  'c:storage_blocks/unobtainium': { all: ['allthemodium'] },
  'c:storage_blocks/vibranium': { all: ['allthemodium'] },
  'c:gems/xychorium': { all: ['xycraft_world'] },
};

const skipMods = new Set(['minecraft', 'mysticalagriculture', 'c']);

function shape(ref, prefix) {
  if (!ref) return null;
  const name = ref.split(':')[1].split('/').pop();
  return name.replace(prefix + '_', '');
}

const dir = path.join(instance, 'config', 'mysticalcustomization', 'crops');
const crops = [];
for (const file of fs.readdirSync(dir).sort()) {
  if (!file.endsWith('.json')) continue;
  const id = file.replace('.json', '');
  const def = JSON.parse(fs.readFileSync(path.join(dir, file), 'utf8'));
  const requires = [];
  let requiresAny = [];
  const ing = def.ingredient;
  if (ing.tag) {
    const m = tagMods[ing.tag];
    if (m.all) requires.push(...m.all);
    if (m.any) requiresAny = m.any;
  } else {
    const ns = ing.item.split(':')[0];
    if (!skipMods.has(ns)) requires.push(ns);
  }
  const tierNs = def.tier.split(':')[0];
  if (tierNs !== 'mysticalagriculture' && !requires.includes(tierNs)) requires.push(tierNs);
  const custom = !def.color;
  const entry = {
    id,
    tier: def.tier,
    type: def.type,
    ingredient: ing.tag ? { tag: ing.tag } : { item: ing.item },
    requires,
    requiresAny,
    custom,
  };
  if (!custom) {
    entry.color = def.color.replace('#', '').toLowerCase();
    entry.flower = shape(def.textures.flower, 'flower');
    entry.essence = shape(def.textures.essence, 'essence');
  }
  entry.name = { en_us: names[id][0], es_es: names[id][1] };
  crops.push(entry);
}
fs.writeFileSync(out, JSON.stringify({ crops }, null, 2) + '\n');
console.log(crops.length + ' crops');
