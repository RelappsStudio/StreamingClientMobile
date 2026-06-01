package com.relapps.localstreaming.checkers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relapps.localstreaming.checkers.domain.PieceColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


data class PotentialMove(val start: Coordinate, val end: Coordinate, val isJump: Boolean)
@HiltViewModel
class CheckersViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CheckersState())
    val state = _state.asStateFlow()

    fun onAction(action: CheckersAction) {
        when(action) {
            is CheckersAction.SquareSelect -> {
                val currentState = _state.value

                //if game has a winner do not process next clicks
                if (currentState.winner != null) {
                    return
                }
                //if it's AI turn ignore user clicks
                if (currentState.gameMode == GameMode.VS_AI && currentState.currentTurn == currentState.aiColor) {
                    return
                }
                handleSquareSelect(action.row, action.column)
            }
            is CheckersAction.SelectGameMode -> {
                _state.value = _state.value.copy(gameMode = action.mode)
                val currentState = _state.value
                if (currentState.gameMode == GameMode.VS_AI && currentState.currentTurn == currentState.aiColor) {
                    aiMove()
                }
            }

            CheckersAction.ResetGame -> _state.value = CheckersState() //reset game to base state
        }
    }

    private fun hasAnyValidMoves(color: PieceColor, board: BoardGrid) : Boolean {
        for (y in 0 until 8) {
            for (x in 0 until 8) {
                if (board[y][x]?.color == color) {
                    val moves = calculateValidMoves(Coordinate(y, x), board)
                    if (moves.isNotEmpty()) {
                        return true
                    }
                }
            }
        }

        return false
    }


    //AI move scans the whole board for pieces
    //prepares all possible moves for pieces found
    //if any piece can be taken from opponent take it
    //else make a random move from possible ones
    private fun aiMove() {
        viewModelScope.launch {
            delay(600)

            val currentState = _state.value
            val aiPieces = mutableListOf<Coordinate>()

            for (y in 0 until 8) {
                for (x in 0 until 8) {
                    if (currentState.board[y][x]?.color == currentState.aiColor) {
                        aiPieces.add(Coordinate(y, x))
                    }
                }
            }

            val allPotentialMoves = mutableListOf<PotentialMove>()

            for (start in aiPieces) {
                val validTargets = calculateValidMoves(start, currentState.board)
                for (end in validTargets) {
                    val isJump = Math.abs(end.row - start.row) == 2
                    allPotentialMoves.add(PotentialMove(start, end, isJump))
                }
            }

            if (allPotentialMoves.isEmpty()) {
                //TODO game over state
                return@launch
            }

            val jumpMoves = allPotentialMoves.filter { move -> move.isJump }
            val chosenMove = if (!jumpMoves.isEmpty()) {
                jumpMoves[Random.nextInt(jumpMoves.size)]
            } else {
                allPotentialMoves[Random.nextInt(allPotentialMoves.size)]
            }

            movePiece(chosenMove.start, chosenMove.end)
        }
    }

    private fun handleSquareSelect(row: Int, column: Int) {
        val currentState = _state.value
        val clickedCoordinate = Coordinate(row, column)
        val clickedPiece = currentState.board[row][column]

        //if user clicks their color piece highlight valid moves
        if (clickedPiece != null && clickedPiece.color == currentState.currentTurn) {
            val moves = calculateValidMoves(clickedCoordinate, currentState.board)
            _state.value = currentState.copy(
                selectedSquare = clickedCoordinate,
                validMoves = moves
            )
            return
        }

        //if user has already selected piece and clicked one of highlighted valid move - perform that move
        if (currentState.selectedSquare != null && clickedCoordinate in currentState.validMoves) {
            movePiece(start = currentState.selectedSquare, end = clickedCoordinate)
            return
        }

        //if user had piece selected but clicked on invalid square reset the selection and possible moves
        _state.value = currentState.copy(
            selectedSquare = null,
            validMoves = emptyList()
        )
    }

    private fun movePiece(start: Coordinate, end: Coordinate) {
        val currentState = _state.value
        val newBoard = currentState.board.map { it.toMutableList() }
        val movingPiece = newBoard[start.row][start.column] ?: return


        //if the move is a jump
        //calculate the mid point and remove taken piece
        val isJump = Math.abs(end.row - start.row) ==2
        if (isJump) {
            val jumpedRow = (start.row + end.row) / 2
            val jumpedColumn = (start.column + end.column) / 2

            newBoard[jumpedRow][jumpedColumn] = null
        }

        //piece can be promoted by reaching opposing end of the board
        val becomesKing = movingPiece.isKing
                || (movingPiece.color == PieceColor.RED && end.row == 7)
                || (movingPiece.color == PieceColor.WHITE && end.row == 0)


        //move the piece to new position, delete it from old position
        newBoard[end.row][end.column] = movingPiece.copy(isKing = becomesKing)
        newBoard[start.row][start.column] = null



        //if a piece was taken calculate if next piece can be taken to keep turn on attacking player
        if (isJump) {
            val additionalMoves = calculateValidMoves(end, newBoard)

            val canMoreMovesBeMade = additionalMoves.any {currentCoordinate -> Math.abs(currentCoordinate.row - end.row) == 2} //only look for valid moves with a distance of 2 - piece taking

            if (canMoreMovesBeMade) {
                _state.value = currentState.copy(
                    board = newBoard,
                    currentTurn = currentState.currentTurn,
                    selectedSquare = end,
                    validMoves = additionalMoves.filter {currentCoordinate -> Math.abs(currentCoordinate.row - end.row) == 2}
                )

                if (currentState.gameMode == GameMode.VS_AI && currentState.currentTurn == currentState.aiColor) {
                    aiMove()
                }
                return
            }
        }

        //switch turns after performing a valid move
        val nextTurn = if (currentState.currentTurn == PieceColor.WHITE) PieceColor.RED else PieceColor.WHITE

        val isGameOver = !hasAnyValidMoves(nextTurn, newBoard)
        val winner = if (isGameOver) currentState.currentTurn else null

        //update state and clear selection
        _state.value = currentState.copy(
            board = newBoard,
            currentTurn = nextTurn,
            selectedSquare = null,
            validMoves = emptyList(),
            winner = winner
        )
        //after changing turns see if it's ai turn and make ai move
        if (winner == null && _state.value.gameMode == GameMode.VS_AI && _state.value.currentTurn == currentState.aiColor) {
            aiMove()
        }
    }

    private fun calculateValidMoves(start: Coordinate, board: BoardGrid): List<Coordinate> {
        val piece = board[start.row][start.column] ?: return emptyList()
        val moves = mutableListOf<Coordinate>()

        val directions = mutableListOf<Int>()
        if (piece.isKing || piece.color == PieceColor.RED) directions.add(1) //red moves only down
        if (piece.isKing || piece.color == PieceColor.WHITE) directions.add(-1) //black moves only up
        //king moves both up and down

        for (y in directions) { //moves only up or down
            for (x in listOf(-1, 1)) { //2 possible moves sideways
                val targetRow = start.row + y
                val targetColumn = start.column + x

                if (targetRow in 0..7 && targetColumn in 0..7) { //if target is in board
                    val targetPiece = board[targetRow][targetColumn]

                    if (targetPiece == null) { // if targeted square is empty
                        moves.add(Coordinate(targetRow, targetColumn))
                    }
                    //if targeted square is not empty
                    //check if piece can be taken by current piece
                    //(if there is an empty square behind)
                    else if (targetPiece.color != piece.color) {
                        val jumpRow = targetRow + y
                        val jumpColumn = targetColumn + x
                        if (jumpRow in 0..7 && jumpColumn in 0..7 && board[jumpRow][jumpColumn] == null) {
                            moves.add(Coordinate(jumpRow, jumpColumn))
                        }
                    }
                }
            }
        }

    return moves
    }
}