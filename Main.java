import java.util.*;

class RPN
{

    static int getPrecedence(char ch)
    {
      if (ch == '+' || ch == '-')
        return 1;
      else if (ch == '*' || ch == '/')
        return 2;
      else if (ch == '^')
        return 3;
      else
        return -1;
    }
    
    static boolean hasLeftAssociativity(char ch)
    {
      if (ch == '+' || ch == '-' || ch == '/' || ch == '*')
        return true;
      else
        return false;
    }

    static List<String> stringParser(String str)
    {
        String tmp = new String("");
        List<String> strArr = new LinkedList<>();
        for (int i = 0; i < str.length(); i++)
        {
            if (hasLeftAssociativity(str.charAt(i)))
            {
                if (tmp != "")
                {
                  strArr.add(tmp);
                }
                strArr.add(Character.toString(str.charAt(i)));
                tmp = "";
            }
            else if (str.charAt(i) == '(')
            {
              strArr.add(Character.toString(str.charAt(i)));
            }
            else if (str.charAt(i) == ')')
            {
              if (tmp != "")
              {
                strArr.add(tmp);
                tmp = "";
              }
              strArr.add(Character.toString(str.charAt(i)));
            }
            // else if (str.charAt(i) == '(' || str.charAt(i) == ')')
            // {
            //   if (str.charAt(i) == ')' && tmp != "")
            //   {
            //     strArr.add(tmp);
            //     tmp = "";
            //   }
            //   strArr.add(Character.toString(str.charAt(i)));
            // }
            else if (i == str.length()-1)
            {
                strArr.add(Character.toString(str.charAt(i)));
            }
            else
            {
              if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.')
              {
                tmp += str.charAt(i);
              }
            }
        }
        
        System.out.println(strArr);
        return strArr;
    }

    static double mergeRpn(List<String> strArr)
    {
      Stack<Double> stack = new Stack<>();
      for (int i = 0; i < strArr.size(); i++)
      {
        if (hasLeftAssociativity(strArr.get(i).charAt(0)) && stack.size() != 1)
        {
          double first = stack.pop();
          double second = stack.pop();
          switch(strArr.get(i).charAt(0))
          {
            case '+':
              stack.push(second + first);
              break;
            case '-':
              stack.push(second - first);
              break;
            case '*':
              stack.push(second * first);
              break;
            case '/':
              stack.push(second / first);
              break;
          }
        }
        else
        {
          stack.push(Double.parseDouble(strArr.get(i)));
        }
      }
      return stack.pop();
    }

    static double intfixToRpn(String expression)
    {
      List<String> strArr =  stringParser(expression);
      Stack<String> stack = new Stack<>();

      List<String> output = new LinkedList<>();

      for (int i = 0; i < strArr.size(); i++)
      {
        String c = strArr.get(i);

        if (Character.isLetterOrDigit(c.charAt(0)))
          output.add(c);
        
        else if (c.charAt(0) == '(')
          stack.push(c);
        
        else if (c.charAt(0) == ')')
        {
          while (!stack.empty() && stack.peek().charAt(0) != '(')
               output.add(stack.pop());

          stack.pop();
        }
        else
        {
          while (!stack.empty() && getPrecedence(c.charAt(0)) <= getPrecedence(stack.peek().charAt(0)) && hasLeftAssociativity(c.charAt(0)))
          {
            output.add(stack.pop());
          }
          stack.push(c);
        }
      }

      while (!stack.empty())
      {
        if (stack.peek().charAt(0) == '(')
        {
          // System.out.println(output);
          return -1;
        }
        output.add(stack.pop());
      }

      return mergeRpn(output);
    }


  public static void main(String[] args)
  {
    Scanner scanner = new Scanner(System.in);
    String str = scanner.nextLine();
    scanner.close();
    System.out.println(intfixToRpn(str));
  }
}