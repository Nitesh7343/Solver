import { validateCube } from "../../cube/validateCube";

const COLORS = [
  {
    face: "U",
    name: "White",
    color: "#ffffff",
  },
  {
    face: "R",
    name: "Red",
    color: "#ff0000",
  },
  {
    face: "F",
    name: "Green",
    color: "#00aa00",
  },
  {
    face: "D",
    name: "Yellow",
    color: "#ffff00",
  },
  {
    face: "L",
    name: "Orange",
    color: "#ff8800",
  },
  {
    face: "B",
    name: "Blue",
    color: "#0066ff",
  },
];

function CubeValidation({ cubeState }) {
  const result = validateCube(cubeState);

  return (
    <div className="cube-validation">
      <h3>Cube check</h3>

      <div className="color-counts">
        {COLORS.map((item) => {
          const count =
            result.counts[item.face] || 0;

          const valid = count === 9;

          return (
            <div
              key={item.face}
              className="color-count"
            >
              <span
                className="validation-color"
                style={{
                  backgroundColor: item.color,
                }}
              />

              <span>
                {item.name}
              </span>

              <strong>
                {count}/9
              </strong>

              <span>
                {valid ? "✓" : "✗"}
              </span>
            </div>
          );
        })}
      </div>

      {result.valid ? (
        <p className="validation-success">
          ✓ Sticker counts and pieces look consistent. The solver will verify orientation.
        </p>
      ) : (
        <div className="validation-errors">
          {result.errors.map(
            (error, index) => (
              <p key={index}>
                {error}
              </p>
            )
          )}
        </div>
      )}
    </div>
  );
}

export default CubeValidation;
