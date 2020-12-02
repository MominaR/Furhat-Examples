package furhatos.app.blackjackdealer.flow

import furhatos.flow.kotlin.*
import furhatos.util.*
import furhatos.app.blackjackdealer.nlu.*
import furhatos.nlu.common.Goodbye
import furhatos.gestures.Gestures


var listofPlayers = ListofUsers()


// Variables or texts which may be called more
val rules = utterance {
    Gestures.Smile
    +"The goal of Blackjack is to have a hand that totals higher than the dealer's, "
    +"but is not higher than 21."
    delay(200)
    +" If your hand's total is higher than 21, it is called a bust "
    +"which means you are out."
    +"When the game starts, I will deal two face up cards to you,"
    +"and one face up and one face down card to myself."
}

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Greet)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter(instant = true) {
        furhat.attend(it)
        listofPlayers.addUserId(it.id)
        goto(Greet)
    }
    onUserLeave(instant = true) {
        listofPlayers.removeUserId(it.id)
        if (users.count > 0) {
            furhat.attend(users.other)
        } else {
            furhat.attendNobody()
        }
    }
}

val Interaction: State = state {
    onResponse<RequestRules> {
        furhat.say(rules)
        // Add the whole list of rules and decide how will we handle utterances.
        reentry()
    }
    onResponse<Goodbye> {
        // Add conditions if we are in between a round then say ohh, you may loose if you leave now.
        // Otherwise say this:
        furhat.say("It was nice talking to you. Have a nice day!")
        goto(Idle)
    }

    onUserLeave(instant = true) {
        // If the user leaves without saying good bye then we have to stop counting scores and end.
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(Greet)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter() {
        var currentUser = users.current.id
        furhat.attend(it)
        listofPlayers.addUserId(it.id)
        furhat.say("I will attend you later.")
        furhat.say("I have two players now ${listofPlayers.getPlayer(0)} and ${listofPlayers.getPlayer(1)}")
        furhat.attend(currentUser)
        reentry()
    }

}