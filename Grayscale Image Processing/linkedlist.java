public class linkedlist
{
	public listnode head=null;
	public listnode tail=null;
	public int n;
	public int getsize(){return n;}
	public listnode head(){return head;}
	public void addfirst(int a) {head=new listnode(a,head);n++;if(n==1)tail=head;}
	public void add(int a)
	{
		n++; if(n==1)  {tail=new listnode(a,null);  head=tail;}
		else  {listnode t=new listnode(a,null);   tail.setnext(t);  tail=t;}
	}
}

