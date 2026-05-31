package com.relapps.localstreaming.checkers.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.relapps.localstreaming.checkers.domain.CheckersPiece
import com.relapps.localstreaming.checkers.domain.PieceColor
import com.relapps.localstreaming.ui.theme.ObsidianBase

@Preview
@Composable
fun CheckersPreview(modifier: Modifier = Modifier) {
    CheckersContent(
        state = CheckersState(),
        onAction = {}
    )
}


@Composable
fun CheckersScreen(
    viewModel: CheckersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    CheckersContent(
        state = state,
        onAction = viewModel::onAction
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckersContent(
    state: CheckersState,
    onAction: (CheckersAction)-> Unit,
    modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Checkers") }) },
        containerColor = ObsidianBase
    ) {

        paddingValues ->

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,

            ) {
                val squareSize = maxWidth/8

                Column {

                    Text(
                        style = MaterialTheme.typography.displayMedium,
                        text = "Current turn: ${state.currentTurn}")

                    Spacer(modifier = Modifier.size(height = 20.dp, width = 0.dp))
                    for(row in 0 until 8) {
                        Row{
                            for(column in 0 until 8) {
                                val currentCoordinate = Coordinate(row, column)
                                val isBlackSquare = (row + column) % 2 != 0
                                val squareColor = if (isBlackSquare) Color.Black else Color.White
                                val isSelected = state.selectedSquare == currentCoordinate
                                val isValidMove = currentCoordinate in state.validMoves
                                val piece = state.board[row][column]

                                Box(
                                    modifier = Modifier
                                        .clickable(
                                            enabled = true,
                                            onClick = { onAction(CheckersAction.SquareSelect(row = row, column = column)) }
                                        )
                                        .size(squareSize)
                                        .border(
                                            width = if (isSelected) 3.dp else 0.dp,
                                            color = if (isSelected) Color.Yellow else Color.Transparent
                                        )
                                        .background(squareColor),
                                    contentAlignment = Alignment.Center,

                                    ) {
                                    if (isValidMove) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(Color.Green.copy(alpha = 0.6f))
                                        ) { }
                                    }

                                    piece?.let { CheckersPiece(piece = it) }
                                }
                            }
                        }
                    }
                }
            }


    }
}

@Composable
fun CheckersPiece(
    piece: CheckersPiece,

    modifier: Modifier = Modifier) {

    val pieceColor = if (piece.color == PieceColor.RED) Color.Red else Color.White

    val strokeColor = if (piece.color == PieceColor.RED) Color.Black else Color.Red

    Box(modifier = Modifier
        .fillMaxSize(0.8f)
        .clip(CircleShape)
        .background(pieceColor),
        contentAlignment = Alignment.Center
    ) {
        if (piece.isKing) {
            Icon(imageVector = Icons.Default.Star,
                contentDescription = "King",
                tint = Color.White,
                modifier = Modifier.fillMaxSize(0.5f)
                )
        }
    }

}