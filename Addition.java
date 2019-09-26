/*
  COSC326 Etude 11: Addition
  @Authors Markham Meredith, Ollie Whiteman
  
  Takes input from users for base, and two numbers of up to 1000 digits they wish to add.
  Outputs the result of addition and division by 2,
  all in the given base

*/

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class Addition{


    @SuppressWarnings("resource")
	public static void main(String [] args){

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter Base(1-10 inclusive)");
        int base = sc.nextInt();
        while(base == 0 || base > 10){
            System.out.println("Invalid Base Number! Try again");
            base = sc.nextInt();
        }


        
        System.out.println("Please enter first number you wish to add(up to 1000 digits)");
        sc.nextLine();
        String firstNum = sc.nextLine();
        while(!isValidLength(firstNum)){
            System.out.println("Number too long, try again!");
            firstNum = sc.nextLine();
        }
        int[] firstNumberArr = strToIntArray(firstNum);


        
        System.out.println("Please enter second number you wish to add(up to 1000 digits)");
        String secondNum = sc.nextLine();
        while(!isValidLength(secondNum)){
            System.out.println("Number too long, try again!");
            secondNum = sc.nextLine();
        }
        int[] secondNumberArr = strToIntArray(secondNum);


            
        int[] res = baseAddition(firstNumberArr, secondNumberArr, base);
        String resultString = strBuild(res);
        System.out.println("Result: " + resultString);
        divideByTwo(res, base);
        
        
    }

    
    
    /* Converts the string input of numbers to an int array
       Input: The user input number in string form
       Return: The converted int array
    */
    public static int[] strToIntArray(String num){
        int i = 0;
        int size = 0;
        boolean neg = false;
        if (num.startsWith("-")){
            size = num.length()-1;
        }
        else {
            size = num.length();
        }
        String [] numarr = new String[size];

        
        if (num.startsWith("-")){
            String newstr  = num.substring(1);
            numarr = newstr.split("");
            neg = true;
        }
        else {
            numarr = num.split("");
        }


        
        int [] arr = new int[size];
        for(String str: numarr){
            if(neg) {
                arr[i] = Integer.parseInt(str);
                arr[i] = -arr[i];
                i++;
            }
            else {
                arr[i++] = Integer.parseInt(str);
            }
        }
        System.out.println(Arrays.toString(arr));
        return arr;
    }




    

    public static int[] baseAddition(int [] num1 , int [] num2 , int base){
        //size of result array determined by size of largest number, num1 if same
        int size = 0;
        if (num1.length > num2.length){
            size = num1.length;
        }

        else if (num2.length > num1.length){
            size = num2.length;
        }
        
        else {
        	size = num1.length;
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
                int [] zeroArray = new int[zeroCount+num2.length];
                Arrays.fill(zeroArray,0,zeroCount+1,0);
                for(int j = zeroCount; j <= zeroArray.length-1; j++){
                    if(x <= num2.length-1){
                        zeroArray[j] = num2[x];
                        x++;
                    }
                }
                //System.out.println(Arrays.toString(zeroArray));
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
        return result;
                
    }




    

    public static String strBuild(int [] result){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < result.length; i++){
            int num = result[i];
            sb.append(num);
        }
        
        String StrResult = sb.toString();
        return StrResult;
    }




    
    public static void divideByTwo(int [] dividend, int base){
    	int size = dividend.length;
    	int [] quotient = new int[size];
    	int remainder = 0;
    	int offset = 0;
        int carry = base;

         
        for(int i = 0; i < size; i++){
            quotient[i] = dividend[i]/2;
            if(dividend[i]%2 != 0){
                if(i < size-1){
                    dividend[i+1] += carry;
                }
                else {
                    remainder = 1;
                }
            }
        }

    	 //find amount of leading 0's to remove 
    	 for(int j: quotient){
             if (j != 0){
                 break;
    	     }
    	     offset++;
    	 }
    	 
     	 int [] result = new int[size-offset];
     	 System.arraycopy(quotient, offset, result, 0, result.length);
    	 System.out.println("When the Result is divided by 2 in given base: ");
    	 System.out.println("Quotient: " + strBuild(result) + " Remainder: " + remainder);    	
    }

    public static boolean isValidLength(String input){

        if(input.length() <= 1000){
            return true;
        }
        else {
            return false;
        }
    }


    
}
