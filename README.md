# Pacman – Java Swing Game

A multi-level Pacman clone built in **Java (Swing)** using a Maven project structure.

---

##  Game Features

### Ghost AI
- Smart path selection based on Pacman's position  
- Different ghost personalities (chase / shy)  
- Difficulty-based randomness  
- Respawn delay + spawn grace protection  

###  Power Mode
- Score multiplier for consecutive ghost captures  
- Power pellets activate frightened state  
- Visual countdown indicator  

###  Bonus Life
- Grants extra life  
- Spawns only in Levels 2 & 3  
- Limited lifetime  

---

##  Difficulty Scaling

| Difficulty | Level 1 | Level 2 | Level 3 |
|------------|---------|---------|---------|
| EASY       | 2 ghosts | 3 ghosts | 3 ghosts |
| NORMAL     | 3 ghosts | 4 ghosts | 4 ghosts |
| HARD       | 4 ghosts | 5 ghosts | 5 ghosts |

---

## Controls

- Arrow Keys → Move  
- P → Pause / Resume  
- ESC → Menu  
- 1 / 2 / 3 → Select difficulty  
- SPACE → Start / Continue  
- R → Restart  
- E → Exit  

---

## Requirements

- Java 17+
- Maven

---

### Future Improvements
- Sound effects & background music
- High score system database based

---

## Run

```bash
mvn clean package
mvn exec:java
