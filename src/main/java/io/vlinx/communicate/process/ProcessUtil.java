package io.vlinx.communicate.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.vlinx.processutils.exception.ProcessException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.lang3.ArrayUtils;

import io.vlinx.communicate.app.AppMessage;
import io.vlinx.communicate.app.AppMsgUtil;
import io.vlinx.communicate.app.IAppMsgListener;
import io.vlinx.communicate.logging.Logger;

public class ProcessUtil {

    public static void runWithListener(String command, IAppMsgListener listener)
            throws IOException, InterruptedException, ProcessException {
        runWithListener(parseCommand(command), listener);
    }

    public static void runWithListener(String[] command, final IAppMsgListener listener)
            throws IOException, InterruptedException, ProcessException {

        Process process = Runtime.getRuntime().exec(command);

        AppMessage appMsg = new AppMessage("process");
        appMsg.setData("process", process);
        AppMsgUtil.sendMessage(appMsg, listener);

        final BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        Thread threadStdout = new Thread(new Runnable() {

            @Override
            public void run() {
                String line = null;

                try {
                    while ((line = stdout.readLine()) != null) {
                        System.out.println(line);
                        AppMsgUtil.sendMessage("stdout", line, listener);
                    }
                } catch (IOException e) {
                    Logger.ERROR(e, listener);
                }
            }
        });

        Thread threadStderr = new Thread(new Runnable() {

            @Override
            public void run() {
                String line = null;

                try {
                    while ((line = stderr.readLine()) != null) {
                        System.out.println(line);
                        AppMsgUtil.sendMessage("stderr", line, listener);
                    }
                } catch (IOException e) {
                    Logger.ERROR(e, listener);
                }
            }
        });

        threadStdout.setDaemon(true);
        threadStdout.start();
        threadStderr.setDaemon(true);
        threadStderr.start();

        process.waitFor();

        int exit = process.exitValue();

        if (exit != 0) {
            throw new ProcessException(ArrayUtils.toString(command) + " [exit: " + exit + "]", exit, command);
        }
    }

    public static String[] parseCommand(String command) {
        command = command.trim();

        String[] commandArr = CommandLine.parse(command).toStrings();

        for (int i = 0; i < commandArr.length; i++) {
            commandArr[i] = commandArr[i].replace("\"", "").replace("'", "");
        }

        return commandArr;

    }

}
