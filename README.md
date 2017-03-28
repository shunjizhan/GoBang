# Introduction
This is a Java program simulating the game [Gobang](https://en.wikipedia.org/wiki/Gomoku), which makes use of [MiniMax Algorithm](https://en.wikipedia.org/wiki/Minimax)

# Run
    cd GoBang/
    ant run

or

    cd GoBang/
    ant compile
    ./Gobang

# Options
`-l`if this is specified, computer will be the dark player.
`-n [size]`size of the board, can be 5-26, default is 11.
`-d [depth]` depth of the Minimax tree, default is 2.

# Sample 
[](https://raw.githubusercontent.com/shunjizhan/GoBang/master/Gobang_demo.gif)

# Referee
To make this program compete with itself or with other program: 

    python referee.py [board_size] ./Gobang ./Gobang

or

    python referee.py [board_size] ./other_program ./Gobang

# Resources
referee.py is provided by TA.

# Specification
A report containing more details can be found [here](https://github.com/shunjizhan/GoBang/blob/master/cs165A_mp2_report.pdf)

# Author
Shunji Zhan