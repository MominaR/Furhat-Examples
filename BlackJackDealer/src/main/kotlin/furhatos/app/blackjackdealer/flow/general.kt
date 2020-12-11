package furhatos.app.blackjackdealer.flow

import furhatos.flow.kotlin.*
import furhatos.util.*
import furhatos.app.blackjackdealer.nlu.*
import furhatos.nlu.common.Goodbye
import furhatos.gestures.Gestures

val rule1 = utterance {
    Gestures.Smile
    +"The goal of Blackjack is to have a hand that totals higher than mine, "
    +"but is not higher than 21. Cards 2 to 10 are worth their value,"
    delay(200)
    +"Jacks, Queens, and Kings are worth 10,"
    delay(200)
    +"and an Ace is worth 1 or 11, whichever gets you closer to 21 without surpassing it."
}


val rule2 = utterance {
    Gestures.Smile
    +"If your hand's total is higher than 21,"
    +"you are busted,"
    +"meaning you are out."
}

val rule2comprehensive = utterance {
    Gestures.Smile
    +"If your hand's total is higher than 21,"
    +"we say that you busted, and you lose."
}

val rule3 = utterance {
    Gestures.Smile
    +"When we start, I will deal two face up cards to you,"
    +"and one face up and one face down card to myself."
    delay(200)
    +"I will then ask for your move. If you want another card,"
    +"you say Hit!, otherwise you say Stand!"
}

val rule3comprehensive = utterance {
    Gestures.Smile
    +"When we start, I will give you two cards that you may look at,"
    +"and I will deal one card to myself that I show you, and one that I leave upside down on the table."
    delay(200)
    +"I will then ask for your move. If you want another card,"
    +"you say Hit!, otherwise you say Stand!"
}

val rule4 = utterance {
    Gestures.Smile
    +"Unless you have busted, I will play out my hand."
    delay(200)
    +"I will do this by taking additional cards until my score is 17 or more."
}

val rule4comprehensive = utterance {
    Gestures.Smile
    +"If you have chosen to stand and not yet busted, I will also play out my hand."
    delay(200)
    +"I will do this according to a pre-defined strategy,"
    +"where I will hit until my score is 17 or more,"
    delay(200)
    +"no matter what your score is."
}

val RuleHit = utterance {
    Gestures.Smile
    +"You say hit to ask for another card."
    +"You should be careful though."
    +"If your score is close to 21 you have a higher chance to lose by taking another card."
    +"But you also have a higher chance of winning. Quite a paradox, is it not?"
}

val RuleStand = utterance {
    Gestures.Smile
    +"You say stand when you don't want another card."
    +"Your score is then final. Once you've decided to stand,"
    +"I will deal cards to myself and we see who ends up closer to 21."
}

val RuleBust = utterance {
    Gestures.Smile
    +"You lose."
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
        if (users.count > 1) {
            furhat.attend(users.other)
            goto(Greet)
        } else {
            furhat.attendNobody()
        }
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
        furhat.say(rule1)
        furhat.say { random {
            +"You should also know what hit, stand, and bust means. If you don't, ask me what they are and I will tell you."
            +"If you don't know what a hit, stand, or bust is, ask me and I will tell you."
        } }
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
    onResponse<ExplainStand> {
        furhat.say(RuleStand)
        // Add the whole list of rules and decide how will we handle utterances.
        reentry()
    }

    onResponse<Goodbye> {
        // Add conditions if we are in between a round then say ohh, you may loose if you leave now.
        // Otherwise say this:
        furhat.say("It was nice talking to you. Have a nice day!")
        goto(Idle)
    }

    onResponse<Hit>{
        furhat.say { random {
            +"Hold your horses! The game has not started yet!"
            +"Woah, someone is feeling lucky today! We'll start soon!"
            +"You were hit? Oh, you mean the game move! Just a moment, and we'll get the game going!"
        } }
        reentry()
    }

    onResponse<Stand>{
        furhat.say { random {
            +"Hold your horses! The game has not started yet!"
            +"I'm already standing..."
        } }
        reentry()
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
        furhat.say { random {
            +"You'll get to play next round!"
            +"I'll attend you later!"
        } }
        furhat.attend(currentUser)
        reentry()
    }

}
