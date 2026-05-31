package com.relapps.localstreaming.checkers.presentation

sealed interface CheckersAction {
    data class SquareSelect(val row: Int, val column: Int) : CheckersAction
}