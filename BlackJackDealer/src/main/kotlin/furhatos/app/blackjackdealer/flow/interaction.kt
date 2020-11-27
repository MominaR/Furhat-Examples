package furhatos.app.blackjackdealer.flow

import furhatos.app.blackjackdealer.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language
import furhatos.app.blackjackdealer.nlu.*

val Greet : State = state(Interaction) {

    onEntry {
        random(
                {furhat.ask("Hi there! Welcome to Cameo Club table! Do you want to play Black Jack?") },
                {furhat.ask("Oh, Hello! Welcome to the Black Jack table! Do you wanna play?")}
        )

    }

    onResponse<Yes>{
        furhat.say("Oh, that is great!")
        goto(PlayingARound)
    }

    onResponse<No>{
        furhat.say("That's sad. You are very welcome to spectate.")
    }
}

val PlayingARound : State = state(Interaction) {
    onEntry {
        random(
                { furhat.ask("Do you want me to tell you the rules for the game?") }
        )
    }

    onResponse<Yes> {
        furhat.say(rules)
    }

    onResponse<No> {
        furhat.say("Okay, Let's start playing the game.")
        goto(Idle)
    }
}