package com.shoot.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class XRandom {
	/**
	 * 随机生成n位数字
	 *
	 * @param n
	 * @return
	 */
	public static String randomNum(int n)
	{
		String res = "";
		Random rnd = new Random();
		for (int i = 0; i < n; i++)
		{
			res = res + rnd.nextInt(10);
		}
		return res;
	}

	/**
	 *
	 * @return
	 */
	public static  String randomPhone()
	{
		/** 前三为 */
		String head[] = { "+8613", "+8615", "+8617", "+8618", "+8616" };
		Random rnd = new Random();
		String res = head[rnd.nextInt(head.length)];
		for (int i = 0; i < 9; i++)
		{
			res = res + rnd.nextInt(10);
		}
		return res;
	}

	public static String randomMac()
	{
		String chars = "abcde0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < 17; i++)
		{
			if (i % 3 == 2)
			{
				res = res + ":";
			} else
			{
				res = res + chars.charAt(rnd.nextInt(leng));
			}

		}
		return res;
	}

	public static String randomMac1()
	{
		String chars = "ABCDE0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < 17; i++)
		{
			if (i % 3 == 2)
			{
				res = res + ":";
			} else
			{
				res = res + chars.charAt(rnd.nextInt(leng));
			}

		}
		return res;
	}

	public static int getRandom(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	public static String randomABC(int n)
	{
		String chars = "abcdef0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < n; i++)
		{
			res = res + chars.charAt(rnd.nextInt(leng));

		}
		return res;
	}

	public static String randomAlphabet(int n)
	{
		String chars = "abcdefghijklmnkoqrspuvwsyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < n; i++)
		{
			res = res + chars.charAt(rnd.nextInt(leng));

		}
		return res;
	}

	/**
	 * 获取CPU型号
	 * @return
	 */
	public static String getCpuName(){

		String str1 = "/proc/cpuinfo";
		String str2 = "";

		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr);
			while ((str2=localBufferedReader.readLine()) != null) {
				if (str2.contains("Hardware")) {
					return str2.split(":")[1];
				}
			}
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return null;

	}
	public static String execShell(String cmd){
		try{
			//权限设置
			Process p = Runtime.getRuntime().exec("su");  //开始执行shell脚本
			//获取输出流
			OutputStream outputStream = p.getOutputStream();
			DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
			//将命令写入
			dataOutputStream.writeBytes(cmd);
			//提交命令
			dataOutputStream.flush();
			//关闭流操作
			dataOutputStream.close();
			outputStream.close();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return cmd;
	}
}
