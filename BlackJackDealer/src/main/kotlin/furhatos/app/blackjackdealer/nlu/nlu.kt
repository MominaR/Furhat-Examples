package furhatos.app.blackjackdealer.nlu

import furhatos.nlu.Intent
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