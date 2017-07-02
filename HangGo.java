import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class HangGo extends AI{

    //state is the real-time chess state
    //color is the color of the next chess
    //the function should return the place you want insert
    //
    //you can't change state, so you must make a copy of 'state' at first
    //
	public int [][]weight;
	public HangGo() {
        weight = new int[8][];
        int i, j;
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
	@Override
	public Point calStep(State state,int color) 
	{
		
		int ans = -100000;
		Point p = new Point();
		p.x = -1;
		p.y = -1;
		List<statev> statevList = new ArrayList<statev>();
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				int x = state.test(i,j,color);
				if(x > 0)
					statevList.add(new statev(i,j,x));
			}
		Collections.sort(statevList, new sortstatev());
		int count = 0;
		for(statev ss : statevList)
		{
			count++;
				State temp = new State(state);
				int x = temp.insert(ss.x,ss.y,color);
					x -= mean(temp,3 - color,5);
					x *= weight[ss.x][ss.y];
					if(x > ans)
					{
						ans = x;
						p.x = ss.x;
						p.y = ss.y;
					}
			}
		return p;
	}
	
	class statev
	{
		int x,y;
		int value;
		public statev(int xx,int yy,int v)
		{
			x=xx;
			y=yy;
			value = v;
		}
	}
	class sortstatev implements Comparator {
        public int compare(Object o1, Object o2) {
        	statev s1 = (statev) o1;
        	statev s2 = (statev) o2;
         if(s1.value > s2.value)
        	 return 1;
         else
        	 return -1;
        }
       }
	
	public int mean(State state,int color,int cnt)
	{
		boolean fag = false;
		int ans = 0;
		List<statev> statevList = new ArrayList<statev>();
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				int x = state.test(i,j,color);
				if(x > 0)
					statevList.add(new statev(i,j,x));
			}
		/*if(cnt>0)
		{
			for(int i=0;i<8;i++)
			{
				for(int j=0;j<i;j++)
					System.out.print(state.s[i][j]);
				System.out.println("\n");
			}
		}*/
		if(cnt <= 0)
		{
			
			//ans=-1;
			for(int i = 0;i < 8;i++)
				for(int j = 0;j < 8;j++)
				{
					int x = state.test(i,j,color);
					if(x > ans && x > 0)
						ans = x;
				}
			return ans;
		}
		Collections.sort(statevList, new sortstatev());
		State temp = new State();
		int count = 0;
		for(statev ss : statevList)
		{
			count++;
			if(count > 5)
				break;
			fag=true;
			for(int ii = 0;ii < 8;ii++)
				for(int jj = 0;jj < 8;jj++)
					temp.s[ii][jj] = state.s[ii][jj];
			int x = temp.insert(ss.x,ss.y,color);
			x -= mean(temp,3 - color,cnt-1);
			x *= weight[ss.x][ss.y];
			if(x > ans)
				ans = x;
		}
				
			
		if(ans == -100000)
		{
			//System.out.print(cnt);
			//System.out.print(fag);
			ans = -mean(state,3 - color,cnt-1);
		}
		return ans;
	}
}
