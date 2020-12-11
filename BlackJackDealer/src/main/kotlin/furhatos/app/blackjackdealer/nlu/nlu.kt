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
                "Can you go over the rules?",
                "rules",
                "explain rules",
                "Can you explain the rules?"
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
                "One more",
                "another card",
                "another card please",
                "Give me another card"
        )
    }
}

class Stand : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "stand",
                "send",
                "stay",
                "I don't want another card",
                "stop",
                "no more"
        )
    }
}

class Play : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Yes",
                "Take my money"
        )
    }
}

class ExplainHit : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What does hit mean",
                "What happens if I ask for another card",
                "What do I say if I want another card",
                "What is hit"
        )
    }
}

class ExplainStand : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What does stand mean",
                "What do I say if I don't want another card",
                "What is stand",
                "What is send"
        )
    }
}

class ExplainBust : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What happens if I bust",
                "What happens if you bust"
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

class RequestSpecificCard : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "can you give me a specific card",
                "give me the card I need and we split the money",
                "give me ace",
                "give me ten",
                "give me nine",
                "give me eight",
                "give me seven",
                "give me six",
                "give me five",
                "give me four",
                "give me three",
                "give me two",
                "give me one"
        )
    }
}

class RequestHelp : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "what is the best move now",
                "what is the optimal move",
                "how can I win",
                "I need help",
                "need help",
                "help",
                "what do you suggest",
                "what should I do"
        )
    }
}

class Surrender : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Take my money",
                "I give up"
        )
    }
}

class TellMyScore : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What is my score",
                "How much do I have"
        )
    }
}

class TellFurhatScore : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What is your score",
                "How much do you have"
        )
    }
}

class TellMyCards : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What are my cards"
        )
    }
}

class TellFurhatCard : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What is your card"
        )
    }
}


