package com.example.campusnavigation

import org.osmdroid.util.GeoPoint

//  data class: special type of class used to hold data.
//  automatically provides many useful features like toString(), equals(), hashCode(),
//  and copy() functions — without writing extra code.
data class CampusNode(val location: GeoPoint, val name: String)

object CampusGraph {

    // map all locations from CampusLocations.kt
    val nodes: Map<String, CampusNode> = CampusLocations.locations.mapValues { (name, point) ->
        CampusNode(point, name)
    }

    // Function to calculate the straight-line distance between two points
    // uses haversine formula under the hood
    fun calculateDistance(p1: GeoPoint, p2: GeoPoint): Double {
        return p1.distanceToAsDouble(p2)  // Returns distance in meters
    }

    // Define the edges with weights (distances between locations)
    val edges: Map<String, List<Pair<String, Double>>> = mapOf(
        "B.Tech Block" to listOf(
            "Flag Spot" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Flag Spot"]!!.location),
            "Cafe & Gym" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Cafe & Gym"]!!.location),
            "Badminton Court" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Badminton Court"]!!.location),
            "Basketball Court" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Basketball Court"]!!.location),
            "Bridge" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Bridge"]!!.location),
            "Aryabhatt Lab" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Aryabhatt Lab"]!!.location),
            "Param Lab" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Param Lab"]!!.location),
            "Library" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Library"]!!.location),
            "Petroleum Block" to calculateDistance(nodes["B.Tech Block"]!!.location, nodes["Petroleum Block"]!!.location)
        ),
        "CS/IT Block" to listOf(
            "CS/IT Entrance Gate" to calculateDistance(nodes["CS/IT Block"]!!.location, nodes["CS/IT Entrance Gate"]!!.location),
            "Lakshmi Bai Girls Hostel" to calculateDistance(nodes["CS/IT Block"]!!.location, nodes["Lakshmi Bai Girls Hostel"]!!.location)
        ),
        "Library" to listOf(
            "Baskteball Court" to calculateDistance(nodes["Library"]!!.location, nodes["Basketball Court"]!!.location),
            "B.Tech Block" to calculateDistance(nodes["Library"]!!.location, nodes["B.Tech Block"]!!.location),
            "Aryabhatt Lab" to calculateDistance(nodes["Library"]!!.location, nodes["Aryabhatt Lab"]!!.location),
            "Param Lab" to calculateDistance(nodes["Library"]!!.location, nodes["Param Lab"]!!.location)
        ),
        "Civil Block" to listOf(
            "Badminton Court" to calculateDistance(nodes["Civil Block"]!!.location, nodes["Badminton Court"]!!.location),
            "B.Tech Block" to calculateDistance(nodes["Civil Block"]!!.location, nodes["B.Tech Block"]!!.location),
            "Basketball Court" to calculateDistance(nodes["Civil Block"]!!.location, nodes["Basketball Court"]!!.location)
        ),
        "Aryabhatt Lab" to listOf(
            "B.Tech Block" to calculateDistance(nodes["Aryabhatt Lab"]!!.location, nodes["B.Tech Block"]!!.location),
            "Param Lab" to calculateDistance(nodes["Aryabhatt Lab"]!!.location, nodes["Param Lab"]!!.location),
            "Library" to calculateDistance(nodes["Aryabhatt Lab"]!!.location, nodes["Library"]!!.location),
            "Vishwakarma Block" to calculateDistance(nodes["Aryabhatt Lab"]!!.location, nodes["Vishwakarma Block"]!!.location),
            "Priyadarshini Hostel" to calculateDistance(nodes["Aryabhatt Lab"]!!.location, nodes["Priyadarshini Hostel"]!!.location)
        ),
        "Parking" to listOf(
            "Chanakya Block" to calculateDistance(nodes["Parking"]!!.location, nodes["Chanakya Block"]!!.location),
            "Gate 2" to calculateDistance(nodes["Parking"]!!.location, nodes["Gate 2"]!!.location),
            "KP Nautiyal Block" to calculateDistance(nodes["Parking"]!!.location, nodes["KP Nautiyal Block"]!!.location)
        ),
        "PlayGround" to listOf(
            "Mess" to calculateDistance(nodes["PlayGround"]!!.location, nodes["Mess"]!!.location),
            "Laxmi Bai Girls Hostel" to calculateDistance(nodes["PlayGround"]!!.location, nodes["Lakshmi Bai Girls Hostel"]!!.location),
            "Sai Hostel" to calculateDistance(nodes["PlayGround"]!!.location, nodes["Sai Hostel"]!!.location),
            "Flag Spot" to calculateDistance(nodes["PlayGround"]!!.location, nodes["Flag Spot"]!!.location),
            "Bridge" to calculateDistance(nodes["PlayGround"]!!.location, nodes["Bridge"]!!.location)
        ),
        "Badminton Court" to listOf(
            "B.Tech Block" to calculateDistance(nodes["Badminton Court"]!!.location, nodes["B.Tech Block"]!!.location),
            "Flag Spot" to calculateDistance(nodes["Badminton Court"]!!.location, nodes["Flag Spot"]!!.location),
            "Civil Block" to calculateDistance(nodes["Badminton Court"]!!.location, nodes["Civil Block"]!!.location)
        ),
        "Mess" to listOf(
            "Lakshmi Bai Girls Hostel" to calculateDistance(nodes["Mess"]!!.location, nodes["Lakshmi Bai Girls Hostel"]!!.location),
            "Sai Hostel" to calculateDistance(nodes["Mess"]!!.location, nodes["Sai Hostel"]!!.location),
            "PlayGround" to calculateDistance(nodes["Mess"]!!.location, nodes["PlayGround"]!!.location)
        ),
        "Cafe & Gym" to listOf(
            "Flag Spot" to calculateDistance(nodes["Cafe & Gym"]!!.location, nodes["Flag Spot"]!!.location),
            "B.Tech Block" to calculateDistance(nodes["Cafe & Gym"]!!.location, nodes["B.Tech Block"]!!.location),
            "Bridge" to calculateDistance(nodes["Cafe & Gym"]!!.location, nodes["Bridge"]!!.location)
        ),
        "Lakshmi Bai Girls Hostel" to listOf(
            "CS/IT Block" to calculateDistance(nodes["Lakshmi Bai Girls Hostel"]!!.location, nodes["CS/IT Block"]!!.location),
            "PlayGround" to calculateDistance(nodes["Lakshmi Bai Girls Hostel"]!!.location, nodes["PlayGround"]!!.location),
            "Mess" to calculateDistance(nodes["Lakshmi Bai Girls Hostel"]!!.location, nodes["Mess"]!!.location)
        ),
        "Sai Hostel" to listOf(
            "PlayGround" to calculateDistance(nodes["Sai Hostel"]!!.location, nodes["PlayGround"]!!.location),
            "Mess" to calculateDistance(nodes["Sai Hostel"]!!.location, nodes["Mess"]!!.location),
            "Gully" to calculateDistance(nodes["Sai Hostel"]!!.location, nodes["Gully"]!!.location)
        ),
        "Medical" to listOf(
            "CS/IT Block" to calculateDistance(nodes["Medical"]!!.location, nodes["CS/IT Block"]!!.location),
            "Gate 1" to calculateDistance(nodes["Medical"]!!.location, nodes["Gate 1"]!!.location),
            "Bus Stop" to calculateDistance(nodes["Medical"]!!.location, nodes["Bus Stop"]!!.location)
        ),
        "Tuck Shop" to listOf(
            "Flag Spot" to calculateDistance(nodes["Tuck Shop"]!!.location, nodes["Flag Spot"]!!.location),
            "Gate 1" to calculateDistance(nodes["Tuck Shop"]!!.location, nodes["Gate 1"]!!.location)
        ),
        "President's Estate" to listOf(
            "Gate 1" to calculateDistance(nodes["President's Estate"]!!.location, nodes["Gate 1"]!!.location)
        ),
        "Gate 1" to listOf(
            "Medical" to calculateDistance(nodes["Gate 1"]!!.location, nodes["Medical"]!!.location),
            "Tuck Shop" to calculateDistance(nodes["Gate 1"]!!.location, nodes["Tuck Shop"]!!.location),
            "President's Estate" to calculateDistance(nodes["Gate 1"]!!.location, nodes["President's Estate"]!!.location),
            "CS/IT Entrance Gate" to calculateDistance(nodes["Gate 1"]!!.location, nodes["CS/IT Entrance Gate"]!!.location),
            "Bus Stop" to calculateDistance(nodes["Gate 1"]!!.location, nodes["Bus Stop"]!!.location)
        ),
        "Basketball Court" to listOf(
            "B.Tech Block" to calculateDistance(nodes["Basketball Court"]!!.location, nodes["B.Tech Block"]!!.location),
            "Civil Block" to calculateDistance(nodes["Basketball Court"]!!.location, nodes["Civil Block"]!!.location),
            "Library" to calculateDistance(nodes["Basketball Court"]!!.location, nodes["Library"]!!.location),
            "Petroleum Block" to calculateDistance(nodes["Basketball Court"]!!.location, nodes["Petroleum Block"]!!.location),
            "Param Lab" to calculateDistance(nodes["Basketball Court"]!!.location, nodes["Param Lab"]!!.location)
        ),
        "Petroleum Block" to listOf(
            "B.Tech Block" to calculateDistance(nodes["Petroleum Block"]!!.location, nodes["B.Tech Block"]!!.location),
            "Chanakya Block" to calculateDistance(nodes["Petroleum Block"]!!.location, nodes["Chanakya Block"]!!.location),
            "Basketball Court" to calculateDistance(nodes["Petroleum Block"]!!.location, nodes["Basketball Court"]!!.location),
            "Param Lab" to calculateDistance(nodes["Petroleum Block"]!!.location, nodes["Param Lab"]!!.location),
            "KP Nautiyal Block" to calculateDistance(nodes["Petroleum Block"]!!.location, nodes["KP Nautiyal Block"]!!.location)
        ),
        "Chanakya Block" to listOf(
            "Parking" to calculateDistance(nodes["Chanakya Block"]!!.location, nodes["Parking"]!!.location),
            "Gate 2" to calculateDistance(nodes["Chanakya Block"]!!.location, nodes["Gate 2"]!!.location),
            "KP Nautiyal Block" to calculateDistance(nodes["Chanakya Block"]!!.location, nodes["KP Nautiyal Block"]!!.location),
            "Petroleum Block" to calculateDistance(nodes["Chanakya Block"]!!.location, nodes["Petroleum Block"]!!.location)
        ),
        "Param Lab" to listOf(
            "Library" to calculateDistance(nodes["Param Lab"]!!.location, nodes["Library"]!!.location),
            "Aryabhatt Lab" to calculateDistance(nodes["Param Lab"]!!.location, nodes["Aryabhatt Lab"]!!.location),
            "Basketball Court" to calculateDistance(nodes["Param Lab"]!!.location, nodes["Basketball Court"]!!.location),
            "Petroleum Block" to calculateDistance(nodes["Param Lab"]!!.location, nodes["Petroleum Block"]!!.location),
            "B.Tech Block" to calculateDistance(nodes["Param Lab"]!!.location, nodes["B.Tech Block"]!!.location)
        ),
        "Vishwakarma Block" to listOf(
            "KP Nautiyal Block" to calculateDistance(nodes["Vishwakarma Block"]!!.location, nodes["KP Nautiyal Block"]!!.location),
            "Aryabhatt Lab" to calculateDistance(nodes["Vishwakarma Block"]!!.location, nodes["Aryabhatt Lab"]!!.location),
            "Priyadarshini Hostel" to calculateDistance(nodes["Vishwakarma Block"]!!.location, nodes["Priyadarshini Hostel"]!!.location)
        ),
        "KP Nautiyal Block" to listOf(
            "Petroleum Block" to calculateDistance(nodes["KP Nautiyal Block"]!!.location, nodes["Petroleum Block"]!!.location),
            "Chanakya Block" to calculateDistance(nodes["KP Nautiyal Block"]!!.location, nodes["Chanakya Block"]!!.location),
            "Gate 2" to calculateDistance(nodes["KP Nautiyal Block"]!!.location, nodes["Gate 2"]!!.location),
            "Parking" to calculateDistance(nodes["KP Nautiyal Block"]!!.location, nodes["Parking"]!!.location),
            "Vishwakarma Block" to calculateDistance(nodes["KP Nautiyal Block"]!!.location, nodes["Vishwakarma Block"]!!.location)
        ),
        "Gate 2" to listOf(
            "Parking" to calculateDistance(nodes["Gate 2"]!!.location, nodes["Parking"]!!.location),
            "Chanakya Block" to calculateDistance(nodes["Gate 2"]!!.location, nodes["Chanakya Block"]!!.location),
            "KP Nautiyal Block" to calculateDistance(nodes["Gate 2"]!!.location, nodes["KP Nautiyal Block"]!!.location),
            "Bus Stop" to calculateDistance(nodes["Gate 2"]!!.location, nodes["Bus Stop"]!!.location)
        ),
        "Mechanical Department" to listOf(
            "Parking" to calculateDistance(nodes["Mechanical Department"]!!.location, nodes["Parking"]!!.location)
        ),
        "St. Paul's High School" to listOf(
            "Medical" to calculateDistance(nodes["St. Paul's High School"]!!.location, nodes["Medical"]!!.location),
            "Gate 2" to calculateDistance(nodes["St. Paul's High School"]!!.location, nodes["Gate 2"]!!.location),
            "CS/IT Entrance Gate" to calculateDistance(nodes["St. Paul's High School"]!!.location, nodes["CS/IT Entrance Gate"]!!.location),
            "Bus Stop" to calculateDistance(nodes["St. Paul's High School"]!!.location, nodes["Bus Stop"]!!.location)
        ),
        "Priyadarshini Hostel" to listOf(
            "Vishwakarma Block" to calculateDistance(nodes["Priyadarshini Hostel"]!!.location, nodes["Vishwakarma Block"]!!.location),
            "Aryabhatt Lab" to calculateDistance(nodes["Priyadarshini Hostel"]!!.location, nodes["Aryabhatt Lab"]!!.location)
        ),
        "Bus Stop" to listOf(
            "Gate 1" to calculateDistance(nodes["Bus Stop"]!!.location, nodes["Gate 1"]!!.location),
            "Gate 2" to calculateDistance(nodes["Bus Stop"]!!.location, nodes["Gate 2"]!!.location),
            "St. Paul's High School" to calculateDistance(nodes["Bus Stop"]!!.location, nodes["St. Paul's High School"]!!.location),
            "Medical" to calculateDistance(nodes["Bus Stop"]!!.location, nodes["Medical"]!!.location),
            "CS/IT Entrance Gate" to calculateDistance(nodes["Bus Stop"]!!.location, nodes["CS/IT Entrance Gate"]!!.location)
        ),
        "CS/IT Entrance Gate" to listOf(
            "CS/IT Block" to calculateDistance(nodes["CS/IT Entrance Gate"]!!.location, nodes["CS/IT Block"]!!.location),
            "Gate 1" to calculateDistance(nodes["CS/IT Entrance Gate"]!!.location, nodes["Gate 1"]!!.location),
            "St. Paul's High School" to calculateDistance(nodes["CS/IT Entrance Gate"]!!.location, nodes["St. Paul's High School"]!!.location),
            "Bus Stop" to calculateDistance(nodes["CS/IT Entrance Gate"]!!.location, nodes["Bus Stop"]!!.location),
            "Medical" to calculateDistance(nodes["CS/IT Entrance Gate"]!!.location, nodes["Medical"]!!.location)
        ),
        "Flag Spot" to listOf(
            "B.Tech Block" to calculateDistance(nodes["Flag Spot"]!!.location, nodes["B.Tech Block"]!!.location),
            "PlayGround" to calculateDistance(nodes["Flag Spot"]!!.location, nodes["PlayGround"]!!.location),
            "Cafe & Gym" to calculateDistance(nodes["Flag Spot"]!!.location, nodes["Cafe & Gym"]!!.location),
            "Badminton Court" to calculateDistance(nodes["Flag Spot"]!!.location, nodes["Badminton Court"]!!.location),
            "Tuck Shop" to calculateDistance(nodes["Flag Spot"]!!.location, nodes["Tuck Shop"]!!.location)
        ),
        "Bridge" to listOf(
            "B.Tech Block" to calculateDistance(nodes["Bridge"]!!.location, nodes["B.Tech Block"]!!.location),
            "PlayGround" to calculateDistance(nodes["Bridge"]!!.location, nodes["PlayGround"]!!.location),
            "Cafe & Gym" to calculateDistance(nodes["Bridge"]!!.location, nodes["Cafe & Gym"]!!.location),
            "Badminton Court" to calculateDistance(nodes["Bridge"]!!.location, nodes["Badminton Court"]!!.location),
            "Tuck Shop" to calculateDistance(nodes["Bridge"]!!.location, nodes["Tuck Shop"]!!.location)
        ),
        "Gully" to listOf(
            "PlayGround" to calculateDistance(nodes["Gully"]!!.location, nodes["PlayGround"]!!.location),
            "Cafe & Gym" to calculateDistance(nodes["Gully"]!!.location, nodes["Cafe & Gym"]!!.location),
            "Bridge" to calculateDistance(nodes["Gully"]!!.location, nodes["Bridge"]!!.location)
        )
    )
}