package com.example.supportmesh

object SharedRequests {
    val requests = mutableListOf<RequestItem>()

    public fun loadRequests() {
        // Fill with sample requests. This can be changed to read from a central db.
        requests.add(
            RequestItem(
                user = "admin",
                lat = 51.47290,
                long = -0.08315,
                title = "Help moving",
                description = "Moving to Coldharbour Lane Call Fred on 0800 123456"
            ))

        requests.add(
            RequestItem(
                user = "Sally",
                lat = 51.47603,
                long = -0.07923,
                title = "Lift to Kings Hospital",
                description = "Need lift to my appointment on Tuesday at 12:15 call Sally on 015 434 45"
            ))

        requests.add(
            RequestItem(
                user = "George",
                lat = 51.47010,
                long = -0.07741,
                title = "Play Games",
                description = "Want someone to play fortnite with."
            ))
    }

    public fun saveRequest(request: RequestItem) {
        // Just add the request to the collection. This can be changed to save it to a central db so all users can see it.
        requests.add(request)
    }
}

data class RequestItem(
    val user: String,
    val lat: Double,
    val long: Double,
    val title: String,
    val description: String
)