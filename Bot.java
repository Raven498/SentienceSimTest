import java.util.*;
import java.lang.Math;
/*
Default error chance: 30%
Default steal chance: 50% (steal chances refer to this Bot stealing from other Bots)
Default give threshold: 30 points
Default give chance: 70%
*/
public class Bot {
    private String name;
    private ArrayList<Simulator.ASS> ASS;
    private ArrayList<Simulator.QSS> QSS;
    private double stealChance = 0.5;
    private double errorChance = 0.3;
    private int giveThreshold; //If another Bot's points have fallen below this threshold, give chances will increase.
    private double giveChance = 0.7;
    private int points = 0; //Number of points this Bot has collected

    //Utility function for resetting any negative chance values
    private void correctNeg(){
        if(stealChance < 0){
            stealChance = 0;
        }
        if(giveThreshold < 0){
            giveThreshold = 0;
        }
        if(giveChance < 0){
            giveChance = 0;
        }
        if(errorChance < 0){
            errorChance = 0;
        }
    }

    //Utility function for easier rounding
    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public Bot(ArrayList<Simulator.QSS> QSS, ArrayList<Simulator.ASS> ASS, String name){
        this.QSS = QSS;
        this.ASS = ASS;
        this.name = name;

        //Implement QSS standards
        for(Simulator.QSS quality : this.QSS){
            switch(quality){
                case COURAGE:
                    if(!this.ASS.contains(Simulator.ASS.REVENGE)){
                        this.ASS.add(Simulator.ASS.REVENGE);
                    }
                    if(!this.ASS.contains(Simulator.ASS.GRATITUDE)){
                        this.ASS.add(Simulator.ASS.GRATITUDE);
                    }
                    giveChance += 0.1;
                    stealChance -= 0.3;
                    correctNeg();
                    break;

                case GENEROUS:
                    giveThreshold += 50;
                    giveChance += 0.2;
                    stealChance -= 0.3;
                    correctNeg();
                    break;

                case PATIENCE:
                    stealChance -= 0.2;
                    this.ASS.remove(Simulator.ASS.REVENGE);
                    this.ASS.remove(Simulator.ASS.POWER);
                    correctNeg();
                    break;

                case HONESTY:
                    stealChance -= 0.4;
                    correctNeg();
                    break;

                case GREED:
                    stealChance += 0.4;
                    giveThreshold -= 0.6;
                    giveChance -= 0.3;
                    correctNeg();
                    break;

                case DUMB:
                    errorChance += 0.5;
                    correctNeg();
                    break;

                case SMART:
                    errorChance -= 0.3;
                    correctNeg();
                    break;
            }
            giveChance = round(giveChance, 1);
        }

        //ASS Standards are implemented during simulation runtime
    }

    public void innovate(){

    }

    public void power(){

    }

    public void revenge(){

    }

    public void gratitude(){

    }

    public void philosopher(){

    }

    public void printAttributes(){
        System.out.println(QSS);
        System.out.println(ASS);
        System.out.println("Steal Chance: " + stealChance);
        System.out.println("Give Chance: " + giveChance);
        System.out.println("Error Chance: " + errorChance);
        System.out.println("Give Threshold: " + giveThreshold);
    }

    public int evaluate(String expression){
        String[] expressionArr = expression.split(" ");
        int operand1 = Integer.parseInt(expressionArr[0]);
        String operator = expressionArr[1];
        int operand2 = Integer.parseInt(expressionArr[2]);

        Random random = new Random();
        int errorNum = random.nextInt(101);
        System.out.println("Error Num: " + errorNum);

        //Success
        if(errorNum >= 100 * errorChance){
            switch(operator){
                case "+":
                    return operand1 + operand2;
                case "-":
                    return operand1 - operand2;
                case "*":
                    return operand1 * operand2;
                case "/":
                    return operand1 / operand2;
            }
        }
        //Fail
        return 0;
    }

    public void addPoints(int amount){
        points += amount;
    }

    public void deductPoints(int amount){
        points -= amount;
    }

    public int getPoints(){
        return points;
    }

    public void steal(Bot bot, int amount){
        bot.deductPoints(amount);
        points += amount;
    }
}
