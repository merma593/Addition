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


public class Addition2{


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

	//System.out.println(Arrays.toString(negativeBorrow(firstNumberArr, 7, 10)));

        if(base ==1){
            List<Integer> base1Res = base1Addition(firstNumberArr, secondNumberArr);
            if(base1Res.size() >= 1){
                System.out.print("Result: ");
                base1Res.forEach(System.out::print); 
                System.out.println();
                base1Div(base1Res);
            }
            else {
                System.out.print("Result: " + 0);
                System.out.println();
                System.out.println("Quotient: " + 0 + " Remainder: " + 0);
            }
        }
        else {
            
            if(firstNumberArr[0] < 0 && secondNumberArr[0] > 0 || secondNumberArr[0] < 0 && firstNumberArr[0] > 0){
                int [] res = negativeAddition(firstNumberArr, secondNumberArr, base);
                String resultString = strBuild(res);
                System.out.println("Result: " + resultString);
                divideByTwo(res, base);
            }
            else {
                int[] res = baseAddition(firstNumberArr, secondNumberArr, base);
                String resultString = strBuild(res);
                System.out.println("Result: " + resultString);
                divideByTwo(res, base);
            }

        }

        
        
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
        return arr;
    }


    public static List<Integer> base1Addition(int [] num1, int [] num2){
        boolean cond = false;
        List<Integer> base1Array = new ArrayList<Integer>();
        List<Integer> base1ArrayZero = new ArrayList<Integer>(1);
        if(num1[0] < 0 && num2[0] > 0){
            if (num1.length < num2.length){
                for(int i = 0; i < num2.length - num1.length; i++){
                    base1Array.add(1);
                }
            }
            
            if(num2.length < num1.length){
                for(int i = 0; i < num1.length - num2.length; i++){
                    base1Array.add(1);
                }
                base1Array.set(0,-1);
            }
        }
        else if(num2[0] < 0 && num1[0] > 0){
            if (num1.length < num2.length){
                int length = num2.length - num1.length;
                System.out.println(length);
                for(int i = 0; i < num2.length - num1.length; i++){
                    base1Array.add(1);
                }
                base1Array.set(0,-1);
            }
            
            if(num2.length < num1.length){
                for(int i = 0; i < num1.length - num2.length; i++){
                    base1Array.add(1);
                }
            }
        }
        else if(num1[0] > 0 && num2[0] > 0){
            for(int i = 0; i < num1.length + num2.length; i++){
                base1Array.add(1);
            }
        }
        else if(num1[0] < 0 && num2[0] > 0 && num1.length == num2.length){
            cond = true;            
        }
        else if(num2[0] > 0 && num1[0] < 0 && num1.length == num2.length){
            cond = true;
        }
        else {
            for(int i = 0; i < num1.length + num2.length; i++){
                base1Array.add(1);
            }
            base1Array.set(0,-1);
        }
        if (cond == false){
            return base1Array;
        }
        else {
            base1ArrayZero.add(0);
            return base1ArrayZero;
        }

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
        boolean neg = false;
      

        // if numbers are same length
        if(num1.length == num2.length){
            
            for(int i = num1.length-1; i >=0; i--){
                
                if(num1[0] < 0 &&  num2[0] > 0){
                    
                    if(num2[i] > Math.abs(num1[i])){
                        num1[i] -= base;
                        num1[i-1] += 1;
                        result[i] = num1[i] + num2[i];
                        
                    }
                    else {
                        int sum  = num1[i] + num2[i];
                        result[i] = sum;
                    }
                }                
                else if(num2[0] < 0 &&  num1[0] > 0){
                    if(num1[i] > Math.abs(num2[i])){
                        num2[i] -= base;
                        num2[i-1] += 1;
                        result[i] = num1[i] + num2[i];
                            
                    }
                    else {
                        result[i] = num1[i] + num2[i];
                    }
                }
                
                else if(num1[0] < 0 && num2[0] < 0){
                    neg = true;
                    for(int z = 0; z < num1.length; z++){
                        for(int j = 0; j < num2.length; j++){
                            num1[z] = Math.abs(num1[z]);
                            num2[j] = Math.abs(num2[j]);
                        }
                    }
                    int sum = carry + num1[i] + num2[i];
                    int remainder = sum % base;
                    result[i] = remainder;
                    carry = sum/base;
                }
                else {
                    int sum = carry + num1[i] + num2[i];
                    int remainder = sum % base;
                    result[i] = remainder;
                    carry = sum/base;
                }
            }
            if(carry > 0){
                int[] temp = new int[result.length + 1];
                System.arraycopy(result, 0, temp, 1, result.length);
                temp[0]= carry;
                result = temp;
           
            }
        }
        // if numbers arent same length, fill shorter number with 0's to match 1st number then perform addition
        else if(num1.length > num2.length){
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
                if(num1[0] < 0 && num2[0] > 0){
                    if(zeroArray[i] > Math.abs(num1[i])){
                        num1[i] -= base;
                        num1[i-1] += 1;
                        result[i] = num1[i] + zeroArray[i];
                    }
                    else {
                        result[i] = num1[i] + zeroArray[i];
                    }
                }
                else if(num2[0] < 0 &&  num1[0] > 0){
                    if(num1[i] < Math.abs(zeroArray[i])){
                        num1[i] += base;
                        num1[i-1] -= 1;
                        result[i] = num1[i] + zeroArray[i];                            
                    }
                    else {
                        result[i] = num1[i] + zeroArray[i];
                    }
                }
                else {
                    int sum = carry + num1[i] + zeroArray[i];
                    int remainder = sum % base;
                    result[i] = remainder;
                    carry = sum/base;
                }
            }
            if(carry != 0){
                int[] temp = new int[result.length + 1];
                System.arraycopy(result, 0, temp, 1, result.length);
                temp[0]= carry;
                result = temp;
            }
                
                
        }
        else{
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
            for(int i = zeroArray.length-1; i >=0; i--){
                if(num1[0] < 0 &&  num2[0] > 0){
                    if(num2[i] < Math.abs(zeroArray[i])){
                        num2[i] += base;
                        num2[i-1] -= 1;
                        result[i] = zeroArray[i] + num2[i];
                    }
                    
                    else {
                        result[i] = zeroArray[i] + num2[i];
                    }
                    
                }
                else if(num2[0] < 0 &&  num1[0] > 0){
                    if(zeroArray[i] > Math.abs(num2[i])){
                        num2[i] -= base;
                        num2[i-1] += 1;
                        result[i] = zeroArray[i] + num2[i];
                            
                    }
                    else {
                        result[i] = zeroArray[i] + num2[i];
                    }
                }

                else {
                    int sum = carry + zeroArray[i] + num2[i];
                    int remainder = sum % base;
                    result[i] = remainder;
                    carry = sum/base;
                }
            }
            if(carry != 0){
                int[] temp = new int[result.length + 1];
                System.arraycopy(result, 0, temp, 1, result.length);
                temp[0]= carry;
                result = temp;
            }
        }
        int offset = 0;
        boolean leading0 = false;
        for(int j = 0; j < result.length; j++){
            if(j == result.length-1){
                break;
            }
            else if(result[j] != 0){
                break;
            }
            offset++;
            leading0 = true;
        }
    	if(leading0 == true){
            int [] result2 = new int[size-offset];
            System.arraycopy(result, offset, result2, 0, result2.length);
            if(neg == true){
                result2[0] = -result2[0];
            }
            return result2;

        }
        else {
            if(neg == true){
                result[0] = -result[0];
            }
            return result;

        }

    }



    public static String strBuild(int [] result){
        StringBuilder sb = new StringBuilder();

        if(result.length == 1 && result[0] == 0){
            sb.append(0);
            String res = sb.toString();
            return res;
        }

        if(result[0] > 0){
            for(int i = 0; i < result.length; i++){
                int num = result[i];
                sb.append(num);
            }
        }

        else {
            for (int i = 0; i < result.length; i++){
                if(i == 0){
                    int num = result[i];
                    sb.append(num);
                }
                else {
                    int num = Math.abs(result[i]);
                    sb.append(num);
                }
            }
        }
        
        String StrResult = sb.toString();
        return StrResult;
    }

    public static void base1Div(List<Integer> base1num){
        int length = 0;
        if(base1num.size() > 1){
            length = base1num.size()/2;
        }
        else {
            length = 1;
        }
        int [] oneArray = new int [length];
        int remainder = 0;
        boolean negative = false;
        if(base1num.get(0) < 0){
            negative = true;
        }
        if(base1num.size() % 2 != 0){
            remainder = 1;
        }

        

        for(int i = 0; i < oneArray.length; i++){
            oneArray[i] = 1;
        }
        if(negative == true){
            oneArray[0] = -oneArray[0];
        }
        
        
        if(length > 1){
            System.out.println("Quotient: " + strBuild(oneArray) + " Remainder: " + remainder);
        }
        else if(length == 1){
            System.out.println("Quotient: " + strBuild(oneArray) + " Remainder: " + 0);
        }
        else {
            System.out.println("Quotient: " + 0 + " Remainder: " + 0);
        }
    

        
    }

    /* divides number by 2 in given base,
       if number is negative, changes to positive
       and performs addition then makes negative */
    
    public static void divideByTwo(int [] dividend, int base){
    	int size = dividend.length;
    	int [] quotient = new int[size];
    	int remainder = 0;
    	int offset = 0;
        int carry = base;
        boolean negative = false;
        boolean zero = false;

        if(dividend[0] < 0){
            negative = true;
            for(int i = 0; i < dividend.length; i++){
                dividend[i] = Math.abs(dividend[i]);
            }
        }

        if(dividend.length == 1 && dividend[0] == 0){
            zero = true;
        }

        if(zero == false){
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
        if(negative == true){
            result[0] = -result[0];
        }
        if(zero == false){
            System.out.println("Quotient: " + strBuild(result) + " Remainder: " + remainder);
        }
        else{
            System.out.println("Quotient: " + 0  + " Remainder: " + 0);
        }
    }

    
    public static int[] negativeAddition(int[] num1, int[] num2, int base){

	int size = 0;
        int carry = 0;
        int zeroCount = 0;
	int[] pos = new int[Math.max(num1.length,num2.length)];
	int[] neg = new int[Math.max(num1.length,num2.length)];
	int[] zeroArray = new int[Math.max(num1.length,num2.length)];
	
	if(num1.length > num2.length){
	    size = num1.length;
	    zeroCount = num1.length - num2.length;
	    int x = 0;
	    Arrays.fill(zeroArray,0,zeroCount+1,0);
	    for(int j = zeroCount; j <= zeroArray.length-1; j++){
		if(x <= num2.length-1){
		    zeroArray[j] = num2[x];
		    x++;
		}
	    }
	    if(num1[0] < 0){
		pos = zeroArray.clone();
		neg = num1.clone();
	    }else{
		pos = num1.clone();
		neg = zeroArray.clone();
	    }
	}else if(num2.length > num1.length){
	    size = num2.length;
	    zeroCount = num2.length - num1.length;
	    int x = 0;
	    Arrays.fill(zeroArray,0,zeroCount+1,0);
	    for(int j = zeroCount; j <= zeroArray.length-1; j++){
		if(x <= num1.length-1){
		    zeroArray[j] = num1[x];
		    x++;
		}		
	    }
	    if(num2[0] < 0){
		pos = zeroArray.clone();
		neg = num2.clone();
	    }else{
		pos = num2.clone();
		neg = zeroArray.clone();
	    }
	}else{
	    size = num1.length;
	    if(num1[0] < 0){
		pos = num2.clone();
		neg = num1.clone();
	    }else{
		pos = num1.clone();
		neg = num2.clone();
	    }
	}
	int[] result = new int[size];
    	//System.out.println(Arrays.toString(pos));
	//System.out.println(Arrays.toString(neg));
	for(int i = size-1; i >=0; i--){
	    if(pos[0] > Math.abs(neg[0])){
		if(pos[i] < Math.abs(neg[i]) && i != 0){
		    pos[i] += base;
		    pos[i-1] -= 1;
		    result[i] = pos[i] + neg[i];
		}else{
		    result[i] = pos[i] + neg[i];
		}
	    }else{
	        if(pos[i] > Math.abs(neg[i])){
		    neg[i] -= base;
		    if(neg[i-1] == 0){
		        neg = negativeBorrow(neg, i-1, base).clone();
		    }else{
			neg[i-1] += 1;
		    }
		    result[i] = pos[i] + neg[i];
		}else{
		    result[i] = pos[i] + neg[i];
		}
	    }
	}
	int offset = 0;
        boolean leading0 = false;
        for(int j = 0; j < result.length; j++){
            if(j == result.length-1){
                break;
            }
            else if(result[j] != 0){
                break;
            }
            offset++;
            leading0 = true;
        }
    	if(leading0 == true){
            int [] result2 = new int[size-offset];
            System.arraycopy(result, offset, result2, 0, result2.length);
            return result2;

        }
        else {
            return result;

        }   
    }

    public static int[] negativeBorrow(int[] arr, int index, int base){
    	if(arr[index-1] != 0){
    	    arr[index] = 1 - base;
    	    arr[index-1] += 1;
    	}else{
    	    arr[index] = 1 - base;
    	    negativeBorrow(arr, index-1, base);
    	}
    	return arr;
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
