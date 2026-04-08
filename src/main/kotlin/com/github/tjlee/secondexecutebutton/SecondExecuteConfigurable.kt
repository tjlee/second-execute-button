package com.github.tjlee.secondexecutebutton

import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel

class SecondExecuteConfigurable : BoundConfigurable("Second SQL Execution Button") {

    private enum class ExecInsideOption(val value: Int, private val label: String) {
        SHOW_CHOOSER(1, "Ask what to execute"),
        SUBQUERY(2, "Smallest subquery or statement"),
        SMALLEST(3, "Smallest statement"),
        LARGEST(4, "Largest statement"),
        BATCH(5, "Largest statement or batch"),
        WHOLE_SCRIPT(6, "Whole script"),
        SCRIPT_TAIL(7, "Everything from caret");

        override fun toString() = label

        companion object {
            fun fromValue(value: Int) = entries.find { it.value == value } ?: WHOLE_SCRIPT
        }
    }

    private enum class ExecOutsideOption(val value: Int, private val label: String) {
        NOTHING(1, "Nothing"),
        WHOLE_SCRIPT(2, "Whole script"),
        SCRIPT_TAIL(3, "Everything below caret");

        override fun toString() = label

        companion object {
            fun fromValue(value: Int) = entries.find { it.value == value } ?: WHOLE_SCRIPT
        }
    }

    private enum class ExecSelectionOption(val value: Int, private val label: String) {
        EXACTLY_ONE(1, "Exactly as a single statement"),
        EXACTLY_SCRIPT(2, "Exactly as separate statements"),
        SMART_EXPAND(3, "Smart expand to script");

        override fun toString() = label

        companion object {
            fun fromValue(value: Int) = entries.find { it.value == value } ?: EXACTLY_SCRIPT
        }
    }

    private val settings = SecondExecuteSettings.getInstance()

    override fun createPanel(): DialogPanel = panel {
        row("When caret inside statement execute:") {
            comboBox(ExecInsideOption.entries)
                .bindItem(
                    { ExecInsideOption.fromValue(settings.state.execInside) },
                    { settings.state.execInside = it?.value ?: 6 }
                )
        }
        row("When caret outside statement execute:") {
            comboBox(ExecOutsideOption.entries)
                .bindItem(
                    { ExecOutsideOption.fromValue(settings.state.execOutside) },
                    { settings.state.execOutside = it?.value ?: 2 }
                )
        }
        row("For selection execute:") {
            comboBox(ExecSelectionOption.entries)
                .bindItem(
                    { ExecSelectionOption.fromValue(settings.state.execSelection) },
                    { settings.state.execSelection = it?.value ?: 2 }
                )
        }
    }
}
