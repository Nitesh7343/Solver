const COLORS = [
  {
    face: "U",
    name: "White",
    color: "#ffffff",
  },
  {
    face: "D",
    name: "Yellow",
    color: "#ffff00",
  },
  {
    face: "F",
    name: "Green",
    color: "#00aa00",
  },
  {
    face: "R",
    name: "Red",
    color: "#ff0000",
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

function ColorPicker({
  selectedColor,
  onColorSelect,
}) {
  return (
    <div className="color-picker">
      <h3>Select Color</h3>

      <div className="color-options">
        {COLORS.map((item) => (
          <button
            key={item.face}
            onClick={() =>
              onColorSelect(item.face)
            }
            className={
              selectedColor === item.face
                ? "color-button selected"
                : "color-button"
            }
          >
            <span
              className="color-circle"
              style={{
                backgroundColor: item.color,
              }}
            />

            {item.name}
          </button>
        ))}
      </div>

      <p>
        Selected: <strong>{selectedColor}</strong>
      </p>
    </div>
  );
}

export default ColorPicker;