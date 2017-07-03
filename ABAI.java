import java.awt.Point;

public class ABAI extends AI {
    boolean f;
    int deep;

    ABAI() {
        super();
    }

    ABAI(int d) {
        deep = d + 2;
    }

    @Override
    public Point calStep(State state, int color) {
        int empty = state.calEmpty();
        if(empty < 7) {
            deep = empty;
            f = true;
        }
        System.out.println("deep:" + deep);
        sResult r = search(state, color, true, deep, -10000, 10000);
        System.out.println("x: " + r.p.x + "  y:" + r.p.y);
        return r.p;
    }

    private sResult search(State state, int color, boolean pos, int depth, 
            int alpha, int beta) {

        System.out.println("pos:" + pos + " depth:" + depth + " alpha:" + alpha + " beta:" + beta);
        int i, j;
        if(depth == 0) {
            int val;
            if(f) val = countPoint(state, color);
            else val = countWeight(state, color);
            return new sResult(val, val, -1, -1);
        } else {
            int cnt = 0, c, fcnt = 0;
            c = pos ? color : 3 - color;
            Point p = new Point(); p.x = -1; p.y = -1;
            for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
                if(state.test(i, j, c) != -1) {
                    cnt++;
                    State s = new State(state);
                    s.insert(i, j, c);
                    sResult r = search(s, color, !pos, depth - 1, alpha, beta);
                    if(pos) {
                        if(alpha < r.beta) {
                            alpha = r.beta;
                            p.x = i; p.y = j;
                        }

                    } else {
                        if(beta > r.alpha) {
                            beta = r.alpha;
                            p.x = i; p.y = j;
                        }
                    }
                    if(alpha >= beta) 
                        return new sResult(alpha, beta, p.x, p.y);
                } else if(state.test(i, j, 3 - c) != -1) fcnt++;
            }
            if(cnt > 0) {
                System.out.println("depth:" + depth + " alpha:" + alpha + " beta:" + beta + " i:" + p.x + " j:" + p.y);
                return new sResult(alpha, beta, p.x, p.y);
            }
            else {
                if(fcnt > 0) {  
                    sResult r = search(state, color, !pos, depth, alpha, beta);
                    return new sResult(r.beta, r.alpha, r.p.x, r.p.y);
                }
                else return search(state, color, pos, 0, alpha, beta);
            }
        }
    }

    class sResult {
        public int alpha, beta;
        public Point p;
        public sResult(int a, int b, int x, int y) {
            alpha = a; beta = b;
            p = new Point();
            p.x = x; p.y = y;
        }
    }

    private int countWeight(State s, int color) {
        int ans = 0, i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            if(s.s[i][j] == color) ans += s.weight[i][j];
            if(s.s[i][j] == 3 - color) ans -= s.weight[i][j];
        }
        return ans;
    }

    private int countPoint(State s, int color) {
        int ans = 0, i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            if(s.s[i][j] == color) ans++;
            if(s.s[i][j] == 3 - color) ans--;
        }
        return ans;
    }

}
