package com.relapps.localstreaming.checkers.presentation

sealed interface CheckersAction {
    data class SquareSelect(val row: Int, val column: Int) : CheckersAction
    data class SelectGameMode(val mode: GameMode) : CheckersAction
    object ResetGame : CheckersAction
}