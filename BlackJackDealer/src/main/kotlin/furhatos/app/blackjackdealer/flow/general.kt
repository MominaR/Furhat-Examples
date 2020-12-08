package furhatos.app.blackjackdealer.flow

import furhatos.flow.kotlin.*
import furhatos.util.*
import furhatos.app.blackjackdealer.nlu.*
import furhatos.nlu.common.Goodbye
import furhatos.gestures.Gestures


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

val rule1 = utterance {
    Gestures.Smile
    +"The goal of Blackjack is to have a hand that totals higher than mine, "
    +"but is not higher than 21."
}


val rule2 = utterance {
    Gestures.Smile
    +"If your hand's total is higher than 21,"
    +"you are busted,"
    +"meaning you are out."
}

val rule3 = utterance {
    Gestures.Smile
    +"When we start, I will deal two face up cards to you,"
    +"and one face up and one face down card to me."
}

val RuleHit = utterance {
    Gestures.Smile
    +" In this case you ask for another card."
    + "You should be careful though."
    + "If your score is close to 21 you have more chances to loose."
}

val RuleBust = utterance {
    Gestures.Smile
    +" You loose. "
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
    onResponse<RequestRule1> {
        furhat.say(rule1)
        // Add the whole list of rules and decide how will we handle utterances.
        reentry()
    }
    onResponse<RequestRule2> {
        furhat.say(rule2)
        // Add the whole list of rules and decide how will we handle utterances.
        reentry()
    }

    onResponse<ExplainHit> {
        furhat.say(RuleHit)
        // Add the whole list of rules and decide how will we handle utterances.
        reentry()
    }

    onResponse<ExplainBust> {
        furhat.say(RuleBust)
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
        furhat.say("I will attend you later.")
        furhat.attend(currentUser)
        reentry()
    }

}
