package com.github.tjlee.secondexecutebutton


import com.intellij.codeInsight.hint.HintManager
import com.intellij.database.actions.QueryActionBase
import com.intellij.database.console.JdbcConsoleProvider
import com.intellij.database.settings.DatabaseSettings
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.psi.PsiDocumentManager

class SecondExecuteAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        FileDocumentManager.getInstance().saveAllDocuments()

        val settings = SecondExecuteSettings.getInstance().state
        val execOption = DatabaseSettings.ExecOption().also {
            it.execInside = settings.execInside
            it.execOutside = settings.execOutside
            it.execSelection = settings.execSelection
        }

        val info = QueryActionBase.getInfoClassic(e, execOption) { editor ->
            HintManager.getInstance().showErrorHint(editor, "Nothing to run")
        } ?: return

        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val console = JdbcConsoleProvider.getValidConsole(project, virtualFile) ?: return

        JdbcConsoleProvider.doRunQueryInConsole(console, info)
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabledAndVisible = editor != null
    }

    override fun getActionUpdateThread() = ActionUpdateThread.BGT
}