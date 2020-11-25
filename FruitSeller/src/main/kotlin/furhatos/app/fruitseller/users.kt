package furhatos.app.fruitseller

import furhatos.app.fruitseller.nlu.*
import furhatos.records.User

class FruitData (
        var fruits : FruitList = FruitList()
)

val User.order : FruitData
    get() = data.getOrPut(FruitData::class.qualifiedName, FruitData())