import java.util.*;
import java.io.*;
import java.lang.*;

public class Puzzle
{
	public static int n;
	heap hp;
	int [] cost_list;  
	public vertex h[];       
	public Puzzle()
	{
		h=(vertex[])new vertex[362880];
		cost_list=new int[9];hp=new heap();
	}
	public static void main(String[] args)
	{
	try{
		Scanner s=new Scanner(new File(args [0]));	PrintStream o = new PrintStream(new File(args[1]));System.setOut(o);
		n=s.nextInt();	String null1=s.nextLine();	
		for(int i=0;i<n;i++)
		{
			Puzzle puzz=new Puzzle();
			String start_state=s.next();String goal_state=s.next();String null2=s.nextLine();
			for(int j=1;j<9;j++)puzz.cost_list[j]=s.nextInt();
			puzz.process(start_state,goal_state,puzz.cost_list);
		}
		}catch(FileNotFoundException e){};	
	}

	public void process(String start_state,String goal_state, int [] cost_list)
	{
		if(parity(start_state)!=parity(goal_state)){System.out.println("-1 -1\n");}
		else
		{
			vertex v=new vertex();v.s=start_state;v.cost=0;v.steps=0;v.hashvalue=hashCode(start_state);
			h[hashCode(start_state)]=v;
			hp.insert(v);vertex f=new vertex();f=v;String s=new String();
			while(!f.s.equals(goal_state) && f!=null){f=hp.deletemin();findneighbours(f);}
			System.out.println(f.steps+" "+f.cost);
			if(f.steps!=0)
			{
				while(f.prev.prev!=null){s=s+f.transition+" ";f=f.prev;}
				s=s+f.transition;for(int i=s.length()-1;i>=0;i--)System.out.print(s.charAt(i));System.out.println();
			}
			else System.out.println();
			cost_list=null;h=null;hp.clear();h=null;
		}
	}

	public int hashCode(String a)
	{
		char t[]=a.toCharArray();int [] n=new int[9];int p=0;
		for(int i=0;i<9;i++){if(t[i]=='G')n[i]=0;else n[i]=((int)t[i])-48;}
		for(int i=0;i<9;i++)
		{
			int c=0;
			for(int j=i+1;j<9;j++){if(n[j]<n[i])c++;}p=p+c*f(8-i);
		}
		return p;		
	}

	public int parity(String a)
	{
		char t[]=a.toCharArray();int [] n=new int[9];int p=0;
		for(int i=0;i<9;i++){if(t[i]=='G')n[i]=10;else n[i]=((int)t[i])-48;}
		for(int i=0;i<9;i++)
		{
			if(n[i]!=10)
				{for(int j=i+1;j<9;j++){if(n[j]<n[i])p++;}}
		}return p%2;
	}

	public static int f(int n){	if(n==1 || n==0) return 1;		else return n*f(n-1);	}

	public void findneighbours(vertex v)
	{
		
		String z;
		z=v.s;
		int i;
		for(i=0;i<9;i++){if(z.charAt(i)=='G')break;}
		int j=i+1;
		if(j==1 || j==7)
		{
			exchange(v,j+1,'L');if(j==1)exchange(v,4,'U');else exchange(v,4,'D');
		}
		else if(j==3 || j==9)
		{
			exchange(v,j-1,'R');if(j==3)exchange(v,6,'U');else exchange(v,6,'D');
		}
		else if(j==2 || j==8)
		{
			exchange(v,j+1,'L');exchange(v,j-1,'R');if(j==2)exchange(v,5,'U');else exchange(v,5,'D');
		}
		else if(j==4 || j==6)
		{
			exchange(v,j+3,'U');exchange(v,j-3,'D');if(j==4)exchange(v,5,'L');else exchange(v,5,'R');
		}
		else
		{
			exchange(v,j+1,'L');exchange(v,j-1,'R');exchange(v,j+3,'U');exchange(v,j-3,'D');
		}
	}

	public void exchange(vertex v,int n,char C)
	{
		int i;String b;
		String a=v.s;char B=a.charAt(n-1);
		for(i=0;i<9;i++){if(a.charAt(i)=='G')break;}
		int j=i+1;
		if(j<n)
		{
			b=new String(a.substring(0,j-1)+a.charAt(n-1)+a.substring(j,n-1)+"G"+a.substring(n,9));
		}
		else
		{
			b=new String(a.substring(0,n-1)+a.charAt(j-1)+a.substring(n,j-1)+a.charAt(n-1)+a.substring(j,9));
		}
		int cost=v.cost;int steps=v.steps;int hashvalue=hashCode(b);
		//System.out.println("found neighbour at step"+steps+":"+b);
		if(h[hashvalue]!=null)
		{
			vertex r=h[hashvalue];
			if(r.cost>cost+cost_list[((int)B)-48] && (!r.in_cloud) )
			{
				r.cost=cost+cost_list[((int)B)-48];
				r.transition="";	r.transition+=C;r.transition+=B;
				r.steps=steps+1;	r.prev=v;	hp.percolateup(r.h_index,r);//System.out.println("updation due to cost diff!");
			}
			else if(r.cost==cost+cost_list[((int)B)-48] && r.steps>steps+1 && (!r.in_cloud) )
			{
				r.transition="";	r.transition+=C;r.transition+=B;
				r.steps=steps+1;	r.prev=v;	hp.percolateup(r.h_index,r);//System.out.println("updation due to step diff!");
			}
		
			//else{System.out.println("updation not needed!");}
		}
		else
		{
			vertex r=new vertex();	r.cost=cost_list[((int)B)-48]+cost;
			r.transition+=C;r.transition+=B;	r.steps=steps+1;	r.s=new String(b);
			r.prev=v;	r.hashvalue=hashCode(b);	h[r.hashvalue]=r;	//System.out.println("inserting into heap");
			hp.insert(r);
		}
	}
}

	class vertex
	{
		public vertex(){}
		public int cost=100000000;
		public int steps;
		public String s;
		public vertex prev=null;
		public boolean in_cloud=false;
		public int hashvalue;
		public String transition=new String();
		public int h_index;
	}
	class heap
	{
		int size;
		public vertex a[];
		public heap()
		{
			a=(vertex[])new vertex[362881];
		}
	
		public void heapify()
		{
			for(int i=(size/2);i>0;i--)
				percolatedown(i,a[i]);
		}

		public void clear(){a=null;size=0;}
		
		public vertex deletemin()
		{
			if(size>0)
			{
				vertex p=a[1];p.in_cloud=true;p.h_index=-1;a[1]=a[size];a[1].h_index=1;size--;
				percolatedown(1,a[1]);
				return p;
			}
			else return null;
		}

		public void insert(vertex c)
		{
			size++;
			a[size]=c;c.h_index=size;percolateup(size,a[size]);
		} 

		public void percolatedown(int i,vertex c)
		{
			int n=c.cost;
			int j;
			if(2*i>size){a[i]=c;c.h_index=i;}

			else if(2*i==size)
			{
				if(n>a[2*i].cost){a[i]=a[size];a[i].h_index=i;a[size]=c;c.h_index=size;}
				else if(n<a[2*i].cost) {a[i]=c;c.h_index=i;}
				else
				{
					if(c.steps>a[2*i].steps){a[i]=a[size];a[i].h_index=i;a[size]=c;c.h_index=size;}
					else {a[i]=c;c.h_index=i;}
				}
			}

			else
			{
				if(a[2*i].cost<a[2*i+1].cost) j=2*i; 
				else if(a[2*i].cost>a[2*i+1].cost) j=2*i+1;
				else
				{
					if(a[2*i].steps<a[2*i+1].steps) j=2*i;
					else j=2*i+1;
				}

				if(a[i].cost<a[j].cost){a[i]=c;c.h_index=i;}
				else if(a[i].cost>a[j].cost){a[i]=a[j];a[i].h_index=i;a[j]=c;c.h_index=j;percolatedown(j,c);}
				else
				{
					if(a[i].steps<=a[j].steps){a[i]=c;c.h_index=i;}
					else {a[i]=a[j];a[i].h_index=i;a[j]=c;c.h_index=j;percolatedown(j,c);}
				}
			}
		}

		public void percolateup(int i,vertex v)
		{
			int n=v.cost;
			if(i==1){a[i]=v;v.h_index=i;}
			else
			{
				if(n>a[i/2].cost){a[i]=v;v.h_index=i;}
				else if(n<a[i/2].cost)
				{
					a[i]=a[i/2];a[i].h_index=i;a[i/2]=v;v.h_index=(i/2);percolateup(i/2,a[i/2]);
				}
				else if(n==a[i/2].cost)
				{
					if(v.steps>=a[i/2].steps){a[i]=v;v.h_index=i;}
					else {a[i]=a[i/2];a[i].h_index=i;a[i/2]=v;v.h_index=(i/2);percolateup(i/2,a[i/2]);}
				}
			}
		}
	}
