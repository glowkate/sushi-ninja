# sushi-ninja
TO RUN:
Go into the run directory and run main.

_______________________________________________________________________________

This is a turn based stratigy sim. There are 3 maps where two teams controlled
by CPUs. Whatever team is left standing wins!

-=Class Overview=-

Coord - A two dimentional co-ordinate. Used by many classes to keep track
	of locations.
CpuInput - A class of static methods that controls fighters. The only method
	that should be caled outside of the class is doTurn.
Fighter - A charicter that can move around and attack other fighters. This
	is the only class with methods that call RNG.
GameTest - A class holding tests for the rest of the classes in the sim.
GameWorld - Using a list of fighters and a map, runs a simulation. Used
	only by main.
Main - Orginises and sets up GameWorld depending on user input.
Map - A map in the game. Contains a dictionary of tiles with coords as the
	keys. Can do fighter pathfinding using BFS and can check line of sight.
MapFrame - Used as input to a JFrame. Responcible for drawing the game.
	Will update the screen when told to.
Tile - Created by map and accsessed by some other classes. Has a type saying
	what it looks like and what can occer inside of it.

