package com.ftp.folderCreator;
//import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
//import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;
//import org.apache.commons.net.ftp.parser.FTPFileEntryParserFactory;
//import org.apache.commons.net.ftp.parser.MLSxEntryParser;
//import org.apache.commons.net.io.CRLFLineReader;
//import org.apache.commons.net.io.CopyStreamAdapter;
//import org.apache.commons.net.io.CopyStreamEvent;
//import org.apache.commons.net.io.CopyStreamListener;
//import org.apache.commons.net.io.FromNetASCIIInputStream;
//import org.apache.commons.net.io.ToNetASCIIOutputStream;
//import org.apache.commons.net.io.Util;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: badyornyi-a
 * Date: 03.03.14
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class FolderCreator {
    //Вывод ответов сервера / Printout server replies
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
    public static void main(String[] args) {
        //Параметры и детали ФТП-сервера / FTP Server parameters and credentials
        /*String server = "ftp.adstream.com";
        int port = 21;
        String user = "sony";
        String pass = "1qa2ws3ed";
        String dirCurrentDate = args[0];
        String dirFileId = args[1];*/
        String server = args[0];
        String port = args[1];
        String user = args[2];
        String pass = args[3];
        String dirCurrentDate = args[4];
        String dirFileId = args[5];

        //Основной код / Main code
        FTPClient ftpClient = new FTPClient();
        try {

            //Подключение к FTP / Connecting to FTP
            ftpClient.connect(server, Integer.parseInt(port));
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            }

            //Проверка существования директории с текущей датой / Check is Current Date directory exists
            success = ftpClient.changeWorkingDirectory("/" + dirCurrentDate);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Date Directory is not exists");
                //return;
                success = ftpClient.makeDirectory(dirCurrentDate);
                showServerReply(ftpClient);
                if (success) {
                    System.out.println("Successfully created Date directory: " + dirCurrentDate);
                } else {
                    System.out.println("Failed to create Date directory. See server's reply.");
                }

            }

            // Создание директории с текущей датой / Creates a Current Date directory
            /*success = ftpClient.makeDirectory(dirCurrentDate);
            showServerReply(ftpClient);
            if (success) {
                System.out.println("Successfully created Date directory: " + dirCurrentDate);
            } else {
                System.out.println("Failed to create Date directory. See server's reply.");
            }*/


            // Переход в директорию с текущей датой / Change working dir to Current Date directory
            ftpClient.changeWorkingDirectory ("/" + dirCurrentDate);
            if (success) {
                System.out.println("Successfully Changed directory: " + dirCurrentDate);
            } else {
                System.out.println("Failed to change directory. See server's reply.");
            }

            // Создание директории с FileId / Creates a directory with needed DirName (here its - dirFileId)
            success = ftpClient.makeDirectory(dirFileId);
            showServerReply(ftpClient);
            if (success) {
                System.out.println("Successfully created FileID directory: " + dirFileId);
            } else {
                System.out.println("Failed to create FileID directory. See server's reply.");
            }
            // logs out
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }
    }
}
