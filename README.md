For **Non-Java** languages go to `./src/main/<language>` and follow README.md instructions.  

For **Java**:
- setup Java (JDK 11)
  + setup JAVA_HOME variable
- setup Maven3
  + setup M2_HOME variable
  + setup Path variable
  + open cmd and run command 'mvn -version' it should print valid java and maven location
- import this project as Maven project into Intellij Idea (Eclipse/ is not recommended)
- define **Engine** dependency
  + on page _http://\<server>/codenjoy-contest/help_
    * you can download zip with dependency
      - \<server> = `<server_host_ip>:8080` - server ip in your LAN
      - \<server> = codenjoy.com if you play on http://codenjoy.com/codenjoy-contest
    * on this page you can also read game instructions
- register your hero on the server _http://\<server>/codenjoy-contest/register_
- in class `./src/main/java/com/codenjoy/dojo/<gamename>/client/YourSolver.java`
  + copy board page browser url from address bar and paste into main method
  + implement `public String get(Board board)` which should return commands to server depends on received board state 
  + run _main_ method of _YourSolver_ class
  + on page _http://\<server>/codenjoy-contest/board/game/\<gamename>_ you can check the leaderboard - your bot should move
  + if something changed - restart the process
    * __warning!__ only one instance of YourSolver class you can run per one player - please check this
- in `./src/main/java/com/codenjoy/dojo/<game_name>/client/Board.java`
  + you can add you own methods for work with board
- in test package `./src/test/java/com/codenjoy/dojo/<gamename>/client`
  + you can write yor own test
- Codenjoy!