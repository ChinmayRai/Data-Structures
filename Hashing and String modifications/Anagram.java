import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.Arrays;
import java.lang.*;
//assuming non-empty vocabulary
public class Anagram
{
	public ArrayList<Integer> [] l;
	public int [] p;
	public int n;
	public String [] v;
	public ArrayList<char []> q1,q2,q4,q5;
	public ArrayList<String> ans,q3;
	public Anagram(Scanner s)
	{
		l=(ArrayList<Integer>[])new ArrayList [104729];p=new int[123];ans=new ArrayList<String>();q3=new ArrayList<String>();
		q1=new ArrayList<char[]>();q2=new ArrayList<char[]>();q4=new ArrayList<char[]>();q5=new ArrayList<char[]>();
		for(int i=0;i<104729 ;i++)l[i]=new ArrayList<Integer>();

		p[97]=2;p[98]=11;p[99]=23;p[100]=43;p[101]=3;p[102]=73;p[103]=17;p[104]=19;p[105]=5;p[106]=29;p[107]=31;p[108]=37;p[109]=41;   
		p[110]=7;p[111]=47;p[112]=53;p[113]=59;p[114]=61;p[115]=67;p[116]=71;p[117]=13;p[118]=79;p[119]=83;p[120]=89;p[121]=97;    
		p[122]=101;p[48]=103;p[49]=107;p[50]=109;p[51]=113;p[52]=127;p[53]=131;p[54]=137;p[55]=141;p[56]=149;p[57]=151;p[39]=157;

		n=s.nextInt();  String null2=new String(s.nextLine());  v=new String[n];  for(int i=0;i<n;i++){v[i]=s.nextLine();}
	}

	public static void main(String[] args)
	{
		try{
		int k;String w1=args[0];String u1=args[1];String [] a;
		File p1=new File(w1);Scanner s=new Scanner(p1);//contains vocabulary
		File q1=new File(u1);Scanner t=new Scanner(q1);//contains input
		k=t.nextInt();a=new String[k];
		Anagram u=new Anagram(s);
		String null1=new String(t.nextLine());
		for(int i=0;i<k;i++){a[i]=t.nextLine();}
		u.hashvocab(u.v);u.ans.clear();

		for(int i=0;i<k;i++)
		{
			int b=0;u.process(a[i]);Collections.sort(u.ans);
			for(int j=0;j<u.ans.size()-1;j++)
			{
				if(!u.ans.get(j).equals(u.ans.get(j+1)))
					{System.out.println(u.ans.get(j));b++;}
			}
			if(u.ans.size()>=1){System.out.println(u.ans.get(u.ans.size()-1));b++;}
			System.out.println("-1");u.ans.clear();//System.out.println(b);
		}
		
		}catch(FileNotFoundException e){}
	}

	public void process(String a)
	{
		long startTime=System.currentTimeMillis();
		int k=hash(a);int p=l[k].size(); int d=a.length();if(d>=13)return;                     
		for(int j=0;j<p;j++){if(Arrays.equals(sort(a),sort(v[l[k].get(j)])))ans.add(v[l[k].get(j)]);}
		if(d>=6)
		{
			findanagrams(3,a);
		}

		if(d>=8)
		{
			findanagrams(4,a);
		}
		if(d>=10)
		{
			findanagrams(5,a);
		}
		if(d==12)
		{
			findanagrams(6,a);
		}


		if(d==9)
		{
			findanagrams2(3,3,a);
		}

		if(d==10)
		{
			findanagrams2(3,3,a);
		}
		if(d==11)
		{
			findanagrams2(3,3,a);
			findanagrams2(4,3,a);
		}
		if(d==12)
		{
			findanagrams2(3,3,a);
			findanagrams2(3,4,a);
			findanagrams2(4,3,a);
			findanagrams2(4,4,a);
		}
	}

	public void findanagrams(int t,String a)
	{
		char []w=sort(a);int d=a.length();
		q1.clear();q2.clear();combinations(w,t,q1);combinations(w,d-t,q2);
		for(int i=0;i<q1.size();i++)
		{
			String z=new String((char[])q1.get(i));int e=hash(z);
			if(l[e].size()>0)
			{
				for(int h=0;h<l[e].size();h++)
				{
					String x=v[l[e].get(h)];   int g=q1.size()-1-i;  int f=hash(new String((char[])q2.get(g)));
					if(l[f].size()>0)
					{
						for(int j=0;j<l[f].size();j++)
						{
							String y=v[l[f].get(j)];
							if(Arrays.equals(sort(a),sort(x+y)))
							{ans.add(x+" "+y);ans.add(y+" "+x);}
						}
					}
				}
			}
		}
	}

	public void findanagrams2(int t1, int t2, String a)
	{
		char []w=sort(a);int d=a.length();q1.clear();q2.clear();q3.clear();combinations(w,t1,q1);combinations(w,d-t1,q2);
		for(int i=0;i<q1.size();i++)
		{
			String z=new String((char[])q1.get(i));int e=hash(z);
			if(l[e].size()>0)
			{
				q3.clear();int g=q1.size()-1-i;findanagrams3(t2,new String(q2.get(g)),q3);
				for(int h=0;h<l[e].size();h++)
				{
					String x=v[l[e].get(h)];
					for(int j=0;j<q3.size();j++)
					{
						String y=q3.get(j);//System.out.println("x:"+x);System.out.println("y:"+y);
						if(Arrays.equals(sort(a),sort(x+y)))
						{ans.add(x+" "+y);ans.add(y+" "+x);}
					}
				}
			}
		}
	}

	public void findanagrams3(int t,String a, ArrayList<String> a1)
	{
		char []w=sort(a);int d=a.length();
		a1.clear();q4.clear();q5.clear();combinations(w,t,q4);combinations(w,d-t,q5);
		for(int i=0;i<q4.size();i++)
		{
			String z=new String((char[])q4.get(i));int e=hash(z);
			if(l[e].size()>0)
			{
				for(int h=0;h<l[e].size();h++)
				{
					String x=v[l[e].get(h)];   int g=q4.size()-1-i;  int f=hash(new String((char[])q5.get(g)));
					if(l[f].size()>0)
					{
						for(int j=0;j<l[f].size();j++)
						{
							String y=v[l[f].get(j)];
							if(Arrays.equals(sort(a),sort(x+y)))
							{a1.add(x+" "+y);a1.add(y+" "+x);}
						}
					}
				}
			}
		}
	}

	public char [] sort(String a)
	{
		String[] t= a.split(" ");String b;
		if(t.length>1)b=t[0]+t[1];
		else  b=t[0];
		char []p=b.toCharArray();
		
		Arrays.sort(p);return p;
	}

	public void hashvocab(String [] a)
	{
		int i=0;for(i=0;i<n;i++){l[hash(a[i])].add(i);}
	}
	
	public int hash(String a)
	{
		char t[]=a.toCharArray();int product=1;
		for(int j=0;j<t.length;j++)
		{
			product=(   product	  *   (  p[  (int)t[j]  ]  )  ) % 104729 ;
		}
		return product;
	}

	public void combinations(char [] arr, int r,ArrayList<char []> a)
	{
		int n = arr.length;
        setCombination(arr, n, r, a);
	}
	public void setCombination(char arr[], int n, int r, ArrayList<char []> a)
	{   char data[]=new char[r];Arrays.sort(arr);   combine(arr, data, 0, n-1, 0, r, a);   }

	public void combine(char arr[], char data[], int start, int end, int index, int r,ArrayList<char []> a)
    {
        if (index == r)
        {   
            char [] t=new char[data.length];
            for(int i=0;i<data.length;i++){t[i]=data[i];}a.add(t);return;
        }
        for (int j=start; j<=end && end-j+1 >= r-index; j++)
        {
            data[index] = arr[j];
            combine(arr, data, j+1, end, index+1, r, a);
            while (j<end && arr[j] == arr[j+1])j++;
        }
    }
}