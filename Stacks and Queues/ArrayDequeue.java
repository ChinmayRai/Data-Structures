public class ArrayDequeue implements DequeInterface
 {
  int max_size=1,head=0,tail=0;int infst=0,inlst=0,rmfst=0,rmlst=0;
  Object [] x;
  public ArrayDequeue(){ x=new Object[1];}


  public void insertFirst(Object o)
  {
  	if(isFull())
  		{ 
  			Object [] a=new Object[2*(max_size)]; for(int i=0;i<max_size;i++){ a[i] = x [(i+head+max_size)%max_size];}
  			head=0;tail=max_size; max_size*=2; x=a; insertFirst(o);
  		}
    else {head=(head-1+max_size)%max_size; x[head]=o; infst++;}
    
  }
  
  public void insertLast(Object o)
  {
  	 if(isFull())
  	 	{ 	
  	 		Object [] a=new Object[2*(max_size)]; for(int i=0;i<max_size;i++){ a[i] = x [(i+head+max_size)%max_size];}
  	 		head=0;tail=max_size; max_size*=2; x=a; insertLast(o);
  	 	} 
     else{x[tail]=o;tail=(tail+1)%max_size;inlst++;}
   
  }
  
  public Object removeFirst() throws EmptyDequeException
  {
  	 if(!isEmpty()) { Object temp=x[head]; rmfst++; head=(head+1)%max_size; return temp; }
  	 else throw new EmptyDequeException("The queue is empty");

  }
  
  public Object removeLast() throws EmptyDequeException
  {
     if(!isEmpty()) {Object temp=x[tail-1]; rmlst++; tail=(tail-1+max_size)%max_size;return temp;}
     else throw new EmptyDequeException("The queue is empty");
  }


  public Object first() throws EmptyDequeException
  {
    if(!isEmpty()) return x[head];
    else throw new EmptyDequeException("The queue is empty");
  }
  
  public Object last() throws EmptyDequeException
  {
    if(!isEmpty()) return x[tail-1];
    else throw new EmptyDequeException("The queue is empty");
  }
  
  public int size() { return (infst+inlst-rmlst-rmfst); }
	
  public boolean isEmpty() { if(head==tail&&(infst+inlst-rmlst-rmfst==0)) return true; else return false; }
  
  public boolean isFull() { if(head==tail&&(infst+inlst-rmlst-rmfst==max_size)) return true; else return false; }

  public String toString()
  {
    if(size()==0) return "[]";
    else
    {
    	String s="["+x[head];
      for(int i=1;i<size();i++) s=s+", "+x[(i+head+max_size)%max_size];
      s=s+"]"; 
    	return s;
    }
   
  }

   public static void main(String[] args)
  {
    int  N = 0;
    DequeInterface myDeque = new ArrayDequeue();
    for(int i = 0; i < N; i++) {
      myDeque.insertFirst(i);
      myDeque.insertLast(-1*i);
   }
   
    int size1 = myDeque.size();
    System.out.println("Size: " + size1);
    System.out.println(myDeque.toString());
    
    if(size1 != 2*N){
      System.err.println("Incorrect size of the queue.");
    }
    
    //Test first() operation
    try{
      int first = (int)myDeque.first();
      int size2 = myDeque.size(); //Should be same as size1
      if(size1 != size2) {
        System.err.println("Error. Size modified after first()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }
    
    //Remove first N elements
    for(int i = 0; i < N; i++) {
      try{
        int first = (Integer)myDeque.removeFirst();
      }
      catch (EmptyDequeException e) {
        System.out.println("Cant remove from empty queue");
      }
      
    }
    
    
    int size3 = myDeque.size();
    System.out.println("Size: " + myDeque.size());
    System.out.println(myDeque.toString());
    
    if(size3 != N){
      System.err.println("Incorrect size of the queue.");
    }
    
    try{
      int last = (int)myDeque.last();
      int size4 = myDeque.size(); //Should be same as size3
      if(size3 != size4) {
        System.err.println("Error. Size modified after last()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }
    
    //empty the queue  - test removeLast() operation as well
    while(!myDeque.isEmpty()){
        try{
          int last = (int)myDeque.removeLast();
        }
        catch (EmptyDequeException e) {
          System.out.println("Cant remove from empty queue");
        }
    }
    
    int size5 = myDeque.size();
    if(size5 != 0){
      System.err.println("Incorrect size of the queue.");
    }
    
  }
  
 
  
}