# Shortest Path Discovery using Dijkstra's Algorithm🗺️

[Project Description 🧾](https://github.com/Lina-Abureesh4/AlgorithmsProj3/blob/master/Project3_Dijkstra.pdf)

## Overview
This project focuses on implementing Dijkstra's Algorithm to compute the shortest path between two cities, based on a map of the world. The primary goal is to provide the user with the route that results in the lowest cost between two cities, taking into account the Euclidean distances between cities, represented as a weighted graph.

All data in the project is *real* data.

### Graph Representation 
The cities will be represented as vertices, and the roads (edges) between them will have weights based on their Euclidean distances. A sample input format will list the number of cities, their coordinates, and the edges (connections between cities).

### Formula used to calculate the distance between two cities
Given the longitude and latitude of two cities, and after converting them to **radian**,

the distance (in km) is calculated with:
```
acos( sin(lat1)*sin(lat2) + cos(lat1)*cos(lat2)*cos(lon2-lon1) ) * 6371
```
Note: 6371 is Earth radius in km.
  
## Screenshots of the program

### Start Screen
![start](https://github.com/user-attachments/assets/f30da609-078c-4b17-be80-ddce4e69974e)

### Main Screen
![main](https://github.com/user-attachments/assets/8cef8e3b-9071-4af8-9ae1-2bcdcde6de01)
