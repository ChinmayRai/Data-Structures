import java.util.*;
public class treenode
{
    public int m; // m-node: m children and m-1 members
    public treenode parent=null;
    public ArrayList<entry> member;
    public ArrayList<treenode> children;
    public treenode(int n)
    {
        m=n;
        member=new ArrayList<entry>();
        children=new ArrayList<treenode>();
    }
    public String toString()
    {
    	String s=new String();s=s+"[";
    	if(member.size()==0){s=s+"]";return s;}
    	for(int i=0;i<member.size()-1;i++)
    	{	
    		if(children.size()!=0){s=s+children.get(i).toString()+", ";}
    		s=s+member.get(i).toString()+", ";	
    	}
    	if(children.size()!=0)s=s+children.get(member.size()-1)+", "+member.get(member.size()-1)+", "+children.get(children.size()-1);
    	else s=s+member.get(member.size()-1);
    	s=s+"]";return s;
    }
}
