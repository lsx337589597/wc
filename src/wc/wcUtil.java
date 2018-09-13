package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * C:\Users\Administrator\Desktop\test.txt
 * wc.exe ��һ�������Ĺ��ߣ�����ͳ���ı��ļ����ַ������������������������ĿҪ��дһ�������г���ģ������
 * wc.exe �Ĺ��ܣ����������䣬����ĳ�����������Դ�ļ����ַ�������������������
ʵ��һ��ͳ�Ƴ���������ȷͳ�Ƴ����ļ��е��ַ��������������������Լ����߱�������չ���ܣ����ܹ����ٵش������ļ���
���幦��Ҫ��
�������û������ģʽΪ��
wc.exe [parameter] [file_name]
 
 
���������б�
wc.exe -c file.c    //�����ļ� file.c ���ַ���
wc.exe -w file.c    //�����ļ� file.c �Ĵʵ���Ŀ  
wc.exe -l file.c    //�����ļ� file.c ������

��չ���ܣ�
    -s   �ݹ鴦��Ŀ¼�·����������ļ���
    -a   ���ظ����ӵ����ݣ������� / ���� / ע���У���
���У�����ȫ���ǿո���ʽ�����ַ�������������룬��ֻ�в�����һ������ʾ���ַ������硰{����
�����У����а�������һ���ַ��Ĵ��롣
ע���У����в��Ǵ����У����ұ��а���ע�͡�һ����Ȥ����������Щ����Ա���ڵ��ַ������ע�ͣ�
    } //ע��
����������£���һ������ע���С�
[file_name]: �ļ���Ŀ¼�������Դ���һ��ͨ�����


 *
 */
public class wcUtil {
	   int charNum=0;//���ַ���
	   int wordNum=0;//������   
	   int line=0;//����
	   int blankLine=0;//������
	   int codeLine=0;//������
	   int commentLine=0;//ע����
   //ʵ�ֻ�������	   
   public void basicFunction(File file) throws IOException{
	  
	   int currentCharNum;//��ǰ���ַ���
	   int curStringLength=0;//��ǰ�зָ������ַ�������
	   int currentWordNum;//��ǰ�е�����
	   BufferedReader br=new BufferedReader(new FileReader(file));
	   String currentLine=null;	
	   while((currentLine=br.readLine())!=null) {
		   currentCharNum=0;
		   currentWordNum=0;
		   String tempCurLine = currentLine;
		   //�ѵ�ǰ��ȥ��ǰ����β���հ׺�ͨ���ո�ָ�Ϊ�ַ�������
		   String[] splitedString=currentLine.trim().split(" ");
		   //���������ÿ�������ַ����ĳ�������ӣ��õ������ַ���
		   for(int i=0;i<splitedString.length;i++) {
			   curStringLength=splitedString[i].length();
			   currentCharNum+=curStringLength;
		   }
		   //ÿ���ַ�����ӵõ�����
		   charNum+=currentCharNum;
		   //ͳ�Ƶ�����
		   //�ѵ�ǰ�з���ĸ���ַ��滻Ϊ�ո�
		   tempCurLine = tempCurLine.replaceAll("[^a-zA-Z]"," ");
		   //�Կո�ָ���ַ���Ϊ���ɸ��ַ�������
		   String[] splitedString2=tempCurLine.trim().split("\\s+");
		   //�ַ�������ĳ��ȼ�Ϊ���е�����
		   currentWordNum=splitedString2.length;
		   //���⵱ǰ�����ַ��������ַ�����Ϊ��ĸʱ�ַ��������޳���
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
   //�������
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
   //���������
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
   //����ע����
   public void commentLine(File file)throws IOException {
	   BufferedReader br=new BufferedReader(new FileReader(file));
	   Pattern commentLinePattern=Pattern.compile(".*//.*");
	   Pattern commentPatternBegin=Pattern.compile("\\s*/\\*.*");//ע�Ϳ�ͷ�ж�
	   Pattern commentPatternEnd=Pattern.compile(".*\\*/\\s*");//ע�Ϳ�β���ж�
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
   //�ݹ鴦��Ŀ¼�·����������ļ�
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
		    	 System.out.println("�ļ���Ϊ��"+f.getName()+" ���ַ�����"+w.charNum+" "+"��������"+w.wordNum+" "+
		    	 "������"+w.line+" "+"���У�"+w.blankLine+" "+"�����У�"+w.codeLine+" "+"ע���У�"+w.commentLine );
		     }
			   
	      }
	   }
   }
} 