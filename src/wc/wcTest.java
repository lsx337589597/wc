package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class wcTest {
	
     public  void Test() throws IOException {
	 Boolean s = true;
     while(s) {
    	System.out.println("请输入命令：");
    	//输入命令
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String input=br.readLine();
    	if("-q".equals(input)) {
    		s=false;
    		System.out.println("已退出。。");
    		break;
    	}
        String[] commond=input.split(" ");
        File file=new File(commond[1]);
        wcUtil wc=new wcUtil();
        if(file.isFile()) {//传进直接文件
            wc.basicFunction(file);
            wc.blankLine(file);
            wc.codeLine(file);
            wc.commentLine(file);
            switch(commond[0]) {
            case "-all":
            	System.out.println("字符数："+wc.charNum);
            	System.out.println("单词数："+wc.wordNum);
            	System.out.println("行数："+wc.line);
            	System.out.println("空行："+wc.blankLine+" "+"代码行："+wc.codeLine+" "+"注释行："+wc.commentLine);
                break;
            case "-c": 
                System.out.println("字符数："+wc.charNum);
                break;
            case "-w":
                System.out.println("单词数："+wc.wordNum);
                break;
            case "-l":
            	System.out.println("行数："+wc.line);
            	break;
            case "-a":
            	System.out.println("空行："+wc.blankLine+" "+"代码行："+wc.codeLine+" "+"注释行："+wc.commentLine);
            	break;
            default:
                System.out.println("命令不正确");
                break;
            }
        }else if(file.isDirectory()){//传进目录
        	switch(commond[0]) {
            case "-s-all":
                wc.Recurve(file);
                break;
            default:
            	System.out.println("命令不正确！");
            	break;
        	}
        }
        else {
        	System.out.println("文件名错误");
        }
        }
    }
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	   wcTest t=new wcTest();
	   t.Test();
    
    
	}

}
