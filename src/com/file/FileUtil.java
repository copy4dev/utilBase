package com.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FileUtil {

	// --- 基础操作准备 ---

	/**
	 * 获取当前系统类型和Tomcat的webapps目录
	 */
	public static void getJavaWebappsdir() {

		// 获取当前系统类型
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			System.out.println(os);
		} else {
			System.out.println(os);
		}
		// 获取Tomcat的webapps目录
		String p = System.getenv("TOMCAT_HOME") + File.separator + "webapps";
		System.out.println(p);
	}

	/**
	 * 递归调用查找指定文件夹下所有文件<br/>
	 * the size of resultList always greater than 1 (大于1)
	 * @param dirPath 文件夹的绝对路径 eg:"F:"+File.separator+"books"
	 * @param resultList 返回的结果集
	 */
	public static void getFilePath(String dirPath, List<String> resultList) {
		File rootDir = new File(dirPath);
		if (!rootDir.isDirectory()) {
//			System.out.println("文件名" + rootDir.getAbsolutePath());
			resultList.add(rootDir.getAbsolutePath());
		} else {
			String[] fileList = rootDir.list();
			for (int i = 0; i < fileList.length; i++) {
				dirPath = rootDir.getAbsolutePath() + File.separator + fileList[i];
				getFilePath(dirPath, resultList);
			}
		}
	}

	// --- delete ---

	/**
	 * 
	 * 删除文件，可以删除单个文件或文件夹
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否是返回false
	 */
	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return true;
		} else {
			if (file.isFile()) {
				if ("1".equals(FileUtil.deleteFile(fileName)[0]))
					return true;
			} else {
				if ("1".equals(FileUtil.deleteDirectory(fileName)[0]))
					return true;
			}
			return false;
		}
	}

	/**
	 * 
	 * 删除单个文件
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否则返回false
	 */
	public static String[] deleteFile(String fileName) {
		String[] result = new String[2];
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				result[0] = "1";
				result[1] = "删除文件 " + fileName + " 成功!";
				return result;
			} else {
				result[0] = "0";
				result[1] = "删除文件 " + fileName + " 失败!";
				return result;
			}
		} else {
			result[0] = "1";
			result[1] = fileName + " 文件不存在!";
			return result;
		}
	}

	/**
	 * 
	 * 删除目录及目录下的文件
	 * 
	 * @param dirName 被删除的目录所在的文件路径
	 * @return 如果目录删除成功，则返回true，否则返回false
	 */
	public static String[] deleteDirectory(String dirName) {
		String[] result = new String[2];
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			result[0] = "1";
			result[1] = dirNames + " 目录不存在!";
			return result;
		}
		String flag = "1";
		// 列出全部文件及子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileUtil.deleteFile(files[i].getAbsolutePath())[0];
				// 如果删除文件失败，则退出循环
				if ("0".equals(flag)) {
					break;
				}
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileUtil.deleteDirectory(files[i].getAbsolutePath())[0];
				// 如果删除子目录失败，则退出循环
				if ("0".equals(flag)) {
					break;
				}
			}
		}

		if ("0".equals(flag)) {
			result[0] = "0";
			result[1] = "删除目录失败!";
			return result;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			result[0] = "1";
			result[1] = "删除目录 " + dirName + " 成功!";
			return result;
		} else {
			result[0] = "0";
			result[1] = "删除目录 " + dirName + " 失败!";
			return result;
		}

	}

	// --- copy ---

	/**
	 * 复制单个文件，如果目标文件存在，则不覆盖
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static String[] copyFile(String srcFileName, String descFileName) {
		return FileUtil.copyFileCover(srcFileName, descFileName, false);
	}

	/**
	 * 复制单个文件
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @param coverlay 如果目标文件已存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static String[] copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
		String[] result = new String[2];
		File srcFile = new File(srcFileName);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			result[0] = "0";
			result[1] = "复制文件失败，源文件 " + srcFileName + " 不存在!";
			return result;
		}
		// 判断源文件是否是合法的文件
		else if (!srcFile.isFile()) {
			result[0] = "0";
			result[1] = "复制文件失败，" + srcFileName + " 不是一个文件!";
			return result;
		}
		File descFile = new File(descFileName);
		// 判断目标文件是否存在
		if (descFile.exists()) {
			// 如果目标文件存在，并且允许覆盖
			if (coverlay) {
				if (!FileUtil.delFile(descFileName)) {
					result[0] = "0";
					result[1] = "删除目标文件 " + descFileName + " 失败!";
					return result;
				}
			} else {
				result[0] = "0";
				result[1] = "复制文件失败，目标文件 " + descFileName + " 已存在!";
				return result;
			}
		} else {
			if (!descFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				if (!descFile.getParentFile().mkdirs()) {
					result[0] = "0";
					result[1] = "创建目标文件所在的目录失败!";
					return result;
				}
			}
		}

		// 准备复制文件
		// 读取的位数
		int readByte = 0;
		InputStream ins = null;
		OutputStream outs = null;
		try {
			// 打开源文件
			ins = new FileInputStream(srcFile);
			// 打开目标文件的输出流
			outs = new FileOutputStream(descFile);
			byte[] buf = new byte[1024];
			// 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
			while ((readByte = ins.read(buf)) != -1) {
				// 将读取的字节流写入到输出流
				outs.write(buf, 0, readByte);
			}
			result[0] = "1";
			result[1] = "复制单个文件 " + srcFileName + " 到" + descFileName + "成功!";
			return result;
		} catch (Exception e) {
			result[0] = "0";
			result[1] = "复制文件失败：" + e.getMessage();
			return result;
		} finally {
			// 关闭输入输出流，首先关闭输出流，然后再关闭输入流
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException oute) {
					oute.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ine) {
					ine.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制整个目录的内容，如果目标目录存在，则不覆盖
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录名
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static String[] copyDirectory(String srcDirName, String descDirName) {
		return FileUtil.copyDirectoryCover(srcDirName, descDirName, false);
	}

	/**
	 * 复制整个目录的内容
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录名
	 * @param coverlay 如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static String[] copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) {
		String[] result = new String[2];
		File srcDir = new File(srcDirName);
		// 判断源目录是否存在
		if (!srcDir.exists()) {
			result[0] = "0";
			result[1] = "复制目录失败，源目录 " + srcDirName + " 不存在!";
			return result;
		}
		// 判断源目录是否是目录
		else if (!srcDir.isDirectory()) {
			result[0] = "0";
			result[1] = "复制目录失败，" + srcDirName + " 不是一个目录!";
			return result;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		// 如果目标文件夹存在
		if (descDir.exists()) {
			if (coverlay) {
				// 允许覆盖目标目录
				if (!FileUtil.delFile(descDirNames)) {
					result[0] = "0";
					result[1] = "删除目录 " + descDirNames + " 失败!";
					return result;
				}
			} else {
				result[0] = "0";
				result[1] = "目标目录复制失败，目标目录 " + descDirNames + " 已存在!";
				return result;
			}
		} else {
			// 创建目标目录
			if (!descDir.mkdirs()) {
				result[0] = "0";
				result[1] = "创建目标目录失败!";
				return result;
			}
		}

		String flag = "1";
		// 列出源目录下的所有文件名和子目录名
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 如果是一个单个文件，则直接复制
			if (files[i].isFile()) {
				flag = FileUtil.copyFile(files[i].getAbsolutePath(), descDirName + files[i].getName())[0];
				// 如果拷贝文件失败，则退出循环
				if ("0".equals(flag)) {
					break;
				}
			}
			// 如果是子目录，则继续复制目录
			if (files[i].isDirectory()) {
				flag = FileUtil.copyDirectory(files[i].getAbsolutePath(), descDirName + files[i].getName())[0];
				// 如果拷贝目录失败，则退出循环
				if ("0".equals(flag)) {
					break;
				}
			}
		}

		if ("0".equals(flag)) {
			result[0] = "0";
			result[1] = "复制目录 " + srcDirName + " 到 " + descDirName + " 失败!";
			return result;
		}
		result[0] = "1";
		result[1] = "复制目录 " + srcDirName + " 到 " + descDirName + " 成功!";
		return result;
	}

	// --- create ---

	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static String[] createFile(String descFileName) {
		String[] result = new String[2];
		File file = new File(descFileName);
		if (file.exists()) {
			result[0] = "0";
			result[1] = "文件 " + descFileName + " 已存在!";
			return result;
		}
		if (descFileName.endsWith(File.separator)) {
			result[0] = "0";
			result[1] = descFileName + " 为目录，不能创建目录!";
			return result;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				result[0] = "0";
				result[1] = "创建文件所在的目录失败!";
				return result;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				result[0] = "1";
				result[1] = descFileName + " 文件创建成功!";
				return result;
			} else {
				result[0] = "0";
				result[1] = descFileName + " 文件创建失败!";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result[0] = "0";
			result[1] = descFileName + " 文件创建失败!";
			return result;
		}

	}

	/**
	 * 创建目录
	 * @param descDirName 目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static String[] createDirectory(String descDirName) {
		String[] result=new String[2];
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			result[0] = "0";
			result[1] = "目录 " + descDirNames + " 已存在!";
			return result;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			result[0] = "1";
			result[1] ="目录 " + descDirNames + " 创建成功!";
			return result;
		} else {
			result[0] = "0";
			result[1] = "目录 " + descDirNames + " 创建失败!";
			return result;
		}

	}

	/**
	 * 写入文件
	 * @param file 要写入的文件
	 */
	public static void writeToFile(String fileName, String content, boolean append) {
		try {
			FileUtil.write(new File(fileName), content, "utf-8", append);
			logger.debug("文件 " + fileName + " 写入成功!");
		} catch (IOException e) {
			logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
		}
	}

	/**
	 * 写入文件
	 * @param file 要写入的文件
	 */
	public static void writeToFile(String fileName, String content, String encoding, boolean append) {
		try {
			FileUtil.write(new File(fileName), content, encoding, append);
			logger.debug("文件 " + fileName + " 写入成功!");
		} catch (IOException e) {
			logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
		}
	}

}
