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
        furhat.ask { random {
            +"Hi there ! Welcome to this Cameo Club table! Do you want to play BlackJack?"
            +"Greetings! Welcome to this Blackjack table! Do you wanna play?"
        } }
    }

    onResponse<Yes>{
        furhat.say { random {
            +"That's great!"
            +"Spectacular!"
        } }
      goto(AskForName )
    }

    onResponse<No>{
        furhat.say { random {
            +"That's sad! Please, stay and spectate, if you wish!"
            +"This table has been hot all day. It's a shame you'll miss out!"
            +"Not a gambler, huh? Well, you're welcome back at any time!"
        } }
    }

    onReentry {
        furhat.ask { random {
            +"Do you want to play?"
            +"Would you like to play?"
            +"It's been a long night. Do you want to play? Yes or no?"
        } }
    }

}

val AskForName : State = state(Interaction) {
    onEntry {
        furhat.ask { random {
            +"What is your name?"
            +"My name is Furhat. What is yours?"
            +"Before we start, what is your name?"
            +"You look like someone with a very exciting name. What is it??"
        } }
    }

    onResponse {
        users.current.info.setUserName(it.text)
        furhat.say { random {
            +"Nice name ${users.current.info.getUserName()}"
            +"${users.current.info.getUserName()}, what a lovely name!"
            +"${users.current.info.getUserName()}? Did I hear that correctly? No matter, I'll just call you that from now on!"
        } }
        goto(AskForGame)
    }

}

val AskForGame : State=state(Interaction){
    onEntry {
        furhat.ask { random {
            +"So, ${users.current.info.getUserName()}, are you familiar with the beautiful game that is Blackjack?"
            +"Do you know Blackjack, ${users.current.info.getUserName()}?"
        } }
    }

    onResponse<Yes> {
        furhat.say { random {
            +"Okay, Let's start the game! You can always ask for the rules as we play."
            +"Swell! Let's get this game underway!"
            +"I thought so! You look like a pro! Let's start playing, shall we?"
        } }
        goto(PlayingARound)
    }

    onResponse<No> {
        furhat.say { random {
            +"A beginner! It's a pleasure to have you here, ${users.current.info.getUserName()}! I think you'll enjoy Blackjack!"
            +"I see!"
        } }
        goto(AskForRulesBeginner)
    }

    onReentry {
        furhat.ask { random {
            +"Have you played Blackjack before?"
            +"Are you familiar with the rules of Blackjack?"
        } }
    }
}

val AskForRulesBeginner : State=state(Interaction){
    onEntry {
        furhat.ask { random {
            +"Do you know the rules?"
            +"Do you want me to tell you the rules?"
        } }
    }

    onResponse<Yes> {
        furhat.say { random {
            +"Sure!"
            +"Certainly!"
        } }
        furhat.say(rule1)
        delay(200)
        furhat.say(rule2)
        goto(AskForComprehensionRule2)
    }

    onResponse<No> {
        furhat.say { random {
            +"Okay, let's start the game!"
            +"Swell! Let's get this game underway!"
            +"I thought so! You look like a pro! Let's start playing, shall we?"
        } }
        goto(PlayingARound)
    }

    onReentry {
        furhat.ask("Now, what about the rules? Do you want me to explain them?")
    }

}

val AskForComprehensionRule2 : State=state(Interaction){
    onEntry {
        furhat.ask { random {
            +"Do you know what a bust is now?"
            +"Got it?"
        } }
    }

    onResponse<Yes> {
        furhat.say("Great!")
        delay(200)
        furhat.say(rule3)
        goto(AskForComprehensionRule3)
    }

    onResponse<No> {
        furhat.say { random {
            +"No worries, ${users.current.info.getUserName()}, let me repeat!"
            +"Not to worry! I have some pedagogical experience. Let me explain it more clearly!"
        } }
        furhat.say(rule2comprehensive)
        reentry()
    }

    onReentry {
        furhat.say { random {
            +"Are you familiar with the term bust now, ${users.current.info.getUserName()}?"
            +"Do you follow?"
        } }
    }
}

val AskForComprehensionRule3 : State=state(Interaction){
    onEntry {
        furhat.ask { random {
            +"Get it?"
            +"Do you follow?"
        } }
    }

    onResponse<Yes> {
        furhat.say("Great!")
        goto(AskForBeingReadyToPlay)
    }

    onResponse<No> {
        furhat.say { random {
            +"No problem, I will repeat it!"
            +"Okey, let me repeat!"
        } }
        delay(200)
        furhat.say(rule3comprehensive)
        reentry()
    }

    onReentry {
        furhat.ask { random {
            +"Do you understand how the game is played now?"
            +"Are you following?"
        } }
    }

}

val AskForBeingReadyToPlay : State=state(Interaction){
    onEntry {
        furhat.ask { random {
            +"Are you ready to play?"
            +"I'm eager to play. Are you ready?"
            +"So, should we try your luck?"
        } }
    }

    onResponse<Yes> {
        furhat.say { random {
            +"Great, let's start the game!"
            +"Swell! Let's get this game underway!"
        } }
        furhat.say("Good luck, ${users.current.info.getUserName()}")
        goto(PlayingARound)
    }

    onResponse<No> {
        furhat.say("I see!")
        goto(AskForRulesBeginner)
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
        if (users.current.hand.getCard(0).getCardScore() >= 10) {
            furhat.say { random {
                +"Your first card is a ${users.current.hand.getCard(0).toText()}. What a start, ${users.current.info.getUserName()}!"
                +"You're lucky today! Your first card is ${users.current.hand.getCard(0).toText()}!"
            } }
        } else {
            furhat.say { random {
                +"Your first card is ${users.current.hand.getCard(0).toText()}. Not too exciting!"
                +"Your first card is just ${users.current.hand.getCard(0).toText()}!"
            } }
        }
        furhat.say { random {
            +"Your second card is ${users.current.hand.getCard(1).toText()}!"
            +"Let's see how lucky you get with the second card. It's ${users.current.hand.getCard(1).toText()}!"
            +"I wonder what your second card is... Oh, ${users.current.hand.getCard(1).toText()}!"
            +"What might your second card be... ${users.current.hand.getCard(1).toText()}!"
        } }
        if (userScore == 21) {
            furhat.gesture(Gestures.Smile)
            furhat.say { random {
                +"Wow, ${users.current.info.getUserName()}, you got BlackJack! That'll be hard to tie!"
                +"Blackjack! You're on a roll, ${users.current.info.getUserName()}!"
            } }
            goto(PlayDealersHand)
        }
        furhat.say { random {
            +"That means your current score is $userScore."
            +"This totals $userScore."
        } }
        furhatHand.addCard(GenerateCard())
        furhat.say { random {
            +"My face up card is ${furhatHand.getCard(0).toText()}."
            +"Seems my face up card is ${furhatHand.getCard(0).toText()}."
        } }
        furhat.ask { random {
            +"What is your move, ${users.current.info.getUserName()}?"
            +"What would you like to do? Hit or stand?"
            +"What's your move?"
            +"What do you want to do?"
        } }
    }

    onReentry {
        furhat.ask { random {
            +"What is your move, ${users.current.info.getUserName()}?"
            +"What would you like to do? Hit or stand?"
            +"What's your move?"
            +"What do you want to do?"
            +"You know the score. What's your move?"
        } }
    }

    onResponse<Hit> {
        users.current.hand.addCard(GenerateCard())
        val userScore = users.current.hand.getScore()
        furhat.say { random {
            +"Your next card is ${users.current.hand.getCard(-1).toText()}, which makes your current score $userScore."
            +"Your next card is... ${users.current.hand.getCard(-1).toText()}! That makes your current score $userScore."
            +"Hit? I'm always excited when people hit! Your next card is ${users.current.hand.getCard(-1).toText()}! That makes your current score $userScore."
            +"${users.current.hand.getCard(-1).toText()}. That makes your current score $userScore."
        } }
        if (userScore > 21) {
            furhat.say { random {
                +"You busted! You lose!"
                +"I'm sorry, ${users.current.info.getUserName()}, that means you busted!"
                +"Poof! I'm afraid you busted, ${users.current.info.getUserName()}! It was a fun round, though!"
            } }
            goto(EndOfRound)
        } else if (userScore == 21) {
            furhat.say { random {
                +"Wow, ${users.current.info.getUserName()}, you got BlackJack! That'll be hard to tie!"
                +"Blackjack! You're on a roll, ${users.current.info.getUserName()}! Let's see if I can get the same score..."
            } }
            goto(PlayDealersHand)
        } else {
            furhat.ask { random {
                +"What is your next move, ${users.current.info.getUserName()}?"
                +"What would you like to do next? Hit or stand?"
                +"What's your next move?"
                +"What do you want to do next?"
                +"You know the score. What's your next move?"
            } }
        }
    }

    onResponse<Stand> {
        furhat.say { random {
            +"Very well!"
            +"Getting cold feet, ${users.current.info.getUserName()}? I'm sure you could have scored a bit higher!"
            +"Stand? Ok, let's see if I can beat your score!"
            +"Oh, this is thrilling! Let's see if I can get a higher score than you!"
        } }
        goto(PlayDealersHand)
    }

    onResponse<RequestOptions> {
        furhat.say("For another card, say 'hit'.")
        furhat.say("Otherwise, say 'stand'.")
        reentry()
    }

    onResponse<RequestSpecificCard> {
        Gestures.GazeAway
        furhat.say { random {
            +"Don't try that here, my boss is a bad guy."
            +"This is not a place to cheat, ${users.current.info.getUserName()}!"
            +"I can't just give you the card you want, ${users.current.info.getUserName()}!"
            +"You wish!"
        } }
        furhat.say("I will just pretend I didn't hear that.")
        reentry()
    }

    onResponse<RequestHelp> {
        val userScore = users.current.hand.getScore()
        var furhatScore = furhatHand.getScore()
        Gestures.Thoughtful
        furhat.say { random {
            +"Your current score is $userScore."
            +"Hmm... If my calculations are correct, and they always are, your current score is $userScore."
            +"Let's see. Your current score is $userScore."
            +"Right now, your current score is $userScore."
        } }
        furhat.say { random {
            +"My current score is $furhatScore."
            +"I'm currently at a score of $furhatScore."
            +"My face up card is ${furhatHand.getCard(-1).toText()}, which makes my score $furhatScore."
        } }
        if(userScore > 16){
            furhat.say { random {
                +"I suggest you stand."
                +"If it was me playing, I would stand."
                +"If I was you, ${users.current.info.getUserName()}, I would not take another card."
                +"Based on this, the optimal move would be to stand."
            } }
        } else if (userScore >= 13 && userScore <= 16){
            if (furhatScore > 6){
                furhat.say { random {
                    +"I suggest you hit."
                    +"I suggest you take another card."
                    +"If it was me playing, I would hit."
                    +"If I was you, ${users.current.info.getUserName()}, I would take another card."
                    +"Based on this, the optimal move would be to hit."
                    +"Based on this, I would ask for another card. That'd make things more exciting as well!"
                } }
            } else {
                furhat.say { random {
                    +"I suggest you stand."
                    +"If it was me playing, I would stand."
                    +"If I was you, ${users.current.info.getUserName()}, I would not take another card."
                    +"Based on this, the optimal move would be to stand."
                } }
            }
        } else if (userScore == 12) {
            if (furhatScore > 3 && furhatScore < 7) {
                furhat.say { random {
                    +"I suggest you stand."
                    +"If it was me playing, I would stand."
                    +"If I was you, ${users.current.info.getUserName()}, I would not take another card."
                    +"Based on this, the optimal move would be to stand."
                } }
            } else {
                furhat.say { random {
                    +"I suggest you hit."
                    +"I suggest you take another card."
                    +"If it was me playing, I would hit."
                    +"If I was you, ${users.current.info.getUserName()}, I would take another card."
                    +"Based on this, the optimal move would be to hit."
                    +"Based on this, I would ask for another card. That'd make things more exciting as well!"
                } }
            }
        } else {
            furhat.say { random {
                +"I suggest you hit."
                +"I suggest you take another card."
                +"If it was me playing, I would hit."
                +"If I was you, ${users.current.info.getUserName()}, I would take another card."
                +"Based on this, the optimal move would be to hit."
                +"Based on this, I would ask for another card. That'd make things more exciting as well!"
            } }
        }

        reentry()
    }

    onResponse<Surrender> {
        furhat.say { random {
            +"That's too bad, ${users.current.info.getUserName()}! Better luck next time!"
            +"I see!"
        } }
        goto(EndOfRound)
    }

    onResponse<TellMyScore> {
        val userScore = users.current.hand.getScore()
        furhat.say { random {
            +"Your current score is $userScore."
            +"One of those and one of those, and ... hmm... Right! ${users.current.info.getUserName()}, your score is $userScore."
        } }
        reentry()
    }
    onResponse<TellFurhatScore> {
        var furhatScore = furhatHand.getScore()
        furhat.say { random {
            +"My current score is $furhatScore."
            +"My hand totals $furhatScore currently."
        } }
        reentry()
    }
    onResponse<TellMyCards> {
        furhat.say { random {
            +"The cards in your hand are"
            +"Your cards are"
            +"Short memory, ${users.current.info.getUserName()}? So is mine... Let's see. Your cards are"
        } }
        for (card in users.current.hand.cards){
            furhat.say(card.toText())
        }
        reentry()
    }
    onResponse<TellFurhatCard> {
        furhat.say { random {
            +"My face up card is ${furhatHand.getCard(0).toText()}."
            +"My card? It's ${furhatHand.getCard(0).toText()}!"
        } }
        reentry()
    }
}

val PlayDealersHand : State = state(Interaction) {
    onEntry {
        furhatHand.addCard(GenerateCard())
        furhat.say { random {
            +"My face down card was ${furhatHand.getCard(-1).toText()}!"
            +"Let's see... My face down card was ${furhatHand.getCard(-1).toText()}!"
            +"This is the most exciting part, in my opinion! My face down card was... ${furhatHand.getCard(-1).toText()}!"
        } }
        var furhatScore = furhatHand.getScore()
        furhat.say { random {
            +"My current score is $furhatScore."
            +"My hand totals $furhatScore currently."
        } }
        while (furhatScore < 17) {
            furhatHand.addCard(GenerateCard())
            furhat.say { random {
                +"I will take another card. It is ${furhatHand.getCard(-1).toText()}."
                +"Let me draw another card. It is ${furhatHand.getCard(-1).toText()}."
                +"I'll draw another card... It is ${furhatHand.getCard(-1).toText()}."
            } }
            furhatScore = furhatHand.getScore()
            furhat.say { random {
                +"My new score is $furhatScore."
                +"Now, my hand totals $furhatScore."
            } }
        }

        val userScore = users.current.hand.getScore()
        if (furhatScore > 21) {
            furhat.say { random {
                +"I busted! You win, ${users.current.info.getUserName()}!"
                +"I busted! Congratulations! You win this round, ${users.current.info.getUserName()}!"
                +"The house doesn't always win! I busted! You win, ${users.current.info.getUserName()}!"
            } }
        } else if (userScore > furhatScore) {
            furhat.say { random {
                +"Your score was higher than mine. You win, ${users.current.info.getUserName()}!"
                +"Your hand totals higher than mine! Congratulations, you win, ${users.current.info.getUserName()}!"
                +"Turns out the house doesn't always win! You win, ${users.current.info.getUserName()}!"
            } }
        } else if (userScore < furhatScore) {
            furhat.say { random {
                +"My score was higher than yours. I'm sorry, ${users.current.info.getUserName()}, you lose!"
                +"Sorry, ${users.current.info.getUserName()}, my score beats yours. You lose!"
                +"That means you lose, ${users.current.info.getUserName()}, since my hand totalls higher than yours."
            } }
            furhat.say { random {
                +"Better luck next time!"
                +"I'm sure you'll win next time!"
            } }
        } else {
            furhat.say { random {
                +"Our scores were the same. It's a tie!"
                +"It's a draw! Our scores were equal!"
                +"Hmm, a draw. That's not very exciting!"
            } }
        }

        goto(EndOfRound)
    }
}

val EndOfRound : State = state(Interaction) {
    onEntry {
        furhat.ask { random {
            +"Do you want to play another round?"
            +"Wanna go again?"
            +"What do you say? Another round?"
            +"I've got time for another round. How about you?"
        } }
    }

    onResponse<Play> {
        furhat.say { random {
            +"Terrific! Let's go!"
            +"Sweet! Let's see who takes it this time!"
            +"Cool! Let's see whose side the cards are on this time!"
        } }
        goto(PlayingARound)
    }


    onResponse<No> {
        furhat.say { random {
            +"Okey, thanks for playing! See you next time!"
            +"I see. I hope you had a good time. Have a good one!"
            +"A good time to stop! Take care, and I'll see you next time!"
        } }
        goto(Idle)
    }


}