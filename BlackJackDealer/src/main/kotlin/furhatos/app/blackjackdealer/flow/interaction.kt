package furhatos.app.blackjackdealer.flow

import furhatos.app.blackjackdealer.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language
import furhatos.app.blackjackdealer.nlu.*


var score = (1..10).random()
fun setScore(){
    ++score
}

val Greet : State = state(Interaction) {

    onEntry {
        random(
                {furhat.ask("Hi there! Welcome to Cameo Club table! Do you want to play Black Jack?") },
                {furhat.ask("Oh, Hello! Welcome to the Black Jack table! Do you wanna play?")}
        )

    }

    onResponse<Yes>{
        furhat.say("Oh, that is great!")
        goto(AskForRules)
    }

    onResponse<No>{
        furhat.say("That's sad. You are very welcome to spectate.")
    }
}

val AskForRules : State = state(Interaction) {
    onEntry {
        random(
                { furhat.ask("Do you want me to tell you the rules for the game?") }
        )
    }

    onResponse<Yes> {
        furhat.say(rules)
        furhat.say("Now, you know the rules, let's play!")
        goto(PlayingARound)
    }

    onResponse<No> {
        furhat.say("Okay, Let's start playing the game.")
        goto(PlayingARound)
    }
}

fun GenerateACard(): String {
    val rand1 = (0..3).random()
    val rand2 = (0..12).random()
    val suits = listOf("Hearts", "Clubs", "Diamonds", "Spades")
    val cardNames = listOf("Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King")
    return(cardNames[rand2] + " of " + suits[rand1])
}

val PlayingARound : State = state(Interaction) {

    onEntry {
        // Randomnly generating the cards
        furhat.say("Your first card is " + GenerateACard())
        furhat.say("Your second card is " + GenerateACard())
        furhat.say("My card is " + GenerateACard())
        furhat.ask("What is your move?")
        users.current.hand.score
        //setScore()

    }
    onReentry {
        furhat.ask("What is your next move? Hit or Stand?")
    }
    onResponse<Hit> {
        furhat.say("Your next card is " + GenerateACard())
        furhat.ask("What is your next move?")
        //setScore()
    }
    onResponse<Stand> {
        furhat.say("Okay, my second card is " + GenerateACard())
        furhat.say("Your score is $score")
        var temp = score+1
        furhat.say("Your score is now ${users.current.hand.score}")
        furhat.say("The game is over!")
        goto(Idle)
    }


}