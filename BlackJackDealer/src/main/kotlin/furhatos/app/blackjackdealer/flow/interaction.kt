package furhatos.app.blackjackdealer.flow

import furhatos.app.blackjackdealer.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language
import furhatos.app.blackjackdealer.nlu.*

var furhatHand = Hand()

val Greet : State = state(Interaction) {

    onEntry {
        random(
                { furhat.ask("Hi there! Welcome to Cameo Club table! Do you want to play Black Jack?") },
                { furhat.ask("Oh, Hello! Welcome to the Black Jack table! Do you wanna play?")}
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

fun GenerateCard(): Card {
    val value = (1..13).random()
    val suit = (1..4).random()
    return Card(value, suit)
}

val PlayingARound : State = state(Interaction) {
    onEntry {
        // Randomnly generating the cards
        users.current.hand.addCard(GenerateCard())
        users.current.hand.addCard(GenerateCard())
        furhat.say("Your first card is ${users.current.hand.getCard(0).toText()}")
        furhat.say("Your second card is ${users.current.hand.getCard(1).toText()}")
        furhatHand.addCard(GenerateCard())
        furhat.say("My face up card is ${furhatHand.getCard(0).toText()}")
        furhat.ask("What is your move?")
    }

    onReentry {
        furhat.ask("What is your next move? Hit or Stand?")
    }

    onResponse<Hit> {
        users.current.hand.addCard(GenerateCard())
        val userScore = users.current.hand.getScore()
        furhat.say("Your next card is ${users.current.hand.getCard(-1).toText()}, which makes your current score $userScore")
        if (userScore > 21) {
            furhat.say("You busted! Come back another time!")
            goto(Idle)
        }
        furhat.ask("What is your next move?")
    }

    onResponse<Stand> {
        furhatHand.addCard(GenerateCard())
        furhat.say("My face down card was ${furhatHand.getCard(-1).toText()}")
        var furhatScore = furhatHand.getScore()
        furhat.say("My current score is $furhatScore")
        while (furhatScore < 17) {
            furhatHand.addCard(GenerateCard())
            furhat.say("I took another card. It was ${furhatHand.getCard(-1).toText()}")
            furhatScore = furhatHand.getScore()
            furhat.say("That makes my current score $furhatScore")
        }

        val userScore = users.current.hand.getScore()
        if (furhatScore > 21) {
            furhat.say("I busted! You win!")
        } else if (userScore > furhatScore) {
            furhat.say("Your score was higher than mine. You win!")
        } else if (userScore < furhatScore) {
            furhat.say("My score was higher than yours. You lose!")
        } else {
            furhat.say("Our scores were equal. It's a draw!")
        }
        goto(Idle)
    }


}