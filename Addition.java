import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;



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
        int[] firstNumberArr = strToIntArray(firstNum);

        System.out.println("Please enter second number you wish to add");
        String secondNum = sc.nextLine();
        int[] secondNumberArr = strToIntArray(secondNum);


        System.out.println("base: " + base + " first number: " + firstNum + " second number: " + secondNum);
            
        String res = baseAddition(firstNumberArr, secondNumberArr, base);
        System.out.println("result: " + res);
        

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

    public static String baseAddition(int [] num1 , int [] num2 , int base){
        //size of result array determined by size of largest number
        int size = 0;
        if (num1.length > num2.length){
            size = num1.length;
        }

        else if (num2.length > num1.length){
            size = num2.length;
        }
        int[] result = new int[size];
        int carry = 0;
        int zeroCount = 0;
      

        // if numbers are same length
        if(num1.length == num2.length){
            for(int i = num1.length-1; i >=0; i--){
                int sum = carry + num1[i] + num2[i];
                int remainder = sum % base;
                result[i] = remainder;
                carry = sum/base;
            }
            if(carry > 0){
                int[] temp = new int[result.length + 1];
                System.arraycopy(result, 0, temp, 1, result.length);
                temp[0]= carry;
                result = temp;
            }
        }
        // if numbers arent same length, fill shorter number with 0's to match 1st number then perform addition
        else {
            if(num1.length > num2.length){
                zeroCount = num1.length - num2.length;
                int x = 0;
                int [] zeroArray = new int[zeroCount+num1.length];
                Arrays.fill(zeroArray,0,zeroCount+1,0);
                for(int j = zeroCount; j <= zeroArray.length-1; j++){
                    if(x <= num1.length-1){
                        zeroArray[j] = num1[x];
                        x++;
                    }
                }
                for(int i = num1.length-1; i >=0; i--){
                    int sum = carry + num1[i] + zeroArray[i];
                    int remainder = sum % base;
                    result[i] = remainder;
                    carry = sum/base;
                }
                if(carry > 0){
                    int[] temp = new int[result.length + 1];
                    System.arraycopy(result, 0, temp, 1, result.length);
                    temp[0]= carry;
                    result = temp;
                }
                
                
            }
            else if (num2.length > num1.length){
                zeroCount = num2.length - num1.length;
                int x = 0;
                int [] zeroArray = new int[zeroCount+num1.length];
                Arrays.fill(zeroArray,0,zeroCount+1,0);
                for(int j = zeroCount; j <= zeroArray.length-1; j++){
                    if(x <= num1.length-1){
                        zeroArray[j] = num1[x];
                        x++;
                    }
                }    
                for(int i = num2.length-1; i >=0; i--){
                    int sum = carry + zeroArray[i] + num2[i];
                    int remainder = sum % base;
                    result[i] = remainder;
                    carry = sum/base;
                }
                if(carry > 0){
                    int[] temp = new int[result.length + 1];
                    System.arraycopy(result, 0, temp, 1, result.length);
                    temp[0]= carry;
                    result = temp;
                }
            }
                
                
                
                    
        }
        //converts result array to string
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < result.length; i++){
            int num = result[i];
            sb.append(num);
        }
        
        String StrResult = sb.toString();
        return StrResult;
                
    }  

    
}
