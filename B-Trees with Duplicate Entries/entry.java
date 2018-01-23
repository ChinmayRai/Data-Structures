public class entry
{
    public Comparable key;
    public Object value;
    public entry prev=null;
    public entry next=null;
    public entry(){}
    public entry(Comparable k,Object v){   key=k;value=v;  }
    public String toString()
    {
    	String s=new String();	String a=new String(key.toString());	String b=new String(value.toString());
    	s=s+a+"="+b;return s;
    }
}