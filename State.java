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
        weight = new int[8][];
        for(i = 0; i < 8; i++) 
        	weight[i] = new int[8];
        for(i = 0; i < 8; i++) 
        	for(j = 0; j < 8; j++)
        		weight[i][j] = 1;
        
        for(i = 0;i < 8;i++)
        {
        	weight[0][i] = 3;
        	weight[7][i] = 3;
        	weight[i][0] = 3;
        	weight[i][7] = 3;
        }
        weight[0][0] = 10;
        weight[0][7] = 10;
        weight[7][0] = 10;
        weight[7][7] = 10;
    }

    public State(State an) {
        this();
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) 
            s[i][j] = an.s[i][j];
    }

    public void init() {
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            if((i == 3 || i == 4) && (j == 3 || j == 4)) {
                if(i == j) s[i][j] = 2;
                else s[i][j] = 1;
            } else s[i][j] = 0;
        }
    }

    //reverse 1 and 2 in s
    public void reverse() {
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
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
            count++;
            x += r;
            y += c;
        }
        if(x < 0 || x > 7 || y < 0 || y > 7) return 0;
        x -= r; y -= c;
        while(s[x][y] == 2) {
            s[x][y] = 1;
            x -= r; y -= c;
        }
        return count;
    }
    
    private int cal1(int x, int y, int r, int c) {
        int count = 0;
        x += r; y += c;
        while(x <= 7 && x >= 0 && y <= 7 && y >= 0) {
            if(s[x][y] == 0) return 0;
            if(s[x][y] == 1) break;
            count += 2 * weight[x][y];
            x += r;
            y += c;
        }
        if(x < 0 || x > 7 || y < 0 || y > 7) return 0;
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
    
    public int insert1(int x, int y, int color) {
        if(x < 0 || x > 7 || y < 0 || y > 7 || s[x][y] != 0) return -1;
        int i, j;

        boolean needReverse = color == 2;

        if(needReverse) reverse();

        s[x][y] = 1;
        int tot = 0;
        for(i = -1; i <= 1; i++) for(j = -1; j <= 1; j++) {
            if(i == 0 && j == 0) continue;
            tot += cal1(x, y, i, j);
        }
        if(tot == 0) return -1;
        if(needReverse) reverse();
        tot += weight[x][y];
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
        int val = an.insert(x, y, color);
//        System.out.println("x: " + x + "  y:" + y + "  color:" + color + "val: " + val);
        return val;
    }
    
    public int test1(int x, int y, int color) {
        State an = new State();
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            an.s[i][j] = s[i][j];
        }
        int val = an.insert1(x, y, color);
//        System.out.println("x: " + x + "  y:" + y + "  color:" + color + "val: " + val);
        return val;
    }
}
