const FACES = ["U", "R", "F", "D", "L", "B"];

const COLOR_NAMES = {
  U: "White", R: "Red", F: "Green", D: "Yellow", L: "Orange", B: "Blue",
};

// Sticker positions follow the Kociemba facelet order: U, R, F, D, L, B.
const CORNERS = [
  ["U8", "R0", "F2"], ["U6", "F0", "L2"], ["U0", "L0", "B2"], ["U2", "B0", "R2"],
  ["D2", "F8", "R6"], ["D0", "L8", "F6"], ["D6", "B8", "L6"], ["D8", "R8", "B6"],
];

const EDGES = [
  ["U5", "R1"], ["U7", "F1"], ["U3", "L1"], ["U1", "B1"],
  ["D5", "R7"], ["D1", "F7"], ["D3", "L7"], ["D7", "B7"],
  ["F5", "R3"], ["F3", "L5"], ["B5", "L3"], ["B3", "R5"],
];

function stickerAt(state, sticker) {
  const face = sticker[0];
  const index = Number(sticker.slice(1));
  return state[FACES.indexOf(face) * 9 + index];
}

function pieceKey(colors) {
  return [...colors].sort().join("");
}

function validatePieceSet(state, positions, label) {
  const expected = positions.map((piece) => pieceKey(piece.map((sticker) => sticker[0])));
  const seen = positions.map((piece) => pieceKey(piece.map((sticker) => stickerAt(state, sticker))));
  const duplicates = [...new Set(seen.filter((piece, index) => seen.indexOf(piece) !== index))];
  const missing = expected.filter((piece) => !seen.includes(piece));

  const errors = [];
  if (duplicates.length) errors.push(`Duplicate ${label} piece${duplicates.length > 1 ? "s" : ""}: ${duplicates.join(", ")}.`);
  if (missing.length) errors.push(`Missing ${label} piece${missing.length > 1 ? "s" : ""}: ${missing.join(", ")}.`);

  return { errors };
}

export function validateCube(cubeState) {
  const errors = [];
  const counts = { U: 0, R: 0, F: 0, D: 0, L: 0, B: 0 };

  if (!cubeState || cubeState.length !== 54) {
    return { valid: false, errors: ["Cube state must contain exactly 54 stickers."], counts: {} };
  }

  for (const sticker of cubeState) {
    if (!(sticker in counts)) errors.push(`Invalid color: ${sticker}`);
    else counts[sticker] += 1;
  }

  FACES.forEach((face) => {
    if (counts[face] !== 9) errors.push(`${COLOR_NAMES[face]} must have exactly 9 stickers. Found ${counts[face]}.`);
  });

  FACES.forEach((face, faceIndex) => {
    if (cubeState[faceIndex * 9 + 4] !== face) errors.push(`${COLOR_NAMES[face]} center is incorrect.`);
  });

  if (errors.length) return { valid: false, errors, counts };

  const corners = validatePieceSet(cubeState, CORNERS, "corner");
  const edges = validatePieceSet(cubeState, EDGES, "edge");
  errors.push(...corners.errors, ...edges.errors);

  return { valid: errors.length === 0, errors, counts };
}
