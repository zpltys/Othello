public class State {
    //0 means empty, 1 means black, 2 means white
    public int[][] s;

    public State() {
        s = new int[8][];
        int i, j;
        for(i = 0; i < 8; i++) 
            s[i] = new int[8];
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++)
            s[i][j] = 0;
    }

    //reverse 1 and 2 in s
    public void reverse() {
        int i, j;
        for(i = 0; i < 8; j++) for(j = 0; j < 8; j++) {
            if(s[i][j] == 0) continue;
            s[i][j] = 3 - s[i][j];
        }
    }

    //calculate how many chesses we can change in the direction (r, c)
    //assuming (x, y) is black
    private int cal(int x, int y, int r, int c) {
        int count = 0;
        x += r; y += c;
        while(x <= 7 && x >= 0 && y <= 7 && y >= 0) {
            if(s[x][y] == 0) return 0;
            if(s[x][y] == 1) break;
        }
        x -= r; y -= c;
        while(s[x][y] == 2) {
            s[x][y] = 1;
            x -= r; y -= c;
        }
        return count;
    }

    //insert a chess in (x, y), if you can't insert return -1
    //else return the number of change points
    //when the function called, 's' will be changed
    public int insert(int x, int y, int color) {
        if(x < 0 || x > 7 || y < 0 || y > 7 || s[x][y] != 0) return -1;
        int i, j;

        boolean needReverse = color == 2;

        if(needReverse) reverse();

        s[x][y] = 1;
        int tot = 0;
        for(i = -1; i <= 1; i++) for(j = -1; j <= 1; j++) {
            if(i == 0 && j == 0) continue;
            tot += cal(x, y, i, j);
        }
        if(tot == 0) return -1;
        if(needReverse) reverse();
        return tot;
    }

    //the same as insert
    //but will not change 's'
    public int test(int x, int y, int color) {
        State an = new State();
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            an.s[i][j] = s[i][j];
        }
        return an.insert(x, y, color);
    }
}
