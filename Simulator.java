import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/*
        Default error chance: 30%
        Default steal chance: 50% (steal chances refer to this Bot stealing from other Bots)
        Default give threshold: 30 points
        Default give chance: 70%
*/
public class Simulator {
    private String[] operators = {"+", "-", "*", "/"};
    public enum QSS{ //Quality standard set - used for all Bots
        /*
        Activates REVENGE, GRATITUDE
        Lowers steal chances by 30%
        Increases give chances by 10%
         */
        COURAGE,

        /*
        Give threshold increases by 50 points
        Increases give chance by 20%
        Decreases steal chances by 30%
         */
        GENEROUS,

        /*
        Decreases steal chances by 20%
        Cancels REVENGE, POWER if they are active
         */
        PATIENCE,

        /*
        Decreases steal chances by 40%
         */
        HONESTY,

        /*
        Increases steal chances by 40%
        Decreases give threshold by 60 points
        Decreases give chances by 30%
         */
        GREED,

        /*
        Increases error chances by 50%
         */
        DUMB,

        /*
        Decreases error chances by 30%
         */
        SMART
    }

    public enum ASS { //Ability Standard Set
        INNOVATE,
        POWER, //Can steal points from other Bots, steal Innovations, command other Bots
        REVENGE, //Allows Bots to steal from other Bots that stole from them
        GRATITUDE, //Allows Bots to give to Bots that give to them
        PHILOSOPHER //Bots have a chance to "change" other Bots' QSS
    }

    public boolean check(int evalNum, String expression){
        String[] expressionArr = expression.split(" ");
        int operand1 = Integer.parseInt(expressionArr[0]);
        String operator = expressionArr[1];
        int operand2 = Integer.parseInt(expressionArr[2]);

        switch(operator){
            case "+":
                if(evalNum == operand1 + operand2){
                    return true;
                }
            case "-":
                if(evalNum == operand1 - operand2){
                    return true;
                }
            case "*":
                if(evalNum == operand1 * operand2){
                    return true;
                }
            case "/":
                if(evalNum == operand1 / operand2){
                    return true;
                }
        }

        return false;
    }

    public void runSimulation(){
        Bot hermes = generateBot("hermes");
        hermes.printAttributes();

        Random random = new Random();
        for(int i = 0; i < 5; i++){
            System.out.println("Original Points: " + hermes.getPoints());
            String operand1 = String.valueOf(random.nextInt(101));
            String operand2 = String.valueOf(random.nextInt(101));
            String operator = operators[random.nextInt(operators.length)];
            if(operator.equals("/")){ //Used to ensure there are no decimal answers if division is chosen
                int operand1Int = Integer.parseInt(operand1);
                int operand2Int = Integer.parseInt(operand2);
                operand1 = String.valueOf(operand1Int += operand2Int);
            }
            String expression = operand1 + " " + operator + " " + operand2;
            System.out.println("Expression: " + expression);

            int evalNum = hermes.evaluate(expression);
            System.out.println("Eval Num: " + evalNum);
            if(check(evalNum, expression)){ //WIP
                hermes.addPoints(random.nextInt(11));
            }
            else{
                hermes.deductPoints(random.nextInt(11));
            }
            System.out.println("New Points: " + hermes.getPoints());
        }
    }

    //Builder function for one Bot
    public Bot generateBot(String name){
        ArrayList<QSS> qss = new ArrayList<>();
        ArrayList<ASS> ass = new ArrayList<>();
        Random rng = new Random();

        for(QSS quality : QSS.values()){
            int chance = rng.nextInt(101);
            //0-49
            if(chance < 50) {
                qss.add(quality);
            }
        }

        for(ASS ability : ASS.values()){
            int chance = rng.nextInt(101);
            //0-49
            if(chance < 50) {
                ass.add(ability);
            }
        }

        return new Bot(qss, ass, name);
    }
}
