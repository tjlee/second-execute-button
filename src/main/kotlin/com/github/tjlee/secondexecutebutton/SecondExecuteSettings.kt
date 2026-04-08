package com.github.tjlee.secondexecutebutton

import com.intellij.database.settings.DatabaseSettings
import com.intellij.openapi.components.*

@Service(Service.Level.APP)
@State(
    name = "SecondExecuteSettings",
    storages = [Storage("secondExecuteButton.xml")]
)
class SecondExecuteSettings : PersistentStateComponent<SecondExecuteSettings.State> {

    data class State(
        var execInside: Int = DatabaseSettings.EXECUTE_INSIDE_WHOLE_SCRIPT,
        var execOutside: Int = DatabaseSettings.EXECUTE_OUTSIDE_WHOLE_SCRIPT,
        var execSelection: Int = DatabaseSettings.EXECUTE_SELECTION_EXACTLY_SCRIPT
    )

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    companion object {
        fun getInstance(): SecondExecuteSettings = service()
    }
}
