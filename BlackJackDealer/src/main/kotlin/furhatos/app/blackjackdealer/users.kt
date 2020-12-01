package furhatos.app.blackjackdealer.flow

import furhatos.records.Record
import furhatos.records.User
import furhatos.skills.UserManager
import javax.jws.soap.SOAPBinding

// Here we need to add the user specific data e.g. score of one user

class UserHand(
        var score : Int = 0,
        var cards : MutableList<String> = mutableListOf<String>()
        //var playing: Boolean = false,
        //var played : Boolean = false,
) : Record()

//Cus
val User.hand : UserHand
    get() = data.getOrPut(UserHand::class.qualifiedName, UserHand())
