Jackson Eshbaugh
05/05/2024
CS 150: Data Structures & Algorithms
Professor John Dahl

Remarks
----
- I had to update the formula I derived for moving the trucks, it was very off. Primarily, I had to round instead
of ceiling every decimal calculation in the sim.

- I overlooked integer division errors in Truck.move and had to cast both ints to a double for that division.

- I needed to add a both null case to my setDestination() method for the end of the simulation—eventually, trucks
will complete all their tasks!

- The way that Dock.handle() works changed a bit from algorithmic planning to implementation, in that trucks are
removed and added in such a way that they don't wait an hour to be docked and another hour to load, for example.
This changed my unit tests slightly.

- I needed to store the name of the log file, so that I could timestamp them and not overwrite one log file over
and over. That's why I created the References class (more of a dictionary of one entry than a class, really). This
variable is initialized when creating a new TransportationSimulation.

- Trucks kept staying at the same warehouse even when they needed to leave. The issue was that destinations were
assigned, but the trucks couldn't fit the shipments into their holds. An update to setDestination() made quick
work of this issue.

Features
----
I succesfully implemented everything I planned on implementing in this project. I implemented trucks, warehouses,
shipments, and a coordinate grid system, just to give a few examples. Each had a complete (and evolved)
functionality. Additionally, the simulation runs well as a whole, the config file works, and the randomization
works, too (execute main() with args = { "random" } to get a randomized config generated). Logging works also (logs
are saved to simulation-datetime.log. One feature I implemented on the fly and really like is the ability to add more
time onto the simulation after it finishes if you'd like.

Lesson / Experience
---
One of the lessons I learned from this project is the software engineering design patterns (something I know I'll
use in CS 205, and probably before that course, too). Being able to describe how code works in English is not
simple—and code evolves, so that spec sheet will almost never represent the exact final product.