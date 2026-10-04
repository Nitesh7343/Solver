import { FACES } from "./notation";

export function parseCubeState(kociembaString) {
  if (!kociembaString || kociembaString.length !== 54) {
    throw new Error("Invalid Kociemba cube state");
  }

  const state = {};

  FACES.forEach((face, faceIndex) => {
    const start = faceIndex * 9;

    state[face] = kociembaString
      .substring(start, start + 9)
      .split("");
  });

  return state;
}

export function getStickerColor(cubeState, face, index) {
  return cubeState[face][index];
}

/*
Returns the Kociemba sticker index
for a particular cubie's face.
*/

export function getFaceIndex(face, x, y, z) {

  if (face === "U") {
    /*
       U face viewed from above

       0 1 2
       3 4 5
       6 7 8

       Front is toward the bottom of
       the U face.
    */

    const row = 1 - z;
    const col = x + 1;

    return row * 3 + col;
  }

  if (face === "D") {

    /*
       D face viewed from below.

       We reverse the Z direction because
       the face is viewed from underneath.
    */

    const row = z + 1;
    const col = x + 1;

    return row * 3 + col;
  }

  if (face === "F") {

    const row = 1 - y;
    const col = x + 1;

    return row * 3 + col;
  }

  if (face === "B") {

    const row = 1 - y;
    const col = 1 - x;

    return row * 3 + col;
  }

  if (face === "R") {

    const row = 1 - y;
    const col = 1 - z;

    return row * 3 + col;
  }

  if (face === "L") {

    const row = 1 - y;
    const col = z + 1;

    return row * 3 + col;
  }

  throw new Error(`Invalid face: ${face}`);
}