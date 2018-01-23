import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.List;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value>
{
    public int m;
    public treenode root;
    public int height; //height of fthe tree
    public int size; //no of key value pairs in tree
    public int index;
    public int x=0;

    public BTree(int b) throws bNotEvenException
    {
        if(b%2==1)throw new bNotEvenException();
        else {m=b;root=new treenode(b);root.parent=null;height=0;size=0;}
    }

    @Override
    public boolean isEmpty(){return size==0;}

    @Override
    public int size(){return size;}

    @Override
    public int height(){return height;}

    public boolean lseq(Comparable a,Comparable b){return a.compareTo(b)<=0;}
    public boolean greq(Comparable a,Comparable b){return a.compareTo(b)>=0;}
    public boolean ls(Comparable a,Comparable b){return a.compareTo(b)<0;}
    public boolean gr(Comparable a,Comparable b){return a.compareTo(b)>0;}
    public boolean eq(Comparable a,Comparable b){return a.compareTo(b)==0;}

    public int heightof(treenode t)
    {
        if(t.children.size()==0) return 0; else return 1+heightof(t.children.get(0));
    }

    public String toString()
    {
        return root.toString();
    }
 
    @Override
    public List<Value> search(Key key) throws IllegalKeyException
    {
        List<Value> v=new Vector<Value>();treenode t=new treenode(m);t=find(key,root);
        if(key==null)throw new IllegalKeyException();
        if(t==null)return v;
        else
        {
            int i=0;    for(i=0;i<t.member.size();i++){if(eq(t.member.get(i).key,key)){break;}}
            entry curr=new entry();     curr=t.member.get(i);      while(curr.prev!=null){curr=curr.prev;}
            while(curr!=null){v.add((Value)curr.value);curr=curr.next;}
        }
        return v;
    }

    @Override
    public void insert(Key key, Value val)
    {
        entry a=new entry(key,val);treenode t=find(key,root);
        if(t!=null)
        {
            int i=0;    for(i=0;i<t.member.size();i++){if(eq(t.member.get(i).key,key)){break;}}
            entry curr=new entry(); curr=t.member.get(i);   while(curr.next!=null){curr=curr.next;}
            a.prev=curr;    curr.next=a;    curr=a;
        }
        if (size==0){root.member.add(a);size++;}
        else if (size>0 && size<m-1)
        {
            if(gr(key,root.member.get(root.member.size()-1).key)){root.member.add(a);}
            else
            {
                for(int i=0;i<root.member.size();i++){if(lseq(key,root.member.get(i).key)){root.member.add(i,a);break;}}
            }
            size++; 
        }
        else{insertat(a,root);}
        return;
    }

    public void insertat(entry e,treenode t)
    {
        Comparable key=e.key;treenode p=new treenode(m);p=t;
        if(t.member.size()==m-1)p=split(t);

        if(p.children.size()!=0)
        {
            if(gr(key,p.member.get(p.member.size()-1).key)){index=p.member.size();insertat(e,p.children.get(p.member.size()));return;}
            for(int i=0;i<p.member.size();i++){ if(lseq(key,p.member.get(i).key)){index=i;insertat(e,p.children.get(i));return;}   }
        }
        else
        {
            size++;if(gr(key,p.member.get(p.member.size()-1).key)){p.member.add(e);return;}
            for(int i=0;i<p.member.size();i++){   if(lseq(key,p.member.get(i).key)){p.member.add(i,e);return;}   }
        }
    }

    public treenode split(treenode t)
    {
        int n=(m/2)-1;entry e=t.member.get(n);
        if(t.equals(root))
        {
            if(t.children.size()==0)
            {
                treenode a=new treenode(m);
                treenode l=new treenode(m);
                treenode r=new treenode(m);
                a.member.add(e);

                for(int i=0;i<n;i++)    {   l.member.add(t.member.get(i));  }

                for(int i=n+1;i<m-1;i++)    {   r.member.add(t.member.get(i));  }

                a.children.add(l);  a.children.add(r);  

                l.parent=a; r.parent=a; root=a; height++;   return a;
            }
            else
            {   
                treenode a=new treenode(m);
                treenode l=new treenode(m);
                treenode r=new treenode(m);
                a.member.add(e);

                for(int i=0;i<n;i++)    { l.member.add(t.member.get(i)); l.children.add(t.children.get(i)); t.children.get(i).parent=l; }

                l.children.add(t.children.get(n));t.children.get(n).parent=l;

                for(int i=n+1;i<m-1;i++){r.member.add(t.member.get(i)); r.children.add(t.children.get(i)); t.children.get(i).parent=r;  }

                r.children.add(t.children.get(m-1));t.children.get(m-1).parent=r;

                a.children.add(l); a.children.add(r); l.parent=a;

                r.parent=a; root=a; height++;   return a;
            }
        }
        else
        {
            
            if(t.children.size()==0)
            {
                treenode p=new treenode(m);     p=t.parent;
                treenode l=new treenode(m);
                treenode r=new treenode(m);

                if(index!=p.member.size())p.member.add(index,e);else p.member.add(e);

                for(int i=0;i<n;i++){l.member.add(t.member.get(i));}

                for(int i=n+1;i<m-1;i++){r.member.add(t.member.get(i));}

                p.children.set(index,l);

                if(index!=p.member.size())p.children.add(index+1,r);else p.children.add(r);

                r.parent=p;  l.parent=p;   return p;
            }
            else
            {
                treenode p=new treenode(m);     p=t.parent;
                treenode l=new treenode(m);
                treenode r=new treenode(m);

                if(index!=p.member.size())p.member.add(index,e);else p.member.add(e);

                for(int i=0;i<n;i++)    {   l.member.add(t.member.get(i));  l.children.add(t.children.get(i)); t.children.get(i).parent=l; }
                l.children.add(t.children.get(n)); t.children.get(n).parent=l;

                for(int i=n+1;i<m-1;i++)    {   r.member.add(t.member.get(i));  r.children.add(t.children.get(i)); t.children.get(i).parent=r; }
                r.children.add(t.children.get(m-1)); t.children.get(m-1).parent=r;

                p.children.set(index,l);

                if(index!=p.member.size())p.children.add(index+1,r);else p.children.add(r);

                r.parent=p;  l.parent=p;   return p;
            }
        }

    }

    public treenode find(Key k,treenode t)
    {
        if(t==null) return null;
        treenode curr =new treenode(m);curr=t;int n=curr.member.size(); int c=curr.children.size();
        //if(c!=0 && c!=n+1)throw new treepropertyException();     
        if(c==0)
        {
            int p=0;while(p<curr.member.size()){if(eq(k,curr.member.get(p).key)){return curr;}p++;}
            return null;
        }

        if(gr(k,curr.member.get(n-1).key))return find(k,curr.children.get(c-1));
        for(int j=0;j<n;j++)
        {
            if(eq(k,curr.member.get(j).key)){if(find(k,curr.children.get(j))==null)return curr;}
            if(lseq(k,curr.member.get(j).key)){return find(k,curr.children.get(j));}
        }
        return null;
    }

    @Override
    public void delete(Key key) throws IllegalKeyException
    {
    try{
            BTree <Key,Value> tree1=new BTree<>(m);
            x=0;ordr(root,key,tree1);
            if(x==1)
            {
                size=tree1.size;height=tree1.height;
                root=tree1.root;
            }
            else{throw new IllegalKeyException();}
        }
        catch(bNotEvenException e)
        {
            System.out.println("b is not even");
        }
    }


    public void ordr(treenode t,Key key,BTree<Key,Value> j)
    { 
        if(t!=null)
        {
            int n=0;
            for(n=0;n<t.member.size();n++)
            {
                if(!eq(key,t.member.get(n).key))
                {
                    j.insert((Key)t.member.get(n).key,(Value)t.member.get(n).value);
                }
                else{x=1;}
                if(t.children.size()!=0)
                {
                    ordr(t.children.get(n),key,j);
                }
            }
        if(t.children.size()!=0)
            ordr(t.children.get(n),key,j);
        }
    }
}