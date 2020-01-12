package com.github.dagwud.woodlands.game.commands.natives;

// By Boann on https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
public class MathEvaluator
{
  public static double eval(final String str)
  {
    return new Object()
    {
      int pos = -1, ch;

      void nextChar()
      {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
      }

      boolean eat(int charToEat)
      {
        while (ch == ' ')
        {
          nextChar();
        }
        if (ch == charToEat)
        {
          nextChar();
          return true;
        }
        return false;
      }

      double parse()
      {
        nextChar();
        double x = parseExpression();
        if (pos < str.length())
        {
          throw new RuntimeException("Unexpected: " + (char) ch);
        }
        return x;
      }

      // Grammar:
      // expression = term | expression `+` term | expression `-` term
      // term = factor | term `*` factor | term `/` factor
      // factor = `+` factor | `-` factor | `(` expression `)`
      //        | number | functionName factor | factor `^` factor

      double parseExpression()
      {
        double x = parseTerm();
        for (; ; )
        {
          if (eat('+'))
          {
            x += parseTerm(); // addition
          }
          else if (eat('-'))
          {
            x -= parseTerm(); // subtraction
          }
          else
          {
            return x;
          }
        }
      }

      double parseTerm()
      {
        double x = parseFactor();
        for (; ; )
        {
          if (eat('d'))
          {
            x = roll(x, parseFactor());
          }
          else if (eat('<'))
          {
            x = Math.min(x, parseFactor());
          }
          else if (eat('>'))
          {
            x = Math.min(x, parseFactor());
          }
          else if (eat('*'))
          {
            x *= parseFactor(); // multiplication
          }
          else if (eat('/'))
          {
            x /= parseFactor(); // division
          }
          else
          {
            return x;
          }
        }
      }

      double parseFactor()
      {
        if (eat('+'))
        {
          return parseFactor(); // unary plus
        }
        if (eat('-'))
        {
          return -parseFactor(); // unary minus
        }

        double x;
        int startPos = this.pos;
        if (eat('('))
        { // parentheses
          x = parseExpression();
          eat(')');
        }
        else if ((ch >= '0' && ch <= '9') || ch == '.')
        { // numbers
          while ((ch >= '0' && ch <= '9') || ch == '.')
          {
            nextChar();
          }
          x = Double.parseDouble(str.substring(startPos, this.pos));
        }
        else if (ch >= 'a' && ch <= 'z')
        { // functions
          while (ch >= 'a' && ch <= 'z')
          {
            nextChar();
          }
          String func = str.substring(startPos, this.pos);
          x = parseFactor();
          switch (func)
          {
            case "sqrt":
              x = Math.sqrt(x);
              break;
            case "sin":
              x = Math.sin(Math.toRadians(x));
              break;
            case "cos":
              x = Math.cos(Math.toRadians(x));
              break;
            case "tan":
              x = Math.tan(Math.toRadians(x));
              break;
            default:
              throw new RuntimeException("Unknown function: " + func);
          }
        }
        else
        {
          throw new RuntimeException("Unexpected: " + (char) ch);
        }

        if (eat('^'))
        {
          x = Math.pow(x, parseFactor()); // exponentiation
        }

        return x;
      }
    }.parse();
  }

  private static double roll(double x, double parseFactor)
  {
    int diceCount = (int) x;
    int diceFaces = (int) parseFactor;

    double total = 0;
    for (int i = 0; i < diceCount; i++)
    {
      int diceRoll = (int)(Math.random() * diceFaces) + 1;
      total += diceRoll;
    }
    System.out.println("ROLL: " + diceCount + "d" + diceFaces + "=" + total);
    return total;
  }
}
