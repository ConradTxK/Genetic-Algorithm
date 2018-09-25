# Genetic-Algorithm
The Genetic Algorithm (GA) belongs to the larger class of Evolutionary Algorithms which generates solutions to optimization problems using techniques inspired by natural evolution, such as inheritance, mutation and selection. GAs are mostly used for problems in which their seach domain is increasing exponentially as the input increases linearly. If n is very large then the time required to look through the entire search domain is sometimes equal to a few years, even with the fastest CPU. A GA generates a number of instances from the search domain and tries to find a solution near to the optimum solution. The generated set of instances then undergoes three main actions: **Crossover**, **Mutation** and **Selection**
## Installation
Simply import the "Intelligent System Project" folder to your Java IDE. And run the 'is17080118.java' to execute the project.
## Preview of the results

### Original Matrix
<img width="508" alt="_20180925212113" src="https://user-images.githubusercontent.com/26427743/46040807-300fac00-c109-11e8-851c-d25d3c5a4a8c.png">

### Similarity Matrix
After the iteration performed by the genetic Algorithm <br/> <br/>
<img width="512" alt="_20180925212201" src="https://user-images.githubusercontent.com/26427743/46040966-9694ca00-c109-11e8-90d6-8e6b83799b51.png">

## Ordering
The solution for this project is a one dimensional array of numbers that represents an ordering. The size of each ordering is equal to the toal number of individual items. No ordering is allowed to have a repeated number.

## Fitness Function
The fitness function indicates how good an ordering is. The measure of the goodness of an ordering is called the **fitness cost**. Typically each problem has to define its own fitness function depending on the nature of the problem. 

## Population size
The GA initially starts with a number of randomly generated orderings. The number of initial random orderings is a parameter called the population size. The initial population of orderings is also called the first generation or generation 0. 

## Selection
Selection is a GA technique which tries to smartly select better orderings from the population for Crossover and Mutation.

## Crossover
Crossover is a GA technique which randomly picks and removes two different orderings (parents) from the current population and creates two new orderings (children) and inserts them into a new population (next generation)

## Mutation
Mutation is another GA technique that randomly picks and removes one ordering from the current population and applies small change on the ordering and inserts it into the next generation. 

## Reproduction
Reproduction randomly picks and removes one ordering from the current population and inserts it into a new population (next generation)

## Generation
When all those steps are done and the current population is empty then the newly created population is called generation 1 and it is ready to undergo all those processes (selection, crossover, mutation and reproduction) again to create the next generation. The whole process continues until a certain number of generations are created and then from the very last generation the best ordering based on the fitness cost is selected as a final result. The number of required generations is set by user.
