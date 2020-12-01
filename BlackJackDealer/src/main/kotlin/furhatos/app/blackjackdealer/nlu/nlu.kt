package furhatos.app.blackjackdealer.nlu

import furhatos.nlu.Intent
import furhatos.nlu.Entity
import furhatos.util.Language

// How the user can ask to repeat the rules
class RequestRules : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "what are the rules",
                "how does it work",
                "Can you go over the rules?"
        )
    }
}

class Hit : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Hit",
                "head",
                "Hit me with another card",
                "One more"
        )
    }
}

class Stand : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "stand",
                "send",
                "I don't want another card",
                "stop"
        )
    }
}


