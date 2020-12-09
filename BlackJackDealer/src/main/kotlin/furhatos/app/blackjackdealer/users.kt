package furhatos.app.blackjackdealer.flow

import furhatos.records.User
import furhatos.skills.UserManager
import javax.jws.soap.SOAPBinding
import kotlin.math.absoluteValue

// Here we need to add the user specific data e.g. score of one user
class Card (
        val value : Int = 1,
        val suit : Int = 1)
{
    fun valueToText(): String {
        val cardNames = listOf("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King")
        return cardNames[value - 1]
    }

    fun suitToText(): String {
        val suitNames = listOf("Hearts", "Diamonds", "Clubs", "Spades")
        return suitNames[suit - 1]
    }

    fun toText(): String {
        val valueText = valueToText()
        var prefix = "a"
        if (listOf("Ace", "Eight").contains(valueText)) {
            prefix = "an"
        }
        return prefix + " " + valueToText() + " of " + suitToText()
    }

    fun getCardScore(): Int {
        var cardScore = 0
        if (value >= 10) {
            cardScore = 10
        } else if (value == 1) {
            cardScore = 11
        } else {
            cardScore = value
        }
        return cardScore
    }
}

class Hand(
        var cards : MutableList<Card> = mutableListOf<Card>()
)
{
    fun getSignedScore(): Int {
        var score = 0
        var nbrLiveAces = 0
        for (card in cards) {
            val cardValue = card.value
            if (cardValue >= 10) {
                score += 10
            } else if (cardValue == 1) {
                score += 11
                nbrLiveAces += 1
            } else {
                score += cardValue
            }
        }

        while (nbrLiveAces > 0) {
            if (score > 21) {
                score -= 10
            } else {
                break
            }
            nbrLiveAces -= 1
        }

        // Used to signal that one ace is live
        if (nbrLiveAces > 0) {
            score *= -1
        }

        return score
    }

    fun getScore() : Int {
        return getSignedScore().absoluteValue
    }


    fun toText() : String {
        var s = ""
        for (card in cards) {
            s += card.toText() + "\n"
        }
        return s
    }

    fun addCard(c : Card) {
        cards.add(c)
    }

    fun getCard(idx : Int) : Card {
        if (idx == -1) {
            return cards.last()
        }
        return cards[idx]
    }

    fun clearHand() {
        cards = mutableListOf<Card>()
    }
}

class UserData(
        var name: String = " "
)
{
    fun getUserName() : String{
        return name
    }

    fun setUserName(n : String) {
        name = n
    }
}


val User.hand : Hand
    get() = data.getOrPut(Hand::class.qualifiedName, Hand())

val User.info : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())


