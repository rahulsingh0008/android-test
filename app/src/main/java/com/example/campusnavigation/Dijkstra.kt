package com.example.campusnavigation

import java.util.PriorityQueue

object Dijkstra {

    fun shortestPath(
        graph: Map<String, List<Pair<String, Double>>>,
        start: String,
        end: String
    ): Pair<List<String>, Double> {

        val distances = mutableMapOf<String, Double>()
        val previous = mutableMapOf<String, String?>()
        val visited = mutableSetOf<String>()

        graph.keys.forEach { node ->
            distances[node] = Double.POSITIVE_INFINITY
            previous[node] = null
        }

        distances[start] = 0.0

        val queue = PriorityQueue(compareBy<Pair<String, Double>> { it.second })
        queue.add(start to 0.0)

        while (queue.isNotEmpty()) {
            val (current, _) = queue.poll()!!

            if (current == end) break
            if (visited.contains(current)) continue

            visited.add(current)

            graph[current]?.forEach { (neighbor, weight) ->
                val altDistance = distances[current]!! + weight
                // Safely access the distance value
                val currentDistance = distances[neighbor] ?: Double.MAX_VALUE
                if (altDistance < currentDistance) {
                    distances[neighbor] = altDistance
                    previous[neighbor] = current
                    queue.add(neighbor to altDistance)
                }
            }
        }

        val path = mutableListOf<String>()
        var currentNode: String? = end

        while (currentNode != null) {
            path.add(0, currentNode)
            currentNode = previous[currentNode]
        }

        return Pair(path, distances[end]!!)
    }
}
