import { MOVES } from "../../cube/notation";

function MoveControls({ onMove, disabled = false }) {
  return (
    <div className="move-controls">
      <h3>Manual Moves</h3>

      <div className="move-grid">
        {MOVES.map((move) => (
          <button
            key={move}
            onClick={() => onMove(move)}
            disabled={disabled}
          >
            {move}
          </button>
        ))}
      </div>
    </div>
  );
}

export default MoveControls;