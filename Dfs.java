import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Dfs extends AI{

	public int level;
	public Dfs() {
        level = 5;
        
    }
	public Dfs(int n) {//构造函数，设定搜索数层数
        level = n;
    }
	@Override
	public Point calStep(State state,int color) //返回当前状态最优走字位置
	{
		//state.s[0][]={0,0,2,2,2,1,1,1};
		int ans = -100000;
		Point p = new Point();
		p.x = -1;
		p.y = -1;
		List<statev> statevList = new ArrayList<statev>();
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				int x = state.test1(i,j,color);
				if(x > 0)
				{
					statevList.add(new statev(i,j,x));
					//System.out.println(i+" "+j+"\n");
				}
			}
		Collections.sort(statevList, new sortstatev());//对能走子位置排序
		int count = 0;
		for(statev ss : statevList)//选择一步之内最优的前五种走子策略
		{
			count++;
			if(count > 5)
				break;
				State temp = new State(state);
				int x = temp.insert1(ss.x,ss.y,color);
				System.out.println(ss.x+" "+ss.y+" "+x+"  ");
					x -= mean(temp,3 - color,level);
				System.out.println(x+'\n');	
					//x *= weight[ss.x][ss.y];
					if(x > ans)//更新最优走子位
					{
						ans = x;
						p.x = ss.x;
						p.y = ss.y;
					}
		}
		System.out.println(p.x+" "+p.y+'\n');	
		return p;
	}
	
	class statev//记录走子位置和仅一步所得优势
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
	class sortstatev implements Comparator {//从大到小排序
        public int compare(Object o1, Object o2) {
        	statev s1 = (statev) o1;
        	statev s2 = (statev) o2;
         if(s1.value < s2.value)
        	 return 1;
         else
        	 return -1;
        }
       }
	
	public int mean(State state,int color,int cnt)//返回该状态、执该子、迭代层数为cnt的最大优势
	{
		boolean fag = false;
		int ans = 0;
		List<statev> statevList = new ArrayList<statev>();
		for(int i = 0;i < 8;i++)
			for(int j = 0;j < 8;j++)
			{
				int x = state.test1(i,j,color);
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
		if(cnt <= 0)//最后一层直接返回
		{
			
			//ans=-1;
			for(int i = 0;i < 8;i++)
				for(int j = 0;j < 8;j++)
				{
					int x = state.test1(i,j,color);
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
			fag=true;
			if(count > 5)
				break;
			fag=true;
			for(int ii = 0;ii < 8;ii++)
				for(int jj = 0;jj < 8;jj++)
					temp.s[ii][jj] = state.s[ii][jj];
			int x = temp.insert1(ss.x,ss.y,color);
			x -= mean(temp,3 - color,cnt-1);
			//x *= weight[ss.x][ss.y];
			if(x > ans)
				ans = x;
		}
				
			
		if(!fag)//无子可走的情况，直接换子走
		{
			//System.out.print(cnt);
			//System.out.print(fag);
			ans = -mean(state,3 - color,cnt-1);
		}
		return ans;
	}
}
