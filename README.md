# Poker-Game

This repository implements a simplified 5-card poker game in Java, showcasing advanced data structures, algorithmic design, and turn-based game logic. The project is divided into two key modules:

**Aggressive AI**: Implements a Max Heap to dynamically evaluate and play the strongest possible 5-card hand each round, maximizing competitiveness against the player.

**Recommended Hands**: Uses a Red-Black Tree to efficiently store all valid 5-card hands for the player, enforce hand validity, and remove consumed hands from the data structure in real-time.

**Core Features**: 
- Turn-Based Gameplay: 5 rounds per game; AI and player each draw 25 cards initially.
- Strategic Hand Selection: Player selects valid hands while AI always executes aggressive moves.
- Score Tracking: Scores updated per round; draws do not increment points.
- Detailed Game Feedback: Pocket cards and every 5-card hand move are displayed for strategic analysis, with the final winner reported at the end.
