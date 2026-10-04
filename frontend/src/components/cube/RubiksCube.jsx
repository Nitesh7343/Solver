import { useRef } from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import { OrbitControls } from "@react-three/drei";
import * as THREE from "three";

import { parseCubeState, getStickerColor, getFaceIndex } from "../../cube/visualMapping";

const COLOR_MAP = {
  U: "#ffffff",
  R: "#ff0000",
  F: "#00aa00",
  D: "#ffff00",
  L: "#ff8800",
  B: "#0066ff",
};

const CUBIE_SIZE = 0.95;

function Cubie({
  position,
  cubeState,
  onStickerClick,
}) {
  const [x, y, z] = position;

  const materials = [
    // Right
    new THREE.MeshStandardMaterial({
      color:
        x === 1
          ? COLOR_MAP[
              getStickerColor(
                cubeState,
                "R",
                getFaceIndex("R", x, y, z)
              )
            ]
          : "#111111",
    }),

    // Left
    new THREE.MeshStandardMaterial({
      color:
        x === -1
          ? COLOR_MAP[
              getStickerColor(
                cubeState,
                "L",
                getFaceIndex("L", x, y, z)
              )
            ]
          : "#111111",
    }),

    // Top
    new THREE.MeshStandardMaterial({
      color:
        y === 1
          ? COLOR_MAP[
              getStickerColor(
                cubeState,
                "U",
                getFaceIndex("U", x, y, z)
              )
            ]
          : "#111111",
    }),

    // Bottom
    new THREE.MeshStandardMaterial({
      color:
        y === -1
          ? COLOR_MAP[
              getStickerColor(
                cubeState,
                "D",
                getFaceIndex("D", x, y, z)
              )
            ]
          : "#111111",
    }),

    // Front
    new THREE.MeshStandardMaterial({
      color:
        z === 1
          ? COLOR_MAP[
              getStickerColor(
                cubeState,
                "F",
                getFaceIndex("F", x, y, z)
              )
            ]
          : "#111111",
    }),

    // Back
    new THREE.MeshStandardMaterial({
      color:
        z === -1
          ? COLOR_MAP[
              getStickerColor(
                cubeState,
                "B",
                getFaceIndex("B", x, y, z)
              )
            ]
          : "#111111",
    }),
  ];

  function handleClick(event) {
    event.stopPropagation();

    if (!onStickerClick) {
      return;
    }

    const materialIndex = event.face?.materialIndex;

    if (materialIndex === undefined) {
      return;
    }

    const faceMap = {
      0: "R",
      1: "L",
      2: "U",
      3: "D",
      4: "F",
      5: "B",
    };

    const face = faceMap[materialIndex];

    if (!face) {
      return;
    }

    // Only allow clicking an outer sticker.
    if (
      (face === "R" && x !== 1) ||
      (face === "L" && x !== -1) ||
      (face === "U" && y !== 1) ||
      (face === "D" && y !== -1) ||
      (face === "F" && z !== 1) ||
      (face === "B" && z !== -1)
    ) {
      return;
    }

    const index = getFaceIndex(face, x, y, z);

    // Centers are fixed.
    if (index === 4) {
      return;
    }

    onStickerClick(face, index);
  }

  return (
    <mesh
      position={position}
      geometry={new THREE.BoxGeometry(
        CUBIE_SIZE,
        CUBIE_SIZE,
        CUBIE_SIZE
      )}
      material={materials}
      onPointerDown={handleClick}
      castShadow
      receiveShadow
    />
  );
}

function AnimatedLayer({
  move,
  children,
  onComplete,
}) {
  const groupRef = useRef();

  const duration = 400;
  const startTime = useRef(null);
  const completed = useRef(false);

  const getAnimation = () => {
    const face = move[0];
    const modifier = move[1];

    let axis;
    let angle;

    const double =
      modifier === "2";

    switch (face) {
      case "R":
        axis = "x";
        angle = double
          ? -Math.PI
          : -Math.PI / 2;
        break;

      case "L":
        axis = "x";
        angle = double
          ? Math.PI
          : Math.PI / 2;
        break;

      case "U":
        axis = "y";
        angle = double
          ? -Math.PI
          : -Math.PI / 2;
        break;

      case "D":
        axis = "y";
        angle = double
          ? Math.PI
          : Math.PI / 2;
        break;

      case "F":
        axis = "z";
        angle = double
          ? -Math.PI
          : -Math.PI / 2;
        break;

      case "B":
        axis = "z";
        angle = double
          ? Math.PI
          : Math.PI / 2;
        break;

      default:
        return null;
    }

    if (modifier === "'") {
      angle *= -1;
    }

    return { axis, angle };
  };

  const animation = getAnimation();

  useFrame(({ clock }) => {
    if (!groupRef.current || !animation) {
      return;
    }

    if (startTime.current === null) {
      startTime.current = clock.getElapsedTime();
    }

    const elapsed =
      (clock.getElapsedTime() -
        startTime.current) *
      1000;

    const progress = Math.min(
      elapsed / duration,
      1
    );

    const eased =
      1 - Math.pow(1 - progress, 3);

    groupRef.current.rotation[
      animation.axis
    ] = animation.angle * eased;

    if (progress >= 1 && !completed.current) {
      completed.current = true;
      onComplete();
    }
  });

  return (
    <group ref={groupRef}>
      {children}
    </group>
  );
}

function CubeModel({
  cubeState,
  animatingMove,
  onAnimationComplete,
  onStickerClick,
}) {
  const parsedState = parseCubeState(cubeState);

  const cubies = [];

  for (let x = -1; x <= 1; x++) {
    for (let y = -1; y <= 1; y++) {
      for (let z = -1; z <= 1; z++) {
        cubies.push({
          position: [x, y, z],
        });
      }
    }
  }

  const getLayer = () => {
    if (!animatingMove) {
      return [];
    }

    const face = animatingMove[0];

    return cubies.filter(({ position }) => {
      const [x, y, z] = position;

      switch (face) {
        case "R":
          return x === 1;
        case "L":
          return x === -1;
        case "U":
          return y === 1;
        case "D":
          return y === -1;
        case "F":
          return z === 1;
        case "B":
          return z === -1;
        default:
          return false;
      }
    });
  };

  const animatedCubies = getLayer();

  const isAnimated = (position) =>
    animatedCubies.some(
      (cubie) =>
        cubie.position[0] === position[0] &&
        cubie.position[1] === position[1] &&
        cubie.position[2] === position[2]
    );

  const renderCubie = ({ position }) => (
    <Cubie
      key={position.join(",")}
      position={position}
      cubeState={parsedState}
      onStickerClick={onStickerClick}
    />
  );

  if (!animatingMove) {
    return (
      <>
        {cubies.map(renderCubie)}
      </>
    );
  }

  return (
    <>
      {cubies
        .filter(({ position }) => !isAnimated(position))
        .map(renderCubie)}

      <AnimatedLayer
        move={animatingMove}
        onComplete={onAnimationComplete}
      >
        {animatedCubies.map(renderCubie)}
      </AnimatedLayer>
    </>
  );
}

function RubiksCube({
  cubeState,
  animatingMove,
  onAnimationComplete,
  onStickerClick,
}) {
  return (
    <div className="cube-stage">
      <Canvas
        dpr={[1, 2]}
        camera={{
          position: [5, 5, 7],
          fov: 45,
        }}
      >
        <ambientLight intensity={1.5} />

        <directionalLight
          position={[5, 5, 5]}
          intensity={2}
        />

        <CubeModel
          cubeState={cubeState}
          animatingMove={animatingMove}
          onAnimationComplete={
            onAnimationComplete
          }
          onStickerClick={onStickerClick}
        />

        <OrbitControls />
      </Canvas>
    </div>
  );
}

export default RubiksCube;
