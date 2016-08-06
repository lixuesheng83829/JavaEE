package cn.itcast.oa.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

//定义一个工具类，用于产生验证码图片
public class SecurityCode {
	public static final int WIDTH = 100;
	public static final int HEIGHT = 25;
	public static String checkstring;

	// 得到验证码的BtyArrayInputStream流
	public static ByteArrayInputStream getImageAsInputStream() {
		BufferedImage image = getRandomImage();
		return convertImageToStream(image);
	}

	// ------------(主体流程)得到验证码图片---------
	public static BufferedImage getRandomImage() {
		BufferedImage randomImage = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);// 图片缓存
		Graphics g = randomImage.getGraphics();//获取绘图对象(图片上下文?)
		setBackground(g);
		setBorder(g);
		paintLine(g);
		checkstring = setRandomString((Graphics2D) g);//
		g.dispose();// 释放画笔资源,清空缓存输出图片
		return randomImage;
	}

	//给定颜色范围(200-255)内获得随机颜色
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	// 设置背景颜色
	private static void setBackground(Graphics g) {
		//g.setColor(Color.WHITE);
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	// 设置边框
	private static void setBorder(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
	}

	// 画干扰线
	private static void paintLine(Graphics g) {
		g.setColor(Color.GREEN);
		for (int i = 0; i < 5; i++) {
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	// 生成4位随机数,返回随机数字符串
	private static String setRandomString(Graphics2D g) {
		StringBuilder sb = new StringBuilder();
		g.setFont(new Font("宋体", Font.BOLD, 22));
		String randomString = "abcdefghijkmnpqrstuvwsyz23456789";
		for (int i = 0; i < 4; i++) {
			g.setColor(Color.red);
			int x = 20 * i + 10;
			String str = randomString.charAt(new Random().nextInt(randomString
					.length()))
					+ "";
			sb.append(str);
			int roll = new Random().nextInt() % 30;
			g.rotate(roll * Math.PI / 180, x, 20);// 旋转画布,3个参数:弧度,指定旋转的中心点
			g.drawString(str, x, 20);
			g.rotate(-roll * Math.PI / 180, x, 20);// 画布旋转还原
		}
		return sb.toString();
	}

	// 将image验证码转化成字节数组输入流
	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
		ByteArrayInputStream inputStream = null;
		//构造字节输出流生成图片,从内存中输出
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageOutputStream imageOut  = ImageIO.createImageOutputStream(bos);
			//将内存中存放的缓存图片以JPEG格式写出到输出流中
			ImageIO.write(image,"JPEG",imageOut);
			imageOut.close();
			byte[] bts = bos.toByteArray();
			//构建输入流接收输出图片,由Struts负责输出,
			//如果使用Servlet已经可以在ImageIO.write(image,"JPEG",response.getOutputStream())完成输出
			inputStream = new ByteArrayInputStream(bts);
		} catch (IOException e) {
			throw new RuntimeException("验证码出错:"+e.toString());
		}
		return inputStream;
	}

}
