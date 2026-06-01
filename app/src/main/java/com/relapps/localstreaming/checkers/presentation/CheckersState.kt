package com.relapps.localstreaming.checkers.presentation

import com.relapps.localstreaming.checkers.domain.CheckersPiece
import com.relapps.localstreaming.checkers.domain.PieceColor

typealias BoardGrid = List<List<CheckersPiece?>>

data class Coordinate(val row: Int, val column: Int)

enum class GameMode {PVP, VS_AI}

data class CheckersState (
    val gameMode: GameMode? = null,
    val board: BoardGrid = createInitialBoard(),
    val currentTurn: PieceColor = PieceColor.WHITE,
    val selectedSquare: Coordinate? = null,
    val validMoves: List<Coordinate> = emptyList(),
    val aiColor: PieceColor = PieceColor.RED,
    val winner: PieceColor? = null
    )

fun createInitialBoard(): BoardGrid {
    return List(8) {row ->
        List(8) {column ->
            val isBlackSquare = (row + column) % 2 != 0

            when{
                isBlackSquare && row < 3 -> CheckersPiece(color = PieceColor.RED)
                isBlackSquare && row > 4 -> CheckersPiece(color = PieceColor.WHITE)
                else -> null
            }
        }
    }
}