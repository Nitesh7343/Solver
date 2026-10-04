import { FACES } from "./notation";

export class CubeState {
  constructor(kociembaString = null) {
    if (kociembaString) {
      this.state = kociembaString;
    } else {
      this.state = this.getSolvedState();
    }
  }

  getSolvedState() {
    return FACES.map((face) => face.repeat(9)).join("");
  }

  getKociembaString() {
    return this.state;
  }

  getFace(face) {
    const faceIndex = FACES.indexOf(face);

    if (faceIndex === -1) {
      throw new Error(`Invalid face: ${face}`);
    }

    const start = faceIndex * 9;

    return this.state.substring(start, start + 9);
  }

  isSolved() {
    return this.state === this.getSolvedState();
  }
}
