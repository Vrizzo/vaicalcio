/*
 * FileUtil.java
 *
 * Created on December 15, 2005, 2:56 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package it.newmedia.io;

import it.newmedia.results.Result;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.PropertyResourceBundle;

/**
 *
 * @author lmor
 */
public class FileUtil
{

  /**
   * String to identifie xml file by exstension (.xml)
   */
  public static final String FILEEXTDOT_XML = ".xml";
  public static final String FILE_ENCODING_UTF_8 = "UTF-8";
  public static final String FILE_ENCODING_ISO_8859_1 = "ISO-8859-1";

  /**
   * Check if file may be an xml file.
   * Attention... checks only name!
   * @param file File to check
   * Il file is null throw exception
   * @return True if file name ends with .xml, otherwise false
   */
  public static boolean isXmlFile(File file)
  {
    return file.getPath().toLowerCase().endsWith(FILEEXTDOT_XML);
  }

  public static Result<Boolean> writeFile(String input, String pathFile)
  {
    return writeFile(input, pathFile, null, false);
  }

  public static Result<Boolean> writeFile(String input, String pathFile, String charset)
  {
    return writeFile(input, pathFile, charset, false);
  }

  public static Result<Boolean> writeFile(String input, String pathFile, boolean append)
  {
    return writeFile(input, pathFile, null, append);
  }

  public static Result<Boolean> writeFile(String input, String pathFile, String charset, boolean append)
  {
    OutputStreamWriter outputStreamWriter = null;
    try
    {
      char[] buffer;

      buffer = input.toCharArray();

      if (charset != null && charset.length() > 0)
      {
        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(pathFile, append), charset);
      }
      else
      {
        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(pathFile, append));
      }

      outputStreamWriter.write(buffer);
      outputStreamWriter.close();

      return new Result<Boolean>(true, true);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
    finally
    {
      if (outputStreamWriter != null)
        try
        {
          outputStreamWriter.close();
        }
        catch (Exception ex2)
        {
        }
    }
  }

  public static Result<Boolean> writeFile(InputStream inputStream, String pathFile)
  {
    return writeFile(inputStream, pathFile, null, false);
  }

  public static Result<Boolean> writeFile(InputStream inputStream, String pathFile, String charset)
  {
    return writeFile(inputStream, pathFile, charset, false);
  }

  public static Result<Boolean> writeFile(InputStream inputStream, String pathFile, boolean append)
  {
    return writeFile(inputStream, pathFile, null, append);
  }

  public static Result<Boolean> writeFile(InputStream inputStream, String pathFile, String charset, boolean append)
  {

    OutputStreamWriter outputStreamWriter = null;
    InputStreamReader InputStreamReader = new InputStreamReader(inputStream);
    try
    {
      int read = 0;

      if (charset != null && charset.length() > 0)
      {
        char[] buffer = new char[1024];
        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(pathFile, append), charset);
        while ((read = InputStreamReader.read(buffer, 0, buffer.length)) != -1)
        {
          outputStreamWriter.write(buffer, 0, read);
        }
      }
      else
      {
        byte[] buffer = new byte[1024];
        FileOutputStream fos = new FileOutputStream(pathFile, append);
        while ((read = inputStream.read(buffer)) > 0)
        {
          fos.write(buffer, 0, read);
        }
      }
      return new Result<Boolean>(true, true);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
    finally
    {
      if (outputStreamWriter != null)
        try
        {
          outputStreamWriter.close();
        }
        catch (Exception ex2)
        {
        }
      if (InputStreamReader != null)
        try
        {
          InputStreamReader.close();
        }
        catch (Exception ex2)
        {
        }
    }
  }

  public static Result<Boolean> writeFileNoCharset(InputStream inputStream, String pathFile)
  {
    FileOutputStream fileOutputStream = null;

    try
    {

      byte[] buffer;

      int read;

      fileOutputStream = new FileOutputStream(pathFile);

      buffer = new byte[4096];

      while ((read = inputStream.read(buffer, 0, buffer.length)) != -1)
      {
        fileOutputStream.write(buffer, 0, read);
      }

      fileOutputStream.close();

      return new Result<Boolean>(true, true);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
    finally
    {
      if (fileOutputStream != null)
        try
        {
          fileOutputStream.close();
        }
        catch (Exception ex2)
        {
        }
    }
  }

  public static Result<String> readFile(String pathFile)
  {
    BufferedReader in = null;
    try
    {
      StringBuffer buffer = new StringBuffer();
      in = new BufferedReader(new FileReader(pathFile));
      String str;
      while ((str = in.readLine()) != null)
      {
        buffer.append(str);
      }
      in.close();
      return new Result<String>(buffer.toString(), true);
    }
    catch (IOException e)
    {
      return new Result<String>(e);
    }
    finally
    {
      if (in != null)
        try
        {
          in.close();
        }
        catch (Exception ex2)
        {
        }
    }
  }

  public static Result<String> readFile(InputStream sourceFile)
  {
    BufferedReader in = null;
    try
    {
      StringBuffer buffer = new StringBuffer();
      in = new BufferedReader(new InputStreamReader(sourceFile));
      String str;
      while ((str = in.readLine()) != null)
      {
        buffer.append(str);
      }
      in.close();
      return new Result<String>(buffer.toString(), true);
    }
    catch (IOException e)
    {
      return new Result<String>(e);
    }
    finally
    {
      if (in != null)
        try
        {
          in.close();
        }
        catch (Exception ex2)
        {
        }
    }
  }

  public static Result<Boolean> moveFile(String sourcePath, String destinationPath)
  {
    return FileUtil.renameTo(sourcePath, destinationPath);
  }

  public static Result<Boolean> renameTo(String sourcePath, String destinationPath)
  {
    try
    {
      File fSource = new File(sourcePath);
      File fDest = new File(destinationPath);
      boolean result = fSource.renameTo(fDest);
      return new Result<Boolean>(result, result);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
  }

  public static Result<Boolean> copyTo(String sourcePath, String destinationPath)
  {
    try
    {
      File file = new File(sourcePath);
      InputStream is = new FileInputStream(file);
      return FileUtil.writeFile(is, destinationPath);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
  }

  public static Result<Boolean> delete(String sourcePath)
  {
    try
    {
      File f = new File(sourcePath);

      // Make sure the file or directory exists and isn't write protected

      if (!f.exists())
        return new Result<Boolean>(new IllegalArgumentException("Delete: no such file or directory: " + sourcePath));

      if (!f.canWrite())
        return new Result<Boolean>(new IllegalArgumentException("Delete: write protected: " + sourcePath));

      // If it is a directory, make sure it is empty

      if (f.isDirectory())
      {
        String[] files = f.list();
        if (files.length > 0)
          return new Result<Boolean>(new IllegalArgumentException("Delete: directory not empty: " + sourcePath));
      }

      f.delete();

      return new Result<Boolean>(true, true);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
  }

  public static PropertyResourceBundle loadPropertyResourceBundle(String propertiesFile) throws Exception
  {
    PropertyResourceBundle propertyResourceBundle;
    FileInputStream fileInputStream;
    fileInputStream = new java.io.FileInputStream(propertiesFile);
    propertyResourceBundle = new PropertyResourceBundle(fileInputStream);
    fileInputStream.close();
    return propertyResourceBundle;
  }

  public static Properties loadProperties(String propertiesFile) throws Exception
  {
    return loadProperties(propertiesFile, null);
  }

  public static Properties loadProperties(String propertiesFile, Object obj) throws Exception
  {
    Properties properties;
    InputStream inputStream = obj != null ? obj.getClass().getResourceAsStream(propertiesFile) : new java.io.FileInputStream(propertiesFile);
    properties = new Properties();
    properties.load(inputStream);
    inputStream.close();
    return properties;
  }

  public static void main(String[] args)
  {
    try
    {
      FileInputStream inputStream = new FileInputStream("c:/zip.zip");
      FileUtil.writeFile(inputStream, "c:/zip2.zip");
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }

  }
}
