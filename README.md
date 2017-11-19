# themeshgame
the algorithm of the The mesh game - http://themeshgame.com/

![game](/imgs/game.png?raw=true "the game")

The main logic is:<br>
eg we got four numbers, [1, 2, 3, 4]<br>
    let's use the [1, 2] first, <br>
    then we got 3 numbers. [1, 3, 4] or [3, 3, 4]<br>
    let's use the [1,3] first, <br>
    then we got two numbers, [2, 4] or [4, 4]<br>
    that's the process.<br>
    
<br>    
Now we support those operators:
1. default
2. multiply 2 (or reverse divide 2)

#How to build
call 'gradle fatrjar'<br>
then 'java -jar build/libs/themeshgame-all-0.1.jar'
