import java.awt.Point;
import java.lang.Thread.State;

public class Dfs extends AI{

    //state is the real-time chess state
    //color is the color of the next chess
    //the function should return the place you want insert
    //
    //you can't change state, so you must make a copy of 'state' at first
    //
	
	private abstract Point calStep(State state,int color) 
	{
		int ans = -1;
		Point p;
		p.x = -10;
		p.y = -10;
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				State temp;
				int x = temp.insert(i,j);
				if(x > 0)
				{
					x += mean(temp,3 - color,6);
					if(x > ans)
					{
						ans = x;
						p.x = i;
						p.y = j;
					}
				}
			}
		return p;
	}
	public int mean(State state,int color,int cnt)
	{
		bool fag = false;
		int ans = -1;
		if(cnt==0)
		{
			if(color == 2)//now is the white,reverse
				for(int i = 0;i < 8;i++)
					for(int j = 0;j < 8;j++)
					{
						if(state.s[i][j] != 0)
						{
							state.s[i][j] = 3 - state.s[i][j];
						}
					}
			int ans=-1;
			for(int i = 0;i < 8;i++)
				for(int j = 0;j < 8;j++)
				{
					int x = state.test(i,j);
					if(x > ans)
						ans = x;
				}
			return ans;
		}
		State temp,t;
		for(int i = 0;i < 8;i++)//before reverse
			for(int j = 0;j < 8;j++)
				t.s[i][j] = state.s[i][j];
		if(color == 2)//now is the white,reverse
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				if(state.s[i][j] != 0)
				{
					state.s[i][j] = 3 - state.s[i][j];
				}
			}
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				int x = state.test(i,j,color);
				if(x > 0)
				{
					fag = true;
					for(int i = 0;i < 8;i++)
						for(int j = 0;j < 8;j++)
							temp.s[i][j] = state.s[i][j];
					x = temp.insert(i,j,1);
					x += mean(temp,2,cnt--);
					if(x > ans)
						ans = x;
				}
			}
		if(!fag)
		{
			ans = mean(state,2,cnt--);
		}
		return ans;
	}
}
