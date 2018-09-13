package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * C:\Users\Administrator\Desktop\test.txt
 * wc.exe 是一个常见的工具，它能统计文本文件的字符数、单词数和行数。这个项目要求写一个命令行程序，模仿已有
 * wc.exe 的功能，并加以扩充，给出某程序设计语言源文件的字符数、单词数和行数。
实现一个统计程序，它能正确统计程序文件中的字符数、单词数、行数，以及还具备其他扩展功能，并能够快速地处理多个文件。
具体功能要求：
程序处理用户需求的模式为：
wc.exe [parameter] [file_name]
 
 
基本功能列表：
wc.exe -c file.c    //返回文件 file.c 的字符数
wc.exe -w file.c    //返回文件 file.c 的词的数目  
wc.exe -l file.c    //返回文件 file.c 的行数

扩展功能：
    -s   递归处理目录下符合条件的文件。
    -a   返回更复杂的数据（代码行 / 空行 / 注释行）。
空行：本行全部是空格或格式控制字符，如果包括代码，则只有不超过一个可显示的字符，例如“{”。
代码行：本行包括多于一个字符的代码。
注释行：本行不是代码行，并且本行包括注释。一个有趣的例子是有些程序员会在单字符后面加注释：
    } //注释
在这种情况下，这一行属于注释行。
[file_name]: 文件或目录名，可以处理一般通配符。


 *
 */
public class wcUtil {
	   int charNum=0;//总字符数
	   int wordNum=0;//单词数   
	   int line=0;//行数
	   int blankLine=0;//空行数
	   int codeLine=0;//代码行
	   int commentLine=0;//注释行
   //实现基本功能	   
   public void basicFunction(File file) throws IOException{
	  
	   int currentCharNum;//当前行字符数
	   int curStringLength=0;//当前行分割后各个字符串长度
	   int currentWordNum;//当前行单词数
	   BufferedReader br=new BufferedReader(new FileReader(file));
	   String currentLine=null;	
	   while((currentLine=br.readLine())!=null) {
		   currentCharNum=0;
		   currentWordNum=0;
		   String tempCurLine = currentLine;
		   //把当前行去掉前导和尾部空白后通过空格分割为字符串数组
		   String[] splitedString=currentLine.trim().split(" ");
		   //计算该行中每个单独字符串的长度再相加，得到该行字符数
		   for(int i=0;i<splitedString.length;i++) {
			   curStringLength=splitedString[i].length();
			   currentCharNum+=curStringLength;
		   }
		   //每行字符数相加得到总数
		   charNum+=currentCharNum;
		   //统计单词数
		   //把当前行非字母的字符替换为空格
		   tempCurLine = tempCurLine.replaceAll("[^a-zA-Z]"," ");
		   //以空格分割本行字符串为若干个字符串数组
		   String[] splitedString2=tempCurLine.trim().split("\\s+");
		   //字符串数组的长度即为该行单词数
		   currentWordNum=splitedString2.length;
		   //避免当前行有字符且所有字符都不为字母时字符串数组无长度
		   if(currentWordNum!=0) {
			   if("".equals(splitedString2[0])){
				   currentWordNum-=1;
			   }
		   }
		   wordNum += currentWordNum;
		   line++;
	   }
	   br.close();
   }
   //计算空行
   public void blankLine(File file) throws IOException {
	   BufferedReader br=new BufferedReader(new FileReader(file));
	   String curLine=null;
	   while((curLine=br.readLine())!=null) {
	      if("".equals(curLine.trim())||"}".equals(curLine.trim())) {
	    	  blankLine++;
	      }
	   }
	   br.close();
   }
   //计算代码行
   public void codeLine(File file)throws IOException{
	   BufferedReader br=new BufferedReader(new FileReader(file));
	   Pattern codeLinePattern=Pattern.compile("^[a-zA-Z].+");
	   String curLine=null;
	   while((curLine=br.readLine())!=null) {
		   Matcher m=codeLinePattern.matcher(curLine.trim());
		   if(m.matches()) {
			   codeLine++;
		   }
	   }
	   br.close();
   }
   //计算注释行
   public void commentLine(File file)throws IOException {
	   BufferedReader br=new BufferedReader(new FileReader(file));
	   Pattern commentLinePattern=Pattern.compile(".*//.*");
	   Pattern commentPatternBegin=Pattern.compile("\\s*/\\*.*");//注释块头判断
	   Pattern commentPatternEnd=Pattern.compile(".*\\*/\\s*");//注释块尾部判断
	   String curLine=null;
	   while((curLine=br.readLine())!=null) {
		   Matcher m=commentLinePattern.matcher(curLine);
		   if(m.matches()) 
			  commentLine++;
		   if(commentPatternBegin.matcher(curLine).matches()) {
			   commentLine++;
			   do {
				  curLine=br.readLine();
				  ++commentLine;
			   }while(!commentPatternEnd.matcher(curLine).matches());
		   }
	   }
	   br.close();
   }
   //递归处理目录下符合条件的文件
   public void Recurve(File Directory) throws IOException {
	   File[] files=Directory.listFiles();
	   if(files!=null&&files.length>0) {
	      for(File f:files) {
		     if(f.isDirectory()) {
			    Recurve(f);
		     }else {
		    	 wcUtil w=new wcUtil();
		    	 w.basicFunction(f);
		    	 w.blankLine(f);
		    	 w.codeLine(f);
		    	 w.commentLine(f);
		    	 System.out.println("文件名为："+f.getName()+" 的字符数："+w.charNum+" "+"单词数："+w.wordNum+" "+
		    	 "行数："+w.line+" "+"空行："+w.blankLine+" "+"代码行："+w.codeLine+" "+"注释行："+w.commentLine );
		     }
			   
	      }
	   }
   }
} 