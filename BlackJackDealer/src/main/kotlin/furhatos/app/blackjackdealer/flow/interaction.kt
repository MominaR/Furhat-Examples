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
      goto(AskForName )
    }

    onResponse<No>{
        furhat.say("That's sad. You are very welcome to spectate.")
    }

    onResponse<Hit>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onResponse<Stand>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onReentry {
        furhat.ask("Do you wanna play?")
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
        goto(AskForGame)
    }

}

val AskForGame : State=state(Interaction){
    onEntry {
        random(
                { furhat.ask("Do you know Blackjack?") }
        )
    }
    onResponse<Yes> {
        furhat.say("Great!")
        goto(AskForRulesAdvanced)

    }

    onResponse<No> {
        furhat.say(rule1)
        goto(AskForRulesBeginner)
    }

    onResponse<Hit>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onResponse<Stand>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onReentry {
        furhat.say("Oh, you know some basic concepts.")
        delay(200)
        furhat.ask("Have you played Blackjack before?")
    }
}

val AskForRulesBeginner : State=state(Interaction){
    onEntry {
        random(
                { furhat.ask("Do you want me to tell you the rules?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Sure!")
        delay(200)
        furhat.say(rule2)
        goto(AskForComprehensionRule2)
    }

    onResponse<No> {
        furhat.say("Okay, Let's start playing the game. You can always ask for the rules during the game.")
        goto(PlayingARound)
    }

    onResponse<Hit>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onResponse<Stand>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onReentry {
        furhat.say("You know some basic concepts.")
        delay(200)
        furhat.say("Cool!")
        delay(200)
        furhat.ask("But, what about the rules?")
    }

}

val AskForRulesAdvanced : State=state(Interaction){
    onEntry {
        random(
                { furhat.ask("Do you want me to tell you the rules in my table?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Okay!")
        delay(100)
        furhat.say("As you know in Blackjack you should have a hand higher than mine, but less than 21!")
        furhat.say(rule2)
        delay(500)
        furhat.say(rule3)
        delay(500)
        goto(AskForBeingReadyToPlay)
    }

    onResponse<No> {
        furhat.say("Okay! Let's not lose more time!")
        goto(PlayingARound)
    }

    onResponse<Hit>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onResponse<Stand>{
        furhat.say("Hold your horses!")
        delay(200)
        furhat.say("The game has not started yet!")
        reentry()
    }

    onReentry {
        furhat.ask("Do you want me to explain the Rules?")
    }

}

val AskForComprehensionRule2 : State=state(Interaction){
    onEntry {
        random(
                { furhat.ask("Do you follow Rule 2?") },
                { furhat.ask("Got it?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Great!")
        delay(200)
        furhat.say(rule3)
        goto(AskForComprehensionRule3)
    }

    onResponse<No> {
        furhat.say("No worries! Let me repeat!")
        furhat.say(rule2)
        reentry()
    }



    onReentry {
        delay(200)
        furhat.ask("Do you get rule 2 now?")
    }


}

val AskForComprehensionRule3 : State=state(Interaction){
    onEntry {
        random(
                { furhat.ask("Do you follow?") },
                { furhat.ask("Got it?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Great!")
        goto(AskForBeingReadyToPlay)
    }

    onResponse<No> {
        furhat.say("No problem!I will repeat it!")
        delay(200)
        furhat.say(rule3)
        reentry()
    }


    onReentry {
        furhat.ask("Do you understand rule 3 now?")
    }

}

val AskForBeingReadyToPlay : State=state(Interaction){
    onEntry {
        random(
                { furhat.ask("Are you ready to play?") }
        )
    }

    onResponse<Yes> {
        furhat.say("Let's play, then!")
        goto(PlayingARound)
    }

    onResponse<No> {
        furhat.say("Ohh, okay!")
        goto(AskForRulesAdvanced)
    }


}


/*val AskForRules : State = state(Interaction) {
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
}*/

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

    onResponse<RequestSpecificCard> {
        Gestures.GazeAway
        random(
                {furhat.say("Don't try that here, my boss is a bad guy.") },
                {furhat.say("This is not a place to cheat!") },
                {furhat.say("I can't give you the card you want.") }

        )
        furhat.say("I will pretend I didn't hear that.")
        reentry()
    }

    onResponse<RequestHelp> {
        val userScore = users.current.hand.getScore()
        var furhatScore = furhatHand.getScore()
        Gestures.Thoughtful
        furhat.say("Your score is $userScore.")
        furhat.say("My current score is $furhatScore")
        if(userScore > 16){
            furhat.say("The optimal move would be Stand")
        } else if (userScore >= 13 && userScore <= 16){
            if (furhatScore > 6){
                furhat.say("The optimal move would be Hit")
            } else {
                furhat.say("The optimal move would be Stand")
            }
        } else if (userScore == 12) {
            if (furhatScore > 3 && furhatScore < 7) {
                furhat.say("The optimal move would be Stand")
            } else {
                furhat.say("The optimal move would be Hit")
            }
        } else {
            furhat.say("The optimal move would be Hit")
        }

        reentry()
    }

    onResponse<Surrender> {
        furhat.say("Better luck next time.")
        goto(EndOfRound)
    }

    onResponse<TellMyScore> {
        val userScore = users.current.hand.getScore()
        furhat.say("Your score is $userScore.")
        reentry()
    }
    onResponse<TellFurhatScore> {
        var furhatScore = furhatHand.getScore()
        furhat.say("My current score is $furhatScore")
        reentry()
    }
    onResponse<TellMyCards> {
        furhat.say("Your cards are")
        for (card in users.current.hand.cards){
            furhat.say(card.toText())
        }
        reentry()
    }
    onResponse<TellFurhatCard> {
        furhat.say("My face up card is ${furhatHand.getCard(0).toText()}")
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

    onResponse<Play> {
        furhat.say("Great! Let's go!")
        goto(PlayingARound)
    }


    onResponse<No> {
        furhat.say("OK! See you next time!")
        goto(Idle)
    }


}