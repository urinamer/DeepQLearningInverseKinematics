# RoboArm-RL: Inverse Kinematics from Scratch

This project is an implementation of an N-link robotic arm that learns to solve Inverse Kinematics (IK) problems through Reinforcement Learning. Every component from the neural network backpropagation to the forward kinematics is written in pure Java, without the use of external machine learning or mathematical libraries.

---

## Key Features

* **Custom DQN Implementation:** A manual implementation of a Deep Q-Network, including the neural network architecture, backpropagation algorithm, and activation functions (Leaky ReLU).
* **Learning Stability:** Employs a **Target Network** to reduce oscillations and a **Replay Buffer** to break correlations between consecutive experiences.
* **Configurable Kinematics:** Supports robotic arms with $N$ joints, configurable via the project's constants.
* **Interactive Visualization:** A Java Swing-based graphical user interface (GUI) provides visualization of the arm's movement and the learning process.

---

## System Architecture

The project is structured into two primary layers to ensure a clean separation of concerns:

### 1. Core Library (`Library` package)
A generic Reinforcement Learning engine independent of the physical problem:
* `Network`: Manages neural layers, forward passes, and gradient calculations.
* `ReplayBuffer`: Stores and samples transitions for experience replay.
* `Neuron`: The fundamental calculation unit (weights, bias, and activation).

### 2. Inverse Kinematics Domain (`InverseKinematics` package)
The specific implementation of the robotic arm environment:
* `Arm`: The physical model calculating the end-effector position based on joint angles.
* `Environment`: Defines the state space, action space, and the reward system.
* `Agent`: The decision-making entity that interacts with the environment.

---

## Technical Specifications

The model's performance can be tuned via the `Constants` and `NetworkConstants` classes. These are the default parameters:

| Parameter | Value |
| :--- | :--- |
| **Learning Rate** | $0.0001$ |
| **Batch Size** | $128$ |
| **Discount Factor ($\gamma$)** | $0.9$ |
| **Epsilon Decay** | $0.99949$ |
| **Huber Loss Alpha** | $1.0$ |

---


### Installation and Execution
* **Java Development Kit (JDK) 17** or higher.
1.  Clone the repository:
    ```bash
    git clone https://github.com/urinamer/DeepQLearningInverseKinematics.git
    ```
2.  Navigate to the project directory and compile the source files.
3.  Run `Controller.createWindow()` command to launch the simulation and GUI.
4.  **Loading Models:** Pre-trained models can be loaded from CSV files located in the `Models/` directory.

---

## Motivation

In an ecosystem dominated by Python based frameworks like PyTorch and TensorFlow, this project serves as a deep dive into the low level mechanics of AI. Implementing these algorithms in Java helped me understand data structures, memory management in DL, and how Reinforcement Learning works from the ground up.
