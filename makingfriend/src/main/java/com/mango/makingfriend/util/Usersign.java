package com.mango.makingfriend.util;

import com.tls.tls_sigature.tls_sigature;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.DataFormatException;

@Component
public class Usersign {


	public  String getUsersig(String userName) {

		String priKey=readKey("/jni/ec_key.pem");

       // String pubStr=readKey("/jni/public.pem");

		tls_sigature.GenTLSSignatureResult result=	tls_sigature.genSig(1400007211,userName,priKey);

		//int ret = sigcheck.tls_gen_signature_ex("2592000", "1400007211", "1400007211", userName, "3316", priKey);

          return 	result.urlSig;

	}

    /**
     *
     * @param userSign 用户签名
     * @param userName 用户名
     * @param path 公私文件路径
     * @return boolean
     */
	public  boolean checkSign(String userSign, String userName, String path){

        String pubStr=readKey("/jni/public.pem");
        boolean flag=false;
        try {
            tls_sigature.CheckTLSSignatureResult   checkResult = tls_sigature.CheckTLSSignatureEx(userSign, 1400007211, userName, pubStr);

            flag=checkResult.verifyResult;
        } catch (DataFormatException e) {
            e.printStackTrace();
        }

        return flag;
    }






	/***
	 *读取公私key
	 * @param path 文件路径
	 * @return String
	 */
	private  String readKey(String path) {

		InputStream inputStream = this.getClass().getResourceAsStream(path);
		StringBuilder strBuilder = new StringBuilder();

		String s = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

			while ((s = br.readLine()) != null) {
				strBuilder.append(s + '\n');
			}

			br.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return  strBuilder.toString();


	}



	/*public static void main(String[] args) {
		
		

		 // F:\NewAndroid6\MakingFriend\src\jni
	        // 使用前请修改动态库的加载路径
	        demo.loadJniLib("F:\\NewAndroid6\\MakingFriend\\src\\jni\\jnisigcheck.dll");
	        
	        File priKeyFile = new File("F:\\NewAndroid6\\MakingFriend\\src\\ec_key.pem");
	        StringBuilder strBuilder = new StringBuilder();
	        String s = "";
	        try {  
	        BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
	 
				while ((s = br.readLine()) != null) {
				    strBuilder.append(s + '\n');
				}
		
	        br.close();
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String priKey = strBuilder.toString();        
	        int ret = demo.tls_gen_signature_ex("2592000", "1400007211", "1400007211", "22222222222", "3316", priKey);
	        
	        if (0 != ret) {
	            System.out.println("ret " + ret + " " + demo.getErrMsg());
	        }
	        else
	        {
	            System.out.println("sig:\n" + demo.getSig());
	        }        
		// TODO Auto-generated method stub

	}*/

}
