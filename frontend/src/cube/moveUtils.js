export function getInverseMove(move) {
  if (move.endsWith("'")) {
    return move[0];
  }

  if (move.endsWith("2")) {
    return move;
  }

  return move + "'";
}