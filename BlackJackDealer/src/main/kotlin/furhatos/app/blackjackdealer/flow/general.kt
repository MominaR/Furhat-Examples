package furhatos.app.blackjackdealer.flow

import furhatos.flow.kotlin.*
import furhatos.util.*
import furhatos.app.blackjackdealer.nlu.*
import furhatos.nlu.common.Goodbye
import furhatos.gestures.Gestures



// Variables or texts which may be called more
val rules = utterance {
    +"Sure!"
    +Gestures.Smile
    +"The goal of Blackjack is to have a hand that totals higher than the dealer's, "
    +"but is not higher than 21."
    +" If your hand's total is higher than 21, it is called a bust "
    +"which means you are out."
    +"When the game starts, I will deal 2 face up cards to you,"
    +"and 2 face up and face down card to myself."
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
        goto(Greet)
    }
    onUserLeave(instant = true) {
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

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

}