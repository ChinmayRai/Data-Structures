import java.util.Scanner;
import java.util.*;
import java.io.*;
public class LinkedListImage implements CompressedImageInterface {
	int m,n;
	linkedlist [] a,b;
	public LinkedListImage(int q)
	{
		a=new linkedlist[q];
		for(int u=0;u<q;u++)a[u]=new linkedlist();
		m=q;n=q;
	}
	public LinkedListImage(String filename)
	{
		try{
		File p=new File(filename);Scanner s=new Scanner(p);
		m=s.nextInt();n=s.nextInt(); int [][] g=new int [m][n];
		for(int i=0;i<n;i++){for(int j=0;j<m;j++)g[j][i]=s.nextInt();}
		construct(g,m,n);
	    }  catch(FileNotFoundException e){}
	}

	public LinkedListImage(boolean[][] grid, int width, int height)
	{
		int [][] g=new int [width][height];
		for(int i=0;i<height;i++){for(int j=0;j<width;j++){if(grid[j][i])g[i][j]=1;else g[i][j]=0;}}
		construct(g,width,height);
	}

	public void construct(int[][] g,int width,int height)
	{   
		m=width;n=height;a=new linkedlist [n];
		for(int u=0;u<n;u++){a[u]=new linkedlist();}
		for(int i=0;i<n;i++)
		{ 
			for(int j=0;j<m;j++)
				{
					if(g[j][i]==0)
					{
						a[i].add(j);
						while(g[j][i]==0){j++;if(j==m)break;}
						a[i].add(j-1);j--;
					}
				}
			a[i].add(-1);
		}
	}

	public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
	{
		if(x>=n||y>=m)
		throw new PixelOutOfBoundException("Pixel value is invalid");
		else
		{
			listnode curr=a[x].head();
			while(curr.z!=-1)
				{if(y>=curr.z && y<=curr.next.z) return false;
				curr=curr.next.next;}	
			return true;
		}
	}

	public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
	{   
		int v; linkedlist h=new linkedlist();linkedlist k=new linkedlist(); if(val) v=1; else v=0;
		if(x>=n||y>=m)
		throw new PixelOutOfBoundException("Pixel value is invalid");
		else if(getPixelValue(x,y)==val){return;}
		else
		{
			int j=0; listnode curr=a[x].head;
			if(curr.z==-1)for(int o=0;o<m;o++)h.add(1);
			else
			{
				while(j<curr.z){h.add(1);j++;}
				while(curr.z!=-1)
				{
					while(j>=curr.z && j<=curr.next.z){h.add(0);j++;} curr=curr.next;
						if(curr.next.z==-1)while(j<m){h.add(1);j++;}
					while(j>curr.z && j<curr.next.z){h.add(1);j++;}curr=curr.next;
				}
			}
			j=0;  h.add(-1);  curr=h.head;
			while(j<=(y-1)) {curr=curr.next; j++;}
			curr.z=v; 
			listnode curr1=h.head;  int [] r=new int[m];  for(j=0;j<m;j++){r[j]=curr1.z;curr1=curr1.next;}
			for(j=0;j<m;j++)
			{ 
				if(r[j]==0)
					{
						k.add(j);
						while(r[j]==0){j++;if(j==m)break;}
						k.add(j-1);j--;
					}
			}
			k.add(-1);a[x]=k;
		}
	}

	public int[] numberOfBlackPixels()
	{	
		int [] t=new int [n];int b;
		for(int i=0;i<n;i++)
			{
				b=0;listnode curr=a[i].head();
				while(curr.z!=-1){b=b+curr.next.z-curr.z+1;curr=curr.next.next;}t[i]=b;
		}		
		return t;
	}
	
	public void invert()
	{
		listnode curr;  b=new linkedlist[n];  for(int u=0;u<n;u++){b[u]=new linkedlist();}
		for(int i=0;i<n;i++)
		{
			if(a[i].head().z==0)
			{
				curr=a[i].head().next;
				if(curr.z==m-1){b[i].add(-1);}
				else
				{
					while(curr.next.z!=-1)
					{
						b[i].add(curr.z+1);
						b[i].add(curr.next.z-1);
						curr=curr.next.next;
					}
					if(curr.z==m-1)  b[i].add(-1);
					else
					{
						b[i].add(curr.z+1);
						b[i].add(m-1);
						b[i].add(-1);
					}
				}
			}
			else
			{
				b[i].add(0);curr=a[i].head();
				if(curr.z==-1)b[i].add(m-1);
				else
				{
					while(curr.next.next.z!=-1)
						{
							b[i].add(curr.z-1);
						    b[i].add(curr.next.z+1);
						    curr=curr.next.next;
						}
					if(curr.next.z==m-1)  b[i].add(curr.z-1); 
					else 
						{
							b[i].add(curr.z-1);
							b[i].add(curr.next.z+1);
							b[i].add(m-1);
						} 
					
			    }
			    b[i].add(-1);
			}
			
		}
		a=b;
	}
	
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException 
	{
		LinkedListImage im=(LinkedListImage)img;
		if(im.n!=n || im.m!=m)  throw new BoundsMismatchException("Bounds mismatch");
		else
		{
			for(int i=0;i<n;i++)
			{
				int flag=0;  linkedlist h=new linkedlist();
				listnode curr1=a[i].head;  listnode curr2=im.a[i].head; listnode precurr1=null; listnode precurr2 =null;
				while(curr1.z!=-1 && curr2.z!=-1 )
				{
					if(precurr2!=null && precurr1!=null)
					{
						if(precurr2.z>=curr1.next.z)  {curr1=curr1.next.next;  precurr1=curr1.next;}
						else if(precurr1.z>=curr2.next.z) {curr2=curr2.next.next;  precurr2=curr2.next;}
					}

					if(curr1.z>curr2.next.z){h.add(curr2.z);h.add(curr2.next.z);precurr2=curr2.next;curr2=curr2.next.next;}
					else if(curr2.z>curr1.next.z){h.add(curr1.z);h.add(curr1.next.z);precurr1=curr1.next;curr1=curr1.next.next;}
					else
					{
						if(curr2.z<curr1.z)h.add(curr2.z);else h.add(curr1.z);
						curr1=curr1.next;  curr2=curr2.next;
						if(curr2.z>curr1.z)h.add(curr2.z);else h.add(curr1.z);
						precurr1=curr1 ;precurr2=curr2 ; curr1=curr1.next; curr2=curr2.next;
					}
				}
				if(curr2.z!=-1 && curr1.z==-1)
				{
					while(curr2.z!=-1){  h.add(curr2.z); curr2=curr2.next;}
				}
				else if(curr1.z!=-1 && curr2.z==-1)
				{
					while(curr1.z!=-1){  h.add(curr1.z) ;curr1=curr1.next;  }
				}
				h.add(-1);listnode curr=h.head;
				while(curr.z!=-1 && curr.next.next.z!=-1 && flag==0)
				{
					flag=1;
					if(curr.next.z+1>=curr.next.next.z)
						{
							if(curr.next.z>=curr.next.next.next.z) curr.next.next.next.z=curr.next.z;
							curr.next=curr.next.next.next ; flag=0;
						}
					if(flag==1){curr=curr.next.next;flag=0;}
				}
				a[i]=h;
			}
		}
	}
	
	public void performOr(CompressedImageInterface img) throws BoundsMismatchException
	{
		LinkedListImage im=(LinkedListImage)img;
		if(im.n!=n || im.m!=m)  throw new BoundsMismatchException("Bounds mismatch");
		invert();im.invert();performAnd(im);invert();im.invert();
	}
	
	public void performXor(CompressedImageInterface img) throws BoundsMismatchException
	{
		LinkedListImage im=(LinkedListImage)img;
		if(im.n!=n || im.m!=m)  throw new BoundsMismatchException("Bounds mismatch");
		invert();
		LinkedListImage x=new LinkedListImage (n);LinkedListImage y=new LinkedListImage (n);
		for(int i=0;i<n;i++){listnode curr=a[i].head();while(curr!=null){x.a[i].add(curr.z);curr=curr.next;}}
		for(int i=0;i<n;i++){listnode curr=im.a[i].head();while(curr!=null){y.a[i].add(curr.z);curr=curr.next;}}	
        invert();im.invert();performAnd(im);x.performAnd(y);performOr(x);im.invert();
	}
	
	public String toStringUnCompressed()
	{
		String p=new String();p=p+m+" "+n+",";
		for(int i=0;i<n;i++)
		{
			int j=0;
			listnode curr=a[i].head();
			if(curr.z==-1)for(int o=0;o<m;o++)p=p+" 1";
			else
			{
			while(j<curr.z){p=p+" 1";j++;}
			while(curr.z!=-1)
			{
				while(j>=curr.z && j<=curr.next.z){p=p+" 0";j++;}
					curr=curr.next;
					if(curr.next.z==-1)while(j<m){p=p+" 1";j++;}
				while(j>curr.z && j<curr.next.z){p=p+" 1";j++;}
					curr=curr.next;
			}
			}
			if(i<n-1)p=p+",";
		}
		return p;
	}
	
	public String toStringCompressed()
	{
		String p=new String();p=p+m+" "+n+", ";
		for(int i=0;i<n;i++)
		{
			listnode curr= a[i].head();
			while(curr.z!=-1)
			{
				p=p+curr.z+" ";
				curr=curr.next;
			}
			if(i<n-1)p=p+"-1, ";
			else if(i==n-1)p=p+"-1";
		}
		return p;
	}

	public static void main(String[] args) {
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try
        {
        	img1.performXor(img2);       
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}

    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }

    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);    
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringCompressed().equals(img_ans));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}
