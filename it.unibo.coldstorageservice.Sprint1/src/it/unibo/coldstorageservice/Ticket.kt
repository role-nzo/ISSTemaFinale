package it.unibo.coldstorageservice

data class Ticket(val id: String, val creationTime: Long, val fw: Int) {

    companion object{
        @JvmStatic
        fun getRandomId() : String {
            val length = 5
            val allowedChars = ('A'..'Z') + ('a'..'z')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

    }



}