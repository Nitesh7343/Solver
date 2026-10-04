export const CUBIE_POSITIONS = [];

for (let x = -1; x <= 1; x++) {
  for (let y = -1; y <= 1; y++) {
    for (let z = -1; z <= 1; z++) {
      CUBIE_POSITIONS.push({ x, y, z });
    }
  }
}