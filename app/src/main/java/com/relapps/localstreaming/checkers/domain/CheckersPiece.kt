package com.relapps.localstreaming.checkers.domain

enum class PieceColor { RED, WHITE}

data class CheckersPiece(
    val color: PieceColor,
    val isKing: Boolean = false,
)