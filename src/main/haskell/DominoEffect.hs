module Main (main) where
import Data.List
import ExampleInput

-- We redefine the Board type here for clarity even though it is already defined in ExampleInput.hs
-- Through import ExampleInput we have access to board1, board2, board3 and board4 as example input.
type Board = (Int -> Int -> Int)

-- Let us define all dominoes
data Domino = D Int Int deriving (Show, Read)

instance Eq Domino where
    (==) (D x1 y1) (D x2 y2) = ((x1 == x2 && y1 == y2) || (x1 == y2 && x2 == y1))

dominoes :: [Domino]
dominoes = [(D x y) | x <- [0..6], y <- [x..6]]

dominoNumber :: Domino -> Int
dominoNumber d = head [n | (n,x) <- zip [1..28] dominoes, x == d]


-- Now we define all possible positions for a single domino on the board.
data Pos = V Int Int | H Int Int deriving (Eq, Show, Read)

positions :: [Pos]
positions = [(V x y) | x <- [0..7], y <- [0..5]] ++ [(H x y) | x <- [0..6], y <- [0..6]]

collidingNeighbours :: Pos -> [Pos]
collidingNeighbours (V x y) = [V x (y+1),        V x (y-1),
                              H (x-1) (y+1),    H x (y+1),
                              H (x-1) y,        H x y]
collidingNeighbours (H x y) = [H (x-1) y,        H (x+1) y,
                              V x y,            V (x+1) y, 
                              V x (y-1),        V (x+1) (y-1)]

dominoAtPosition :: Pos -> Board -> Domino
dominoAtPosition (V x y) b = D (b x y) (b x (y+1))
dominoAtPosition (H x y) b = D (b x y) (b (x+1) y)


-- Let us run over all the positions on the board and see what (unique) stones lie there.
findPossibleDominoPositions :: Board -> [(Pos, Domino)]
findPossibleDominoPositions b = [(p, (dominoAtPosition p b)) | p <- positions]

-- Now we collect the positions in which each of the 28 Dominoes can be.
groupByDomino :: [(Pos, Domino)] -> [([Pos], Domino)]
groupByDomino xs = [([p | (p,y) <- xs, x == y], x) | x <- dominoes]

-- We will sort by dominos that have the lowest amount of positions first to make our lives easier and faster.
-- We need a custom sort function which is convenient to have a shorthand of.
sortL :: [([a], b)] -> [([a], b)]
sortL = sortBy (\(xs,_) -> \(ys,_) -> compare (length xs) (length ys))

-- We need a remove function to remove colliding positions with tiles that are "on the board".
remove :: Eq a => [a] -> [a] -> [a]
remove _ [] = []
remove ys (x:xs)    
    | x `elem` ys   = remove ys xs
    | otherwise     = x : (remove ys xs)

-- Let us delete the neighbours of a given positions out of all the possibilities in the stones that are left.
deleteNeighbours :: [Pos] -> [([Pos], Domino)] -> [([Pos], Domino)]
deleteNeighbours ns = foldr (\(ps,d) -> (:) ((remove ns ps), d)) []


-- The crown jewel
findSolutions :: [([Pos], Domino)] -> [[(Pos, Domino)]]
findSolutions [] = [[]]
findSolutions ((ps,d):xs) = [(p,d) : sol | p <- ps, sol <- (findSolutions . sortL) (deleteNeighbours (collidingNeighbours p) xs)]

-- Let us bring it all together
solutions :: Board -> [[(Pos, Domino)]]
solutions = findSolutions . sortL . groupByDomino . findPossibleDominoPositions



{-

----- Here follows some deserialize stuff to give the solutions in human readable format. Highly inefficient I would say. -----

-}
prettySolutions :: Board -> IO ()
prettySolutions b = do sequence_ (map (putStr . showBoard . outputFromSolution) (solutions b))

outputFromSolution :: [(Pos, Domino)] -> [[Int]]
outputFromSolution sol = [[findDominoNumber (possiblePositions (x,y)) sol | x <- [0..7]] | y <- [0..6]]
                                    where possiblePositions (x,y) = [V x y, V x (y-1), H x y, H (x-1) y]

findDominoNumber :: [Pos] -> [(Pos, Domino)] -> Int
findDominoNumber _ []                           = error "position not found"
findDominoNumber ps ((p,d):xs)  | p `elem` ps   = dominoNumber d
                                | otherwise     = findDominoNumber ps xs

showBoard :: [[Int]] -> String
showBoard = foldr (\x -> (++) (showRow x)) "\n"

showRow :: [Int] -> String
showRow = foldr (\x -> (++) ((if x >= 10 then " " else "  ") ++ show x)) "\n"

main :: IO ()
--main = (putStr . show . length . solutions) board4
main = prettySolutions board1
