import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Addition{
    /* main method
       maybe need to add a method that checks string inputs are ints
    */

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter Base");
        int base = sc.nextInt();
        
        System.out.println("Please enter first number you wish to add");
        sc.nextLine();
        String firstNum = sc.nextLine();

        System.out.println("Please enter second number you wish to add");
        String secondNum = sc.nextLine();


        System.out.println("base: " + base + " first number: " + firstNum + " second number: " + secondNum);

        int[] firstNumberArr = strToIntArray(firstNum);
        int[] secondNumberArr = strToIntArray(secondNum);

        //System.out.println(Arrays.toString(firstNumberArr));
        //System.out.println(Arrays.toString(secondNumberArr));

        String res = baseAddition(firstNumberArr, secondNumberArr, base);
        System.out.println(res);
    }

    
    
    /* Converts the string input of numbers to an int array
       Input: The user input number in string form
       Return: The converted int array
    */
    public static int[] strToIntArray(String num){
        int i = 0;
        String numarr [] = num.split("");
        int [] arr = new int [num.length()];
        for(String str: numarr){
            arr[i++] = Integer.parseInt(str);
        }
        return arr;
    }

    public static String baseAddition(int [] num1, int [] num2, int base){
        List<Integer> result = new ArrayList<>();
        
        for(int i = num1.length-1; i > 0; i--){
            for(int j = num2.length-1; i > 0; i--){
                if(num1[i] + num2[j] > base){
                    int sum = num1[i] + num2[j];
                    int remainder = sum - base;
                    if(result.size() == 0) {
                        result.add(remainder);
                        num1[i-1] += 1;
                    }
                    else{
                        result.add(result.get(i), remainder);
                        num1[i-1] += 1;
                    }
                }

                else {
                    result.add(result.get(i), num1[i] + num2[j]);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++){
            int num = result.get(i);
            sb.append(num);
        }
        String StrResult = sb.toString();
        return StrResult;


        
    }

    

    
}
