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

class RequestRule1 : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What should I aim?",
                "What is Blackjack?",
                "How does Blackjack work?"
        )
    }
}

class RequestRule2 : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What is Bust?",
                "Can you repeat the second rule?",
                "Please explain bust again.",
                "Can you repeat Rule 2?"
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

class Play : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Yes",
                "Take my money!"
        )
    }
}

class ExplainHit : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What does hit mean?",
                "What happens if I say one more?",
                "What does head mean?"
        )
    }
}

class ExplainBust : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What happens if I bust?",
                "What happens if you bust?",
                "What happens if I get 21?"
        )
    }
}


class RequestOptions : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "what are my options",
                "options?",
                "what can I do",
                "what moves can I make",
                "my options?"
        )
    }
}


