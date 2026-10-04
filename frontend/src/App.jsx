import { useCallback, useEffect, useMemo, useState } from "react";
import RubiksCube from "./components/cube/RubiksCube";
import MoveControls from "./components/controls/MoveControls";
import ColorPicker from "./components/controls/ColorPicker";
import CubeValidation from "./components/controls/CubeValidation";
import { applyMove, scrambleCube, solveCube } from "./services/cubeApi";
import { CubeState } from "./cube/CubeState";
import { getInverseMove } from "./cube/moveUtils";
import { validateCube } from "./cube/validateCube";

const solvedCubeState = new CubeState().getKociembaString();

function App() {
  const [cubeState, setCubeState] = useState(solvedCubeState);
  const [animatingMove, setAnimatingMove] = useState(null);
  const [solutionMoves, setSolutionMoves] = useState([]);
  const [currentSolutionIndex, setCurrentSolutionIndex] = useState(0);
  const [lastScramble, setLastScramble] = useState([]);
  const [isPlaying, setIsPlaying] = useState(false);
  const [isSolved, setIsSolved] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [selectedColor, setSelectedColor] = useState("U");
  const [pendingAction, setPendingAction] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const solutionFinished = solutionMoves.length > 0 && currentSolutionIndex >= solutionMoves.length;
  const interactionLocked = Boolean(animatingMove || pendingAction);
  const controlsLocked = interactionLocked || isPlaying || solutionMoves.length > 0 || isEditing;
  const validation = useMemo(() => validateCube(cubeState), [cubeState]);
  const clearFeedback = () => { setMessage(""); setError(""); };

  async function requestMove(move, onSuccess) {
    setPendingAction("move"); clearFeedback();
    try {
      const result = await applyMove(cubeState, move);
      setCubeState(result.cubeState); setIsSolved(result.solved); setAnimatingMove(move); onSuccess?.(result);
    } catch (requestError) { setError(requestError.message); setIsPlaying(false); }
    finally { setPendingAction(null); }
  }

  function handleMove(move) { if (!controlsLocked) requestMove(move); }

  async function handleScramble() {
    if (controlsLocked) return;
    setPendingAction("scramble"); clearFeedback();
    try {
      const result = await scrambleCube();
      setCubeState(result.cubeState); setLastScramble(result.scrambleMoves); setSolutionMoves([]);
      setCurrentSolutionIndex(0); setIsPlaying(false); setIsSolved(false);
      setMessage("A fresh 20-move scramble is ready to solve.");
    } catch (requestError) { setError(requestError.message); }
    finally { setPendingAction(null); }
  }

  async function handleSolve() {
    if (controlsLocked) return;
    if (!validation.valid) {
      setError(validation.errors.join(" "));
      return;
    }
    setPendingAction("solve"); clearFeedback();
    try {
      const result = await solveCube(cubeState);
      setSolutionMoves(result.moves); setCurrentSolutionIndex(0); setIsPlaying(false); setIsSolved(result.moves.length === 0);
      setMessage(result.moves.length === 0 ? "This cube is already solved." : `${result.moveCount} moves found. Follow them at your own pace.`);
    } catch (requestError) { setError(requestError.message); }
    finally { setPendingAction(null); }
  }

  const handleNextMove = useCallback(async () => {
    if (interactionLocked || currentSolutionIndex >= solutionMoves.length) return;
    const move = solutionMoves[currentSolutionIndex];
    setPendingAction("move"); clearFeedback();
    try {
      const result = await applyMove(cubeState, move);
      const nextIndex = currentSolutionIndex + 1;
      setCubeState(result.cubeState); setIsSolved(result.solved); setAnimatingMove(move); setCurrentSolutionIndex(nextIndex);
      if (nextIndex === solutionMoves.length) { setIsPlaying(false); setMessage("Solved — nicely done."); }
    } catch (requestError) { setError(requestError.message); setIsPlaying(false); }
    finally { setPendingAction(null); }
  }, [cubeState, currentSolutionIndex, interactionLocked, solutionMoves]);

  async function handlePreviousMove() {
    if (interactionLocked || currentSolutionIndex === 0) return;
    await requestMove(getInverseMove(solutionMoves[currentSolutionIndex - 1]), () => {
      setCurrentSolutionIndex((index) => index - 1); setIsPlaying(false);
    });
  }

  useEffect(() => {
    if (!isPlaying || interactionLocked || solutionFinished) return undefined;
    const timer = window.setTimeout(handleNextMove, 650);
    return () => window.clearTimeout(timer);
  }, [handleNextMove, interactionLocked, isPlaying, solutionFinished]);

  function handleStickerClick(face, index) {
    if (!isEditing || index === 4) return;
    const faceIndex = ["U", "R", "F", "D", "L", "B"].indexOf(face);
    if (faceIndex < 0) return;
    const absoluteIndex = faceIndex * 9 + index;
    setCubeState((state) => `${state.slice(0, absoluteIndex)}${selectedColor}${state.slice(absoluteIndex + 1)}`);
    setIsSolved(false); setSolutionMoves([]); setCurrentSolutionIndex(0); setIsPlaying(false); clearFeedback();
  }

  function handleToggleEditor() {
    if (interactionLocked || solutionMoves.length > 0 || isPlaying) return;
    setIsEditing((editing) => !editing); clearFeedback();
  }
  function handleResetCube() {
    if (interactionLocked) return;
    setCubeState(solvedCubeState); setSolutionMoves([]); setCurrentSolutionIndex(0); setLastScramble([]);
    setIsPlaying(false); setIsSolved(true); clearFeedback();
  }
  function handleNewSolve() { if (!interactionLocked) { setSolutionMoves([]); setCurrentSolutionIndex(0); setIsPlaying(false); clearFeedback(); } }

  const cubeStatus = pendingAction ? (pendingAction === "solve" ? "Finding the best solution…" : pendingAction === "scramble" ? "Creating a scramble…" : "Making the move…") : isEditing ? "Edit mode — select a color, then click a sticker." : animatingMove ? `Turning ${animatingMove}…` : isSolved ? "Solved" : "Ready";

  return (
    <div className="app-shell">
      <header className="app-header">
        <div><p className="eyebrow">Round &amp; Solve</p><h1>Rubik&apos;s Cube Solver</h1><p className="header-copy">Scramble, enter a real cube state, or solve one move at a time.</p></div>
        <div className={`status-pill ${isSolved ? "solved" : ""}`} aria-live="polite"><span className="status-dot" /> {cubeStatus}</div>
      </header>
      <main className="solver-layout">
        <section className="cube-card panel" aria-label="Interactive 3D cube">
          <div className="panel-heading"><div><p className="section-kicker">Live cube</p><h2>{isEditing ? "Click stickers to set their color" : "Drag to rotate the cube"}</h2></div>{isEditing && <span className="mode-badge">Editing</span>}</div>
          <RubiksCube cubeState={cubeState} animatingMove={animatingMove} onAnimationComplete={() => setAnimatingMove(null)} onStickerClick={handleStickerClick} />
          <p className="cube-tip">Use the mouse or trackpad to inspect every face.</p>
        </section>
        <aside className="control-column">
          <section className="panel action-panel"><div className="panel-heading compact"><div><p className="section-kicker">Start here</p><h2>Cube actions</h2></div></div>
            <div className="primary-actions"><button className="button primary" onClick={handleSolve} disabled={controlsLocked}>{pendingAction === "solve" ? "Solving…" : "Solve cube"}</button><button className="button secondary" onClick={handleScramble} disabled={controlsLocked}>{pendingAction === "scramble" ? "Scrambling…" : "Scramble cube"}</button></div>
            <div className="secondary-actions"><button className="text-button" onClick={handleToggleEditor} disabled={interactionLocked || solutionMoves.length > 0 || isPlaying}>{isEditing ? "Finish editing" : "Enter cube state"}</button><button className="text-button" onClick={handleResetCube} disabled={interactionLocked}>Reset cube</button></div>
          </section>
          {lastScramble.length > 0 && !solutionMoves.length && <section className="panel sequence-panel"><p className="section-kicker">Current scramble</p><div className="move-chips muted">{lastScramble.map((move, index) => <span key={`${move}-${index}`}>{move}</span>)}</div></section>}
          {isEditing && <section className="panel editor-panel"><ColorPicker selectedColor={selectedColor} onColorSelect={setSelectedColor} /><CubeValidation cubeState={cubeState} /></section>}
          {solutionMoves.length > 0 && <section className="panel solution-panel"><div className="solution-heading"><div><p className="section-kicker">Solution path</p><h2>{solutionFinished ? "Complete" : `Move ${currentSolutionIndex + 1} of ${solutionMoves.length}`}</h2></div><span className="move-count">{solutionMoves.length} moves</span></div><div className="progress-track"><span style={{ width: `${(currentSolutionIndex / solutionMoves.length) * 100}%` }} /></div><div className="move-chips" aria-label="Solution moves">{solutionMoves.map((move, index) => <span key={`${move}-${index}`} className={index < currentSolutionIndex ? "completed" : index === currentSolutionIndex ? "current" : ""}>{move}</span>)}</div><div className="solution-controls"><button className="button secondary" onClick={handlePreviousMove} disabled={interactionLocked || currentSolutionIndex === 0}>Previous</button><button className="button primary" onClick={() => setIsPlaying((playing) => !playing)} disabled={interactionLocked || solutionFinished}>{isPlaying ? "Pause" : "Play"}</button><button className="button secondary" onClick={handleNextMove} disabled={interactionLocked || solutionFinished}>Next</button></div><button className="text-button reset-solution" onClick={handleNewSolve} disabled={interactionLocked}>Clear solution</button></section>}
          <section className="panel moves-panel"><MoveControls onMove={handleMove} disabled={controlsLocked} /></section>
        </aside>
      </main>
      {(message || error) && <div className={`toast ${error ? "error" : "success"}`} role={error ? "alert" : "status"}><strong>{error ? "Something needs attention" : "Cube update"}</strong><span>{error || message}</span></div>}
    </div>
  );
}

export default App;
