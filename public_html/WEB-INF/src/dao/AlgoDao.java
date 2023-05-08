package dao;

import malgnsoft.db.*;
import malgnsoft.util.*;
import org.apache.commons.digester.StackAction;

import java.util.*;

public class AlgoDao {

    public AlgoDao() {}

    public int findChar(String str, char t) {
        int answer = 0;
        str = str.toUpperCase();
        t = Character.toUpperCase(t);
        
        for (char x : str.toCharArray()) {
            if (x == t) answer++;
        }
        return answer;
    }

    public String upperLower(String str) {
        //문자열 길이는 100이하
        //문자열은 영어만
        if(str.length() >= 100) {return "문자열 100초과";}
        if(!str.matches("^[a-zA-Z]*$")) {return "영어만 입력하시오!";}

        String answer = "";
        //받은 문자열을 반복문 돌리면서 char만들어서 아스키코드 검사하고 반대로 만들어준거지.
        for(char c : str.toCharArray()){
            if(c >= 65 && c <= 95){ //대문자 -> 소문자
                answer += (char)(c+32);
            }else{ // 소문자 -> 대문자
                answer += (char)(c-32);
            }
        }
        return answer;
    }

    public String longestWord(String str) {
        String ans = "";
        String[] subStr = str.split(" ");
        int oriNum = 0;
        for(String s : subStr) {
            int nextNum = s.length();
            if(nextNum > oriNum) {
                oriNum = nextNum;
                ans = s;
            }
        }
        return ans;
    }

    public String reverse(String[] str) {
        return "";
    }

    public String getBiggerNum(String str) {
        ArrayList list = new ArrayList();
        String[] array = str.split(",");
        list.add(array[0]);
        for(int i = 1 ; i < array.length ; i++) {
            int prevNum = Integer.parseInt(array[i-1]);
            int nowNum = Integer.parseInt(array[i]);
            if(prevNum < nowNum) {
                list.add(nowNum);
            }
        }
        return list.toString();
    }

    public String checkTaller(String input) {
        String[] inputArray = input.split(" ");
        String firstStudent = inputArray[0];
        String ret = "키 : " + firstStudent + ", ";
        int max = Integer.parseInt(firstStudent);
        int cnt = 1;

        for(int i = 1 ; i < inputArray.length ; i++) {
            if(Integer.parseInt(inputArray[i]) > max ) {
                max = Integer.parseInt(inputArray[i]);
                cnt++;
                ret += inputArray[i] + ", ";
            }
        }
        ret += " 보이는 학생 : " + cnt;
        return ret;
    }

    public String rockScissorsPaper(String aInput, String bInput) {
        String ret = "";
        String[] aInputArray = aInput.split(" ");
        String[] bInputArray = bInput.split(" ");
        int a = 0;
        int b = 0;

        for(int i = 0 ; i < aInputArray.length ; i++) {
            a = Integer.parseInt(aInputArray[i]);
            b = Integer.parseInt(bInputArray[i]);

            if(a == 1) {//a=가위
                //b : 바위 -> b 승
                if(b == 2) {
                    ret += i + "회수의 승자는 = B <br>";
                } else if(b == 3) {//b : 보 -> a 승
                    ret += i + "회수의 승자는 = A <br>";
                } else {//무승부
                    ret += i + "회수는 무승부 <br>";
                }
            } else if(a == 2) {//바위
                //b : 가위 -> a 승
                if(b == 1) {
                    ret += i + "회수의 승자는 = A <br>";
                } else if(b == 3) {//b : 보 -> b 승
                    ret += i + "회수의 승자는 = B <br>";
                } else {//무승부
                    ret += i + "회수는 무승부 <br>";
                }
            } else if(a == 3) {//보
                //b : 가위 -> b 승
                if(b == 1) {
                    ret += i + "회수의 승자는 = B <br>";
                } else if(b == 2) {//b : 바위 -> a 승
                    ret += i + "회수의 승자는 = A <br>";
                } else {//무승부
                    ret += i + "회수는 무승부 <br>";
                }
            }
        }
        return ret;
    }

    //4
    public String pibonichi(int number) {
        int[] numberArray = new int[number];
        numberArray[0] = 1;
        numberArray[1] = 1;
        String ret = "1 1 ";
        for(int i = 2; i < number; i++) {
            numberArray[i] = numberArray[i-2] + numberArray[i-1];
            ret += numberArray[i] + " ";
        }
        return ret;
    }

    //7
    public String calculateGrade(String scores) {
        String[] scoresArray = scores.split(" ");
        String ret = "총점은 = ";
        int totalScore = 0;
        int cnt = 0;

        for(int i = 0; i < scoresArray.length; i++) {
            if(Integer.parseInt(scoresArray[i]) == 1) {
                cnt++;
                totalScore += cnt;
            } else {
                cnt = 0;
            }
        }

        return ret + totalScore;
    }

    //8
    public String getGrade(String grades) {
        String[] gradeArray = grades.split(" ");
        String ret = "";

        for(int i = 0; i < gradeArray.length; i++) {
            int cnt = 1;
            for(int j = 0; j <gradeArray.length; j++) {
                if(Integer.parseInt(gradeArray[i]) < Integer.parseInt(gradeArray[j])) {
                    cnt++;
                }
            }
            ret += gradeArray[i] + "-> " + cnt + "등 <br> ";
        }
        return ret;
    }

    //two pointer
    //1
    public String twoPointer1(String firstInput, String secondInput) {
        String[] firstArray = firstInput.split(" ");
        String[] secondArray = secondInput.split(" ");
        String answer = "합쳐 배열 = ";
        int pointer1 = 0;
        int pointer2 = 0;

        while(pointer1 < firstArray.length && pointer2 < secondArray.length) {
            if(Integer.parseInt(firstArray[pointer1]) < Integer.parseInt(secondArray[pointer2])) {
                answer += ", " + firstArray[pointer1];
                pointer1++;
            } else {
                answer += ", " + secondArray[pointer2];
                pointer2++;
            }
        }
        while (pointer1 < firstArray.length) answer += ", " + firstArray[pointer1++];
        while (pointer2 < secondArray.length) answer += ", " + secondArray[pointer2++];

        return answer;
    }

    //3
    public String twoPointer2(int firstInput, String secondInput) {
        String[] arr = secondInput.split(" ");
        int answer = 0;
        int sum = 0;

        for(int i = 0; i < arr.length; i++) {
            if((i+firstInput) > arr.length) return answer + "";

            for(int j = 0 ; j < firstInput; j++) {
                sum += Integer.parseInt(arr[i+j]);
            }
            if(sum > answer) answer = sum;
            sum = 0;
        }

        return answer + "";
    }

    //STACK
    //1
    public String stack1(String input) {
        String answer = "YES";
        Stack<Character> answerStack = new Stack();
        for(char a : input.toCharArray()) {
            if(a == '(') {
                answerStack.push(a);
            } else {
                if (answerStack.isEmpty()) {
                    answer = "NO1";
                    return answer;
                }
                answerStack.pop();
            }
        }
        if(!answerStack.isEmpty()) {
            answer = "NO2";
            return answer;
        }
        return answer;
    }
    //2
    public String stack2(String input) {
        String answer = "";
        String[] inputArr = input.split(" ");
        Stack<Character> answerStack = new Stack();

        for(char a : input.toCharArray()) {
            if(a == ')') {
                while(answerStack.pop() != '(');
            } else {
                answerStack.push(a);
            }
        }
        for(int i = 0; i < answerStack.size(); i++) answer += answerStack.get(i);

        return answer;
    }
    //4 후위식
    public String stack4(String input) {
        String answer = "";
        String[] inputArr = input.split(" ");
        Stack<Integer> stack = new Stack();

        for(int i = 0; i < inputArr.length; i++) {
            if("+".equals(inputArr[i]) || "-".equals(inputArr[i]) || "*".equals(inputArr[i]) || "/".equals(inputArr[i])) {

            }
        }

        return answer;
    }
    //6
    public String stack6(int princess, int k) {
        String answer = "";
        Queue<Integer> queue = new LinkedList();
        for(int i = 1; i <= princess; i++) queue.offer(i);
        while(!queue.isEmpty()) {
            for(int i = 1; i < k; i++) queue.offer(queue.poll());//꺼낸걸 뒤에 넣기
            queue.poll();//k번째를 꺼내서 제외
            if(queue.size() == 1) answer = queue.poll() + "";
        }
        return answer;
    }
}