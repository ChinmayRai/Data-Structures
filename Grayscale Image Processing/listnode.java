public class listnode
{
	public int z;
	public listnode next;
	public listnode(int n,listnode x)
	{
		z=n;
		next=x;
	}
	public listnode(int n)
	{
		this(n,null);
	}
    public int getval(){return z;}
    public listnode getnext(){return next;}
    public void setnext(listnode a){next=a;}
    public void setval(int n){z=n;}
}