import java.util.Scanner; 
public class FabricBreakup
{
	public static void main(String[] args)
	{
		Scanner s=new Scanner(System.in);
		int num=s.nextInt();
		breakup b=new breakup(); b.n(num);
		try{ for(int i=0;i<num;i++)
			{ 
				int x=s.nextInt();    int y=s.nextInt();   int z=0;   if(y==1)z=s.nextInt();  int q=0;
				if(x!=(i+1))  System.out.println("The index of operation is invalid, hence ignoring "+(i+1)+"th operation");
				else
				{
					if(y==1 && (z<=10000) && (z>=0))  b.push(z);
					else if(y==2)  {  q=b.remove();  System.out.println ((i+1)+" "+q);}
					else System.out.println("Invalid operation value (OR) Invalid shirt preference number, hence ignoring "+(i+1)+"th operation");
				}
				
			}}
		catch(java.util.NoSuchElementException e){System.out.println("This output is corresponding to incomplete input file.");}
	}

	 int size=0,sizef=0,c,w;
	 int [] s; int [] f;
	 
	public void n(int n) {  s=new int [n];   f=new int[n];  f[0]=0;  s[0]=0;  }

	//removes the fav shirt along with all on top of that
	public int remove()
	{
		c=0;
		if(size>0) {c=size-f[sizef-1];   size=f[sizef-1];  if(size==0)s[0]=0 ; sizef-- ;  return c-1;}
		else return -1;
	}

	public void push(int n) { if(sizef==0)w=0;else w=f[sizef-1] ; if(n>=s[w]) { f[sizef]=size; sizef++;}  s[size]=n; size++; }

	public int size() {return size;}

	//public boolean isempty() { if(size==0) return true; else return false; }	
}