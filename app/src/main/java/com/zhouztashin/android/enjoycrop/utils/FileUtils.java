

package com.zhouztashin.android.enjoycrop.utils;

import android.content.Context;
import android.text.TextUtils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 文件相关处理工具类
 *
 * <ul>
 *     文件名或路径相关
 *     <li>{@link #getExtName(String)} 获取文件扩展名，带.</li>
 *     <li>{@link #getFileNameWithExt(String)} 解析文件全名,带扩展名</li>
 *     <li>{@link #getFileNameWithoutExt(String)} 获取文件名，不带扩展名</li>
 *     <li>{@link #getParentPath(String)} 获取父目录路径</li>
 *     <li>{@link #getFileFolderPath(String)} 解析文件所在的文件夹</li>
 *     <li>{@link #getUnSameFileNameOfSize(String, String, int)} 获取不重复的文件名</li>
 *     <li>{@link #getUnSameFileNameOfNum(String, String)} 获取不重复的文件名</li>
 * </ul>
 * <ul>
 *     文件读取相关
 *     <li>{@link #loadPropertiesFromAsset(android.content.Context, String)} 从assset资源中读取Properties</li>
 *     <li>{@link #loadPropertiesFromRaw(android.content.Context, int)} 从raw资源中读取Properties</li>
 *     <li>{@link #getFileInputStream(String)} 获取文件的输入流</li>
 *     <li>{@link #getFileOutputStream(String)} 获取文件的输出流</li>
 *     <li>{@link #getFileData(String)} 获取文件的数据</li>
 *     <li>{@link #getFileSize(String)} 获取文件大小</li>
 * </ul>
 * <ul>
 *     文件处理相关
 *     <li>{@link #rewriteData(String, byte[])} 重写文件的数据</li>
 *     <li>{@link #rewriteData(String, java.io.InputStream)} 重写文件的数据</li>
 *     <li>{@link #appendData(String, byte[])} 向文件的末尾添加数据</li>
 *     <li>{@link #appendData(String, java.io.InputStream)} 向文件末尾添加数据</li>
 *     <li>{@link #deleteFile(String)} 删除文件或文件夹(包括目录下的文件)</li>
 *     <li>{@link #deleteFile(String, boolean)} 删除文件</li>
 *     <li>{@link #createFile(String, int)} 创建一个空的文件(创建文件的模式，已经存在的是否要覆盖)</li>
 *     <li>{@link #createFolder(String, int)} 创建一个空的文件夹(创建文件夹的模式，已经存在的是否要覆盖)</li>
 *     <li>{@link #isExist(String)} 判断文件或文件夹是否存在</li>
 *     <li>{@link #rename(String, String)}重命名文件/文件夹</li>
 *     <li>{@link #listFiles(String)} 列出目录文件</li>
 *     <li>{@link #moveFile(String, String)} 移动文件</li>
 *     <li>{@link #writeData(String, byte[])} 写入新文件</li>
 *     <li>{@link #isErrorFile(String)} 是否是下载出错文件（下到错误页面的数据）</li>
 *     <li>{@link #hasfile(String)} 某个文件夹下是否文件</li>
 * </ul>
 * <ul>
 *  音频文件操作相关
 *     <li>{@link #getAudioMimeType(String)} 获取音频文件的mimeType</li>
 *     <li>{@link #isM4A(String)} 是否是m4a文件</li>
 *     <li>{@link #readMp3HashFromM4a(String)} 从m4a读取mp3哈希值</li>
 * </ul>
 */
public class FileUtils {

    private FileUtils() {}
	/**
	 * <p>获取文件扩展名，带.</p>
	 * 如path=/sdcard/image.jpg --> .jpg
     *
	 * @param fileName
	 *            文件名
	 * @return 扩展名
	 */
	public static String getExtName(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return null;
		}
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return fileName.substring(index, fileName.length());
		}
	}

	/**
	 * <p>解析文件全名,带扩展名</p>
	 * 如path=/sdcard/image.jpg --> image.jpg
     *
	 * @param filePath
	 *            文件路径
	 * @return 文件全名
	 */
	public static String getFileNameWithExt(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		int last = filePath.lastIndexOf("/");
		int end = filePath.length() - 1;
		if (last == -1) {
			return filePath;
		} else if (last < end) {
			return filePath.substring(last + 1);
		} else {
			return filePath.substring(last);
		}
	}

	/**
	 * <p>获取文件名，不带扩展名</p>
	 * 如path=/sdcard/image.jpg --> image
     *
	 * @param filePath
	 * @return
	 */
	public static String getFileNameWithoutExt(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		int last = filePath.lastIndexOf("/");
		int index = filePath.lastIndexOf(".");
		if (last == -1 && index == -1) {
			return filePath;
		} else if (index > last) {
			return filePath.substring(last + 1, index);
		} else {
			return filePath.substring(last);
		}
	}

	/**
	 * 获取父目录路径
	 *
	 * @param filePath
	 * @return
	 */
	public static String getParentPath(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		int last = filePath.lastIndexOf("/");
		if (last == -1) {
			return null;
		}
		return filePath.substring(0, last + 1);
	}

    /**
     * 解析文件所在的文件夹
     *
     * @param filePath
     *            文件路径
     * @return 文件所在的文件夹路径
     */
    public static String getFileFolderPath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int last = filePath.lastIndexOf("/");
        if (last == -1) {
            return null;
        }
        return filePath.substring(0, last + 1);
    }

	/**
	 * 从raw资源中读取Properties
	 *
	 * @param context
	 * @param rawId
	 * @return
	 */
	public static Properties loadPropertiesFromRaw(Context context, int rawId) {
		Properties result = null;
		if (context != null) {
			InputStream ins = null;
			try {
				ins = context.getResources().openRawResource(rawId);
				result = new Properties();
				result.load(ins);
			} catch (IOException e) {
				result = null;
				e.printStackTrace();
			} finally {
				try {
					if (ins != null) {
						ins.close();
					}
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	/**
	 * 从assset资源中读取Properties
	 *
	 * @param context
	 * @param assetPath
	 * @return
	 */
	public static Properties loadPropertiesFromAsset(Context context, String assetPath) {
		Properties result = null;
		if (context != null && !TextUtils.isEmpty(assetPath)) {
			InputStream ins = null;
			try {
				ins = context.getAssets().open(assetPath);
				result = new Properties();
				result.load(ins);
			} catch (IOException e) {
				result = null;
				e.printStackTrace();
			} finally {
				try {
					if (ins != null) {
						ins.close();
					}
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	/**
	 * 创建文件的模式，已经存在的文件要覆盖
	 */
	public final static int MODE_COVER = 1;

	/**
	 * 创建文件的模式，文件已经存在则不做其它事
	 */
	public final static int MODE_UNCOVER = 0;

	/**
	 * 获取文件的输入流
	 *
	 * @param path
	 * @return
	 */
	public static FileInputStream getFileInputStream(String path) {
		FileInputStream fis = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				fis = new FileInputStream(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}

	/**
	 * 获取文件的输出流
	 *
	 * @param path
	 * @return
	 */
	public static OutputStream getFileOutputStream(String path) {
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				fos = new FileOutputStream(file);
			}
		} catch (Exception e) {
			return null;
		}
		return fos;
	}

	/**
	 * 获取文件的数据
	 *
	 * @param path
	 * @return
	 */
	public static byte[] getFileData(String path) {
		byte[] data = null;// 返回的数据
		try {
			File file = new File(path);
			if (file.exists()) {
                data = IOUtils.toByteArray(new FileInputStream(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 重写文件的数据
	 *
	 * @param path
	 * @param data
	 */
	public static void rewriteData(String path, byte[] data) {
		try {
			File file = new File(path);
			if (file.exists()) {
				FileOutputStream fos = new FileOutputStream(file, false);
				fos.write(data);
				fos.flush();
				IOUtils.closeQuietly(fos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写文件的数据
	 *
	 * @param path
	 * @param is
	 */
	public static boolean rewriteData(String path, InputStream is) {
		try {
			File file = new File(path);
			if (file.exists()) {
				FileOutputStream fos = new FileOutputStream(file, false);
				byte[] data = new byte[4 * 1024];
				int receive = 0;
				while ((receive = is.read(data)) != -1) {
					fos.write(data, 0, receive);
					fos.flush();
				}
                IOUtils.closeQuietly(fos);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 向文件的末尾添加数据
	 *
	 * @param path
	 * @param data
	 */
	public static boolean appendData(String path, byte[] data) {
		try {
			File file = new File(path);
			if (file.exists()) {
				FileOutputStream fos = new FileOutputStream(file, true);
				fos.write(data);
				fos.flush();
				IOUtils.closeQuietly(fos);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 向文件末尾添加数据
	 *
	 * @param path
	 * @param is
	 */
	public static void appendData(String path, InputStream is) {
		try {
			File file = new File(path);
			if (file.exists()) {
				FileOutputStream fos = new FileOutputStream(file, true);
				byte[] data = new byte[1024];
				int receive = 0;
				while ((receive = is.read(data)) != -1) {
					fos.write(data, 0, receive);
					fos.flush();
				}
                IOUtils.closeQuietly(fos);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 删除文件或文件夹(包括目录下的文件)
	 *
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return;
		}
		try {
			File f = new File(filePath);
			if (f.exists() && f.isDirectory()) {
				File[] delFiles = f.listFiles();
				if (delFiles != null) {
					for (int i = 0; i < delFiles.length; i++) {
						deleteFile(delFiles[i].getAbsolutePath());
					}
				}
			}
			f.delete();
		} catch (Exception e) {

		}
	}

	/**
	 * 删除文件
	 *
	 * @param filePath
	 * @param deleteParent
	 *            是否删除父目录
	 */
	public static void deleteFile(String filePath, boolean deleteParent) {
		if (filePath == null) {
			return;
		}
		try {
			File f = new File(filePath);
			if (f.exists() && f.isDirectory()) {
				File[] delFiles = f.listFiles();
				if (delFiles != null) {
					for (int i = 0; i < delFiles.length; i++) {
						deleteFile(delFiles[i].getAbsolutePath(), deleteParent);
					}
				}
			}
			if (deleteParent) {
				f.delete();
			} else if (f.isFile()) {
				f.delete();
			}
		} catch (Exception e) {

		}
	}


    /**
     * 删除指定文件夹下所有文件(包括子文件夹)
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    /**
     * * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 创建一个空的文件(创建文件的模式，已经存在的是否要覆盖)
	 *
	 * @param path
	 * @param mode
	 */
	public static boolean createFile(String path, int mode) {
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		try {
			File file = new File(path);
			if (file.exists()) {
				if (mode == FileUtils.MODE_COVER) {
					file.delete();
					file.createNewFile();
				}
			} else {
				// 如果路径不存在，先创建路径
				File mFile = file.getParentFile();
				if (!mFile.exists()) {
					mFile.mkdirs();
				}
				file.createNewFile();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 创建一个空的文件夹(创建文件夹的模式，已经存在的是否要覆盖)
	 *
	 * @param path
	 * @param mode
	 */
	public static void createFolder(String path, int mode) {
		try {
			// LogUtil.debug(createShapePath);
			File file = new File(path);
			if (file.exists()) {
				if (mode == FileUtils.MODE_COVER) {
					file.delete();
					file.mkdirs();
				}
			} else {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件大小
	 *
	 * @param path
	 * @return
	 */
	public static long getFileSize(String path) {
		if (TextUtils.isEmpty(path)) {
			return 0;
		}
		long size = 0;
		try {
			File file = new File(path);
			if (file.exists()) {
				size = file.length();
			}
		} catch (Exception e) {
			return 0;
		}
		return size;
	}

	/**
	 * 判断文件或文件夹是否存在
	 *
	 * @param path
	 * @return true 文件存在
	 */
	public static boolean isExist(String path) {
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		boolean exist = false;
		try {
			File file = new File(path);
			exist = file.exists();
		} catch (Exception e) {
			return false;
		}
		return exist;
	}

	/**
	 * 重命名文件/文件夹
	 *
	 * @param path 原路径
	 * @param newName 新的路径
	 */
	public static boolean rename(final String path, final String newName) {
		boolean result = false;
		if (TextUtils.isEmpty(path) || TextUtils.isEmpty(newName)) {
			return result;
		}
		try {
			File file = new File(path);
			if (file.exists()) {
				result = file.renameTo(new File(newName));
			}
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * 列出目录文件
	 *
	 * @return
	 */
	public static File[] listFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isDirectory()) {
			return file.listFiles();
		}
		return null;
	}

	/**
	 * 获取不重复的文件名
	 *
	 * @param filename
	 * @return
	 */
	public static String getUnSameFileNameOfSize(String folder, String filename, int fileSize) {
		String tempFilename = folder + StringUtils.formatFilePath(filename);
		boolean isExist = isExist(tempFilename);
		if (isExist) {
			String title = filename.substring(0, filename.lastIndexOf("."));
			String ext = filename.substring(filename.lastIndexOf(".") + 1);
			title = title + "(" + StringUtils.getSizeText(fileSize) + ")";
			// tempFilename = getUnSameFileNameOfNum(folder, title, ext);
			tempFilename = folder + StringUtils.formatFilePath(title) + "." + ext;
		}
		return tempFilename;
	}

	/**
     * 获取音频文件的mimeType
	 * @param path
	 *            hash.ext.kgtmp
	 * @return
	 */
	public static String getAudioMimeType(String path) {
		boolean isM4A = path.toLowerCase().endsWith(".m4a");
		return isM4A ? "audio/mp4" : "audio/mpeg";
	}

	public static String getUnSameFileNameOfNum(String folder, String filename) {
		if (TextUtils.isEmpty(filename)) {
			return "";
		}
		if (filename.lastIndexOf(".") == -1) {
			return folder + StringUtils.formatFilePath(filename);
		}
		String title = filename.substring(0, filename.lastIndexOf("."));
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		int count = 0;
		String tempFilename = folder + StringUtils.formatFilePath(title) + "." + ext;
		boolean isExist = isExist(tempFilename);
		while (isExist) {
			tempFilename = folder + StringUtils.formatFilePath(title + "(" + (++count) + ")") + "." + ext;
			isExist = isExist(tempFilename);
		}
		return tempFilename;
	}

	/**
	 * 是否是m4a文件
	 *
	 * @param m4a
	 *            m4a文件路径
	 * @return
	 */
	public static boolean isM4A(final String m4a) {
		if (TextUtils.isEmpty(m4a)) {
			return false;
		}
		try {
			FileInputStream stream = new FileInputStream(new File(m4a));
			byte[] buffer = new byte[8];
			if (stream.read(buffer) == 8) {
                IOUtils.closeQuietly(stream);
				return (buffer[4] == 'f' && buffer[5] == 't' && buffer[6] == 'y' && buffer[7] == 'p');
			} else {
                IOUtils.closeQuietly(stream);
				return false;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	private static final String TAG_KGMP3HASH = "kgmp3hash";

	private static final int TAG_KGMP3HASH_LENGTH = TAG_KGMP3HASH.length() + 32;

	/**
	 * 从m4a读取mp3哈希值
	 *
	 * @param m4a
	 *            m4a文件路径
	 * @return
	 */
	public static String readMp3HashFromM4a(final String m4a) {
		if (TextUtils.isEmpty(m4a)) {
			return null;
		}
		File m4afile = new File(m4a);
		RandomAccessFile accessFile = null;
		try {
			accessFile = new RandomAccessFile(m4afile, "r");
			accessFile.skipBytes((int) (m4afile.length() - TAG_KGMP3HASH_LENGTH));
			byte[] b = new byte[TAG_KGMP3HASH_LENGTH];
			if (accessFile.read(b) == TAG_KGMP3HASH_LENGTH) {
				String taghash = new String(b);
				if (!TextUtils.isEmpty(taghash) && taghash.startsWith(TAG_KGMP3HASH)) {
					return taghash.substring(TAG_KGMP3HASH.length());
				}
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			if (accessFile != null) {
				try {
					accessFile.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 移动文件
	 *
	 * @param oldFilePath
	 *            旧路径
	 * @param newFilePath
	 *            新路径
	 * @return
	 */
	public static boolean moveFile(String oldFilePath, String newFilePath) {
		if (TextUtils.isEmpty(oldFilePath) || TextUtils.isEmpty(newFilePath)) {
			return false;
		}
		File oldFile = new File(oldFilePath);
		if (oldFile.isDirectory() || !oldFile.exists()) {
			return false;
		}
		try {
			File newFile = new File(newFilePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(oldFile));
			FileOutputStream fos = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int read;
			while ((read = bis.read(buf)) != -1) {
				fos.write(buf, 0, read);
			}
			fos.flush();
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(bis);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 是否是下载出错文件（下到错误页面的数据）
	 *
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public static boolean isErrorFile(final String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return false;
		}
		try {
			FileInputStream stream = new FileInputStream(new File(filePath));
			byte[] buffer = new byte[16];
			if (stream.read(buffer) == 16) {
				IOUtils.closeQuietly(stream);
				return ((buffer[0] & 0xFF) == 0xFF && (buffer[1] & 0xFF) == 0xD8 && (buffer[2] & 0xFF) == 0xFF && (buffer[3] & 0xFF) == 0xE0 && (buffer[4] & 0xFF) == 0x00
						&& (buffer[5] & 0xFF) == 0x10 && (buffer[6] & 0xFF) == 0x4A && (buffer[7] & 0xFF) == 0x46 && (buffer[8] & 0xFF) == 0x49 && (buffer[9] & 0xFF) == 0x46
						&& (buffer[10] & 0xFF) == 0x00 && (buffer[11] & 0xFF) == 0x01 && (buffer[12] & 0xFF) == 0x02 && (buffer[13] & 0xFF) == 0x01 && (buffer[14] & 0xFF) == 0x00 && (buffer[15] & 0xFF) == 0x48);
			} else {
                IOUtils.closeQuietly(stream);
				return false;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 某个文件夹下是否文件
     * @param filepath 文件路径
	 */
	public static boolean hasfile(String filepath) {
		boolean returnValue = false;
		File file = new File(filepath);
		if (!file.exists()) {
			returnValue = false;
		} else if (!file.isDirectory()) {
			returnValue = true;
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					returnValue = true;
					break;
				} else if (readfile.isDirectory()) {
					hasfile(filepath + "\\" + filelist[i]);
				}
			}
		}
		return returnValue;
	}

	/**
	 * 写入文件,如果文件已经存在,则覆盖之.
     * @param path 文件保存路径
     * @param data 文件保存的数据
	 * @return 是否写入成功
	 */
	public static boolean writeData(String path, byte[] data) {
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			IOUtils.closeQuietly(fos);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

    /**
     * 解压路径为zipFile的.zip文件到路径folderPath下， 如果有同名folderPath的文件，则删除该文件，新建为文件夹
     * 解压过程使用buffer大小为5k
     *
     * @param zipFilePath
     * @param folderPath
     * @return
     * @throws java.io.IOException
     */
    public static boolean unZipFile(String zipFilePath, String folderPath) {
        // new ZipInputStream(null);
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        ZipFile zfile = null;
        OutputStream os = null;
        ZipInputStream is = null;
        try {
            File folder = new File(folderPath);
            if (folder.exists() && !folder.isDirectory()) {
                folder.delete();
            }
            if (!folder.exists()) {
                folder.mkdirs();
            }

            is = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry zipEntry;
            while ((zipEntry = is.getNextEntry()) != null) {
                String subfilename = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    File subDire = new File(folderPath + subfilename);
                    if (subDire.exists() && subDire.isDirectory()) {
                        continue;
                    } else if (subDire.exists() && subDire.isFile()) {
                        subDire.delete();
                    }
                    subDire.mkdirs();
                } else {
                    File subFile = new File(folderPath + subfilename);
                    if (subFile.exists()) {
                        continue;
                    }
                    subFile.createNewFile();
                    os = new FileOutputStream(subFile);
                    int len;
                    byte[] buffer = new byte[5120];
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                        os.flush();
                    }
                }
            }

        } catch (Exception e) {
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (zfile != null) {
                try {
                    zfile.close();
                } catch (IOException e) {
                }
            }

        }
        return true;
    }
}
