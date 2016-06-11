package sourcePackage;
//all hail stackoverflow
public class Touple<F, S, T> {
    private F first; //first member of pair
    private S second; //second member of pair
    private T third; //third memeber of pair
    public Touple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third =third;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }
    public void setThird(T third){
    	this.third =third;
    }
    
    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
    public T getThird(){
    	return third;
    }
    
    
}