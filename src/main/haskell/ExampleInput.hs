module ExampleInput (board1, board2, board3, board4) where

-- This will be our board definition
type Board = (Int -> Int -> Int)

-- Not that different from [[Int]] but I like functions more than arrays.
boardify :: [[Int]] -> Board
boardify xs = (\x -> \y -> (xs !! y) !! x)

board1 :: Board
board1 = boardify exampleInput1

board2 :: Board
board2 = boardify exampleInput2

board3 :: Board
board3 = boardify exampleInput3

board4 :: Board
board4 = boardify exampleInput4

exampleInput1 :: [[Int]]
exampleInput1 = [[6,6,2,6,5,2,4,1],
                [1,3,2,0,1,0,3,4],
                [1,3,2,4,6,6,5,4],
                [1,0,4,3,2,1,1,2],
                [5,1,3,6,0,4,5,5],
                [5,5,4,0,2,6,0,3],
                [6,0,5,3,4,2,0,3]]

exampleInput2 :: [[Int]]
exampleInput2 = [[5, 4, 3, 6, 5, 3, 4, 6],
                [0, 6, 0, 1, 2, 3, 1, 1],
                [3, 2, 6, 5, 0, 4, 2, 0],
                [5, 3, 6, 2, 3, 2, 0, 6],
                [4, 0, 4, 1, 0, 0, 4, 1],
                [5, 2, 2, 4, 4, 1, 6, 5],
                [5, 5, 3, 6, 1, 2, 3, 1]]

exampleInput3 :: [[Int]]
exampleInput3 = [[4, 2, 5, 2, 6, 3, 5, 4],
                [5, 0, 4, 3, 1, 4, 1, 1],
                [1, 2, 3, 0, 2, 2, 2, 2],
                [1, 4, 0, 1, 3, 5, 6, 5],
                [4, 0, 6, 0, 3, 6, 6, 5],
                [4, 0, 1, 6, 4, 0, 3, 0],
                [6, 5, 3, 6, 2, 1, 5, 3]]

-- credits to Ruben for the following testBoard
exampleInput4 :: [[Int]]
exampleInput4 = [[0,0,0,2,0,4,0,6],
              [0,1,3,0,5,0,6,1],
              [1,1,1,3,1,5,2,2],
              [1,2,4,1,5,2,2,3],
              [2,4,3,3,3,5,4,4],
              [6,2,3,4,6,3,4,5],
              [4,6,5,5,5,6,6,6]]