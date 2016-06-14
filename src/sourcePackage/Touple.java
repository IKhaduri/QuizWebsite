package sourcePackage;
//all hail stackoverflow
public class Touple<F, S, T> {
    private F first; //first member of pair
    private S second; //second member of pair
    private T third; //third member of pair
    /**
     * public constructor
     * @param first - first parameter in touple
     * @param second - second parameter in touple
	 * @param third - third parameter in touple
     * */
    public Touple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third =third;
    }
    /**
     * getter for first parameter
     * 
     * **/
    public F getFirst() {
        return first;
    }
    /**
     * getter for second parameter
     * 
     * **/
    public S getSecond() {
        return second;
    }
    /**
     * getter for third parameter
     * **/
    public T getThird(){
    	return third;
    }
}