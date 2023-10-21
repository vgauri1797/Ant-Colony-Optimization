# Ant-Colony-Optimization

Introduction-

The traveling salesman problem (TSP) is a deceptively simple combinatorial problem. It can be stated very simply: a salesman spends his time visiting n cities (or nodes) cyclically. In one tour he visits each city just once, and finishes up where he started. TSP became a benchmark for many new approaches in combinatorial optimization. 
In the theory of computational complexity, the decision version of the TSP (where, given a length L, the task is to decide whether the graph has any tour shorter than L) belongs to the class of NP-complete problems. Thus, it is possible that the worst-case running time for any algorithm for the TSP increases superpolynomially, but no more than exponentially, with the number of cities.
The generalized TSP is a very simple but practical extension of TSP. In the GTSP problem, the set of nodes is the union of m clusters, which may or may not be intersected. Each feasible solution of GTSP, called a g-tour, is a closed path that includes at least one node from each cluster, and the objective is to find a g-tour with the minimum cost. In a special case of GTSP, called E-GTSP, each cluster is visited exactly once.

Generalized Travelling Salesman Problem-

The GTSP represents a kind of combinatorial optimization problem. It can be described as the problem of seeking a special Hamiltonian cycle with the lowest cost in a complete weighted graph. Let G = (X, E, W) be a complete weighted graph, where X is the number of nodes in the graph, E is the number of edges between two nodes and W is the weight of each edge in the graph.
The set X is partitioned into m intersecting groups of X1, X2,..., Xm with each group having atleast one node. The special Hamiltonian cycle is required to pass through all of the groups, but not all of the vertices differing from that of the TSP. 

There are two different kinds of GTSP under the abovementioned framework of the special Hamiltonian cycle: 
1. The cycle passes exactly one vertex in each group.
2. The cycle passes at least one vertex in each group. In this paper we only discuss the GTSP for the first case, namely, the E-GTSP.

Ant Colony Optimization for TSP-

In ant colony optimization, the problem is tackled by simulating a number of artificial ants moving on a graph that encodes the problem itself: each vertex represents a city and each edge represents a connection between two cities. A variable called pheromone is associated with each edge and can be read and modified by ants. 
Ant colony optimization is an iterative algorithm. At each iteration, a number of artificial ants are considered. Each of them builds a solution by walking from vertex to vertex on the graph with the constraint of not visiting any vertex that she has already visited in her walk. At each step of the solution construction, an ant selects the following vertex to be visited according to a stochastic mechanism that is biased by the pheromone: when in vertex i, the following vertex is selected stochastically among the previously unvisited ones. In particular, if j has not been previously visited, it can be selected with a probability that is proportional to the pheromone associated with edge (i, j).
At the end of an iteration, on the basis of the quality of the solutions constructed by the ants, the pheromone values are modified in order to bias ants in future iterations to construct solutions similar to the best ones previously constructed.
Given an n-city TSP with distances d ij , the artificial ants are distributed to these n cities randomly. Each ant will choose the next to visit according to the pheromone trail remained on the paths just as mentioned in  above example. However, there are two main differences between artificial ants and real ants: 1. The artificial ants have ‘‘memory” therefore they can remember the cities they have visited and therefore they would not select those cities again.
2. The artificial ants are not completely ‘‘blind” therefore they know the distances between two cities and prefer to choose the nearby cities from their positions.
In general, the kth ant moves from city x to city y with probability-

![alt text](https://wikimedia.org/api/rest_v1/media/math/render/svg/87876b3e1033b60f992d33a181bee4e2d7b229ab)

where Txy is the amount of pheromone deposited for transition from state x to y, 0 ≤alpha  is a parameter to control the influence of Txy, nxy is the desirability of state transition xy and  beta≥ 1 is a parameter to control the influence of nxy, Txy and nxz represent the attractiveness and trail level for the other possible state transitions.
When all the ants have completed a solution, the trails are updated by 

![alt text](https://wikimedia.org/api/rest_v1/media/math/render/svg/62ef8b59ad37970b4e693ee923b6d7db8bbd5c30)

where Txy is the amount of pheromone deposited for a state transition xy, p is the pheromone evaporation coefficient and delta(Txy^k) is the amount of pheromone deposited by kth ant, typically given for a TSP problem by

![alt text](https://wikimedia.org/api/rest_v1/media/math/render/svg/da75f512c94f2b2737112bebbf97539f5f6928c0)

Ant Colony Optimization method for GTSP 
The probability that city j is selected by ant k to be visited after city i is computed according to two factors, namely, that the pheromone trail quantity distributed on the paths and the visibility of city j from city i. But ants must be able to identify the cities in the same classes of the ones having been visited. A simple idea is that the probability should also be computed where the set allowed k has different meaning. It is the set of cities which are in the classes having not been visited by ant k. And the updating rule of Tij is also followed. Denote visited k and tabu k as the set of visited cities and the set of groups having been visited by ant k, respectively. Then


Algorithm-

- TSP using ACO
The most important part is to properly select next city to visit. We should select the next town based on the probability logic. First, we can check if Ant should visit a random city. 
If we didn’t select any random city, we need to calculate probabilities to select the next city, remembering that ants prefer to follow stronger and shorter trails. We can do this by storing the probability of moving to each city in the array. In this algorithm, it is calculating the probability of each city getting selected by the ant.

```java
int i = ant.trail[currentIndex];
double pheromone = 0.0;
   for (int l = 0; l < numberOfCities; l++) {
  if (!ant.visited(l)){
  pheromone+= Math.pow(trails[i][l], alpha) * 
  Math.pow(1.0 / graph[i][l], beta);
  }
}
for (int j = 0; j < numberOfCities; j++) {
  if (ant.visited(j)) {
  probabilities[j] = 0.0;
  }
  else {
  double numerator= Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
  probabilities[j] = numerator / pheromone;
  }
}
```

In this algorithm, we find which city is to be selected by the ant next. 

```java
double r = random.nextDouble();
double total = 0;
for (int i = 0; i < numberOfCities; i++) {
	total += probabilities[i];
	if (total >= r) {
		return i;
	}
}
```

In this algorithm, we update the trail of pheromones because it gets evaporated after a time.

```java
for (int i = 0; i < numberOfCities; i++) {
  for (int j = 0; j < numberOfCities; j++) {
			trails[i][j] *= evaporation;
  }
}
for (Ant a : ants) {
double contribution = Q / a.trailLength(graph);
for (int i = 0; i < numberOfCities - 1; i++) {
  trails[a.trail[i]][a.trail[i + 1]] += contribution;
}
trails[a.trail[numberOfCities-1]][a.trail[0]] += contribution;
```

GTSP using ACO-

The formally extended ACO algorithm for GTSP is:-

1. Initialize:
   - Set time:=0 {time is time counter}
   - For every edge (i, j) 
	    -  an initial s ij = c for trail density and Delta(T(i,j)) = 0.
2. Set s:=0 {s is travel step counter}
   - For k:=1 to l do
     - Place ant k on a city randomly. Place the city in visited k .
	   - Place the group of the city in tabu k .
3. Repeat until s 6 m
   - Set s:=s + 1
   - For k:=1 to l do
      - Choose the next city to be visited according to the probability p ijk.
	    - Move the ant k to the selected city.
	    - Insert the selected city in visited k .
	    - Insert the group of selected city in tabu k .
4. For k:=1 to l do
	  - Move the ant k from visited k(n) to visited k(1).
	  - Compute the tour length L k traveled by ant k.
	  - Update the shortest tour found.
	  - For every edge (i, j) do
	  - For k:=1 to l do
		  - Update the pheromone trail density T(i,j).
		  - time:=time + 1
5. If (time<TIME_MAX) then
    - Empty all visited k and tabu k
	  - Goto Step 2.
6. Else
    - Print the shortest tour.
Stop

Conclusion-

Focused on the generalized traveling salesman problem, this paper extends the ant colony optimization method from TSP to this field. Based on the basic extended ACO method, we developed an improved method by considering the group influence.
