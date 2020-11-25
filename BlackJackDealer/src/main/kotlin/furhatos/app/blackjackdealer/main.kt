package furhatos.app.blackjackdealer

import furhatos.app.blackjackdealer.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class BlackjackdealerSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
