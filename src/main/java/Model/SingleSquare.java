package Model;

import java.io.Serializable;

public class SingleSquare implements Serializable {
    private int boxvalue;
    /**
     * the number (from 1 to 9) that will be in the singelSquare
     */
    private boolean Visebility;
    /**
     * Visebility If boxValue shall be shown in the View
     */

    private boolean Lockage;
    /**
     * Lockage If the number can be changed by user / number is a starting number
     */



    /**
     * constructor
     * @param boxvalue
     */
    protected SingleSquare(int boxvalue){
        this.boxvalue = boxvalue;

        if(this.boxvalue==0){
            this.Visebility = false;
            this.Lockage=true;
        }else {
            this.Visebility = true;
            this.Lockage= false;
        }
    }

    /**
     * getters
     * @return
     */
    public int getValue() {
        return boxvalue;
    }
    public boolean getVisebility() {
        return Visebility;
    }

    public boolean getLockage() {
        return Lockage;
    }

    /**
     * setters
     * @param
     */
    public void setBoxValue(int box) {
        this.boxvalue = box;
    }
    public void setVisebility(Boolean visebility) {
        this.Visebility = visebility;
    }

    public void setLockage(boolean lockage) {
        this.Lockage = lockage;
    }


    @Override
    public String toString() {
        return  "The value in the singelSquare is"+ boxvalue ;
    }

}
