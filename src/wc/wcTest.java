package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class wcTest {
	
     public  void Test() throws IOException {
	 Boolean s = true;
     while(s) {
    	System.out.println("���������");
    	//��������
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String input=br.readLine();
    	if("-q".equals(input)) {
    		s=false;
    		System.out.println("���˳�����");
    		break;
    	}
        String[] commond=input.split(" ");
        File file=new File(commond[1]);
        wcUtil wc=new wcUtil();
        if(file.isFile()) {//����ֱ���ļ�
            wc.basicFunction(file);
            wc.blankLine(file);
            wc.codeLine(file);
            wc.commentLine(file);
            switch(commond[0]) {
            case "-all":
            	System.out.println("�ַ�����"+wc.charNum);
            	System.out.println("��������"+wc.wordNum);
            	System.out.println("������"+wc.line);
            	System.out.println("���У�"+wc.blankLine+" "+"�����У�"+wc.codeLine+" "+"ע���У�"+wc.commentLine);
                break;
            case "-c": 
                System.out.println("�ַ�����"+wc.charNum);
                break;
            case "-w":
                System.out.println("��������"+wc.wordNum);
                break;
            case "-l":
            	System.out.println("������"+wc.line);
            	break;
            case "-a":
            	System.out.println("���У�"+wc.blankLine+" "+"�����У�"+wc.codeLine+" "+"ע���У�"+wc.commentLine);
            	break;
            default:
                System.out.println("�����ȷ");
                break;
            }
        }else if(file.isDirectory()){//����Ŀ¼
        	switch(commond[0]) {
            case "-s-all":
                wc.Recurve(file);
                break;
            default:
            	System.out.println("�����ȷ��");
            	break;
        	}
        }
        else {
        	System.out.println("�ļ�������");
        }
        }
    }
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	   wcTest t=new wcTest();
	   t.Test();
    
    
	}

}
