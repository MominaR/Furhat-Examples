package furhatos.app.blackjackdealer.flow

import furhatos.app.blackjackdealer.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language
import furhatos.app.blackjackdealer.nlu.*
import furhatos.gestures.Gestures

var furhatHand = Hand()


val Greet : State = state(Interaction) {
    onEntry {
        furhat.ask({ random {
            +"Hi there ! Welcome to this Cameo Club table! Do you want to play BlackJack?"
            +"Oh, Hello! Welcome to the BlackJack table! Do you wanna play?"
        } })
    }

    onResponse<Yes>{
        furhat.say { random {
            +"That's great!"
            +"Spectacular!"
        } }
        goto(AskForName)
    }

    onResponse<No>{
        furhat.say("That's sad. You are very welcome to spectate.")
    }
}

val AskForName : State = state(Interaction) {
    onEntry {
        random(
                { furhat.ask("What is your name?") }
        )
    }

    onResponse {
        users.current.info.setUserName(it.text)
        furhat.say("Nice name ${users.current.info.getUserName()}")
        goto(AskForRules)
    }

}

val AskForRules : State = state(Interaction) {
    onEntry {
        random(
                { furhat.ask("Do you want me to tell you the rules for the game?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Sure!")
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
        furhatHand.clearHand()
        users.current.hand.clearHand()
        // Randomnly generating the cards
        users.current.hand.addCard(GenerateCard())
        users.current.hand.addCard(GenerateCard())
        val userScore = users.current.hand.getScore()
        furhat.say("Your first card is ${users.current.hand.getCard(0).toText()}")
        furhat.say("Your second card is ${users.current.hand.getCard(1).toText()}")
        if (userScore == 21) {
            furhat.gesture(Gestures.Smile)
            random(
                    {furhat.say("Wow, you got BlackJack! That'll be hard to beat!") },
                    {furhat.say("You're on a roll!") }
            )
            goto(PlayDealersHand)
        }
        random(
                {furhat.say("Your score is $userScore.") },
                {furhat.say("This totals $userScore.") }
        )
        furhatHand.addCard(GenerateCard())
        furhat.say("My face up card is ${furhatHand.getCard(0).toText()}")
        furhat.ask("What is your move?")
    }

    onReentry {
        random(
                {furhat.ask("What is your move?")},
                {furhat.ask("What is your next move?")},
                {furhat.ask("What is your next move? Hit or Stand?")},
                {furhat.ask("What do you want to do?")}
        )
    }

    onResponse<Hit> {
        users.current.hand.addCard(GenerateCard())
        val userScore = users.current.hand.getScore()
        furhat.say("Your next card is ${users.current.hand.getCard(-1).toText()}, which makes your current score $userScore")
        if (userScore > 21) {
            furhat.say("You busted! You lose!")
            goto(EndOfRound)
        } else if (userScore == 21) {
            random(
                    {furhat.say("Wow, you got BlackJack! That'll be hard to beat!") }
            )
            goto(PlayDealersHand)
        } else {
            furhat.ask("What is your next move?")
        }
    }

    onResponse<Stand> {
        furhat.say("Very well!")
        goto(PlayDealersHand)
    }

    onResponse<RequestOptions> {
        furhat.say("For another card, say 'hit'.")
        furhat.say("Otherwise, say 'stand'.")
        reentry()
    }
}

val PlayDealersHand : State = state(Interaction) {
    onEntry {
        furhatHand.addCard(GenerateCard())
        furhat.say("My face down card was ${furhatHand.getCard(-1).toText()}")
        var furhatScore = furhatHand.getScore()
        furhat.say("My current score is $furhatScore")
        while (furhatScore < 17) {
            furhatHand.addCard(GenerateCard())
            furhat.say("I will take another card. It is ${furhatHand.getCard(-1).toText()}")
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

        goto(EndOfRound)
    }
}

val EndOfRound : State = state(Interaction) {
    onEntry {
        random(
                { furhat.ask("Do you want to play another round?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Great! Let's go!")
        goto(PlayingARound)
    }

    onResponse<No> {
        furhat.say("OK! See you next time!")
        goto(Idle)
    }
}