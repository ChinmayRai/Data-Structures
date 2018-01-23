
public class vertex
{
	public int cost=1000000000;
	public int steps;
	public String s;
	public vertex prev=null;
	public boolean in_cloud=false;
	public int hashvalue=s.hashCode();
	public String transition;
	public int h_index;
}