module Main (main) where

import Criterion.Main (bench, bgroup, defaultMain, env, nf)
import Day1 (day1a, day1b)
import Day2 (day2a, day2b)
import Day3 (day3a, day3b)
import Day4 (day4a, day4b)
import Day5 (day5a, day5b)
import Day6 (day6a, day6b)
import Day7 (day7a, day7b)
import Day8 (day8a, day8b)
import Day9 (day9a, day9b)
import Day10 (day10)
import Day11 (day11a, day11b)
import Day12 (day12)
import Day13 (day13a, day13b)
import Day14 (day14a, day14b)
import Paths_aoc2018 (getDataFileName)
import System.Environment (lookupEnv)

getDayInput :: Int -> IO String
getDayInput i = getDataFileName ("day" ++ show i ++ ".txt") >>= readFile

readDayInput :: (Read a) => Int -> IO a
readDayInput = fmap read . getDayInput

main :: IO ()
main = maybe False (not . null) <$> lookupEnv "CI" >>= \ci -> defaultMain
  [ env (getDayInput 1) $ \input -> bgroup "Day 1"
      [ bench "part 1" $ nf day1a input
      , bench "part 2" $ nf day1b input
      ]
  , env (getDayInput 2) $ \input -> bgroup "Day 2"
      [ bench "part 1" $ nf day2a input
      , bench "part 2" $ nf day2b input
      ]
  , env (getDayInput 3) $ \input -> bgroup "Day 3"
      [ bench "part 1" $ nf day3a input
      , bench "part 2" $ nf day3b input
      ]
  , env (getDayInput 4) $ \input -> bgroup "Day 4"
      [ bench "part 1" $ nf day4a input
      , bench "part 2" $ nf day4b input
      ]
  , env (getDayInput 5) $ \input -> bgroup "Day 5"
      [ bench "part 1" $ nf day5a input
      , bench "part 2" $ nf day5b input
      ]
  , env (getDayInput 6) $ \input -> bgroup "Day 6"
      [ bench "part 1" $ nf day6a input
      , bench "part 2" $ nf (day6b 10000) input
      ]
  , env (getDayInput 7) $ \input -> bgroup "Day 7"
      [ bench "part 1" $ nf day7a input
      , bench "part 2" $ nf (day7b 60 5) input
      ]
  , env (getDayInput 8) $ \input -> bgroup "Day 8"
      [ bench "part 1" $ nf day8a input
      , bench "part 2" $ nf day8b input
      ]
  , env (getDayInput 9) $ \input -> bgroup "Day 9"
      [ bench "part 1" $ nf day9a input
      , bench "part 2" $ nf day9b input
      ]
  , env (getDayInput 10) $ \input ->
        bench "Day 10" $ nf day10 input
  , env (getDayInput 11) $ \input -> bgroup "Day 11"
      [ bench "part 1" $ nf day11a input
      , bench "part 2" $ nf day11b input
      ]
  , env (getDayInput 12) $ \input -> bgroup "Day 12"
      [ bench "part 1" $ nf (day12 20) input
      , bench "part 2" $ nf (day12 50000000000) input
      ]
  , env (getDayInput 13) $ \input -> bgroup "Day 13"
      [ bench "part 1" $ nf day13a input
      , bench "part 2" $ nf day13b input
      ]
  , env (getDayInput 14) $ \input -> bgroup "Day 14" $
        bench "part 1" (nf day14a input) :
        [bench "part 2" $ nf day14b input | not ci]
  ]