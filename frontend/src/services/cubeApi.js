// Override this in .env.local when the backend is hosted elsewhere.
// Example: VITE_API_BASE_URL=https://api.example.com/api/cube
const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL ?? "http://localhost:9092/api/cube";

async function request(path, options = {}) {
  let response;

  try {
    response = await fetch(`${API_BASE_URL}${path}`, options);
  } catch {
    throw new Error(
      "Could not reach the cube server. Make sure the backend is running."
    );
  }

  if (!response.ok) {
    let message = "The cube server could not complete that request.";

    try {
      const body = await response.json();
      message = body.message || body.error || message;
    } catch {
      // A response body is optional for error responses.
    }

    throw new Error(message);
  }

  return response.json();
}

export async function scrambleCube() {
  return request("/scramble", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  });

}

export async function applyMove(cubeState, move) {
  return request("/move", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      cubeState,
      move,
    }),
  });

}

export async function solveCube(cubeState) {
  return request("/solve", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      cubeState,
    }),
  });

}
