package com.pdmi.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Java执行shell脚本工具类
 */
public class ShellExcutor {
    /**
     * Java执行shell脚本入口
     * @param shellName 脚本文件名
     * @throws Exception
     */
    public void service(String shellName) throws Exception{
        try {
            callScript(shellName);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 脚本文件具体执行及脚本执行过程探测
     * @param script 脚本文件绝对路径
     * @throws Exception
     */
    private void callScript(String script) throws Exception{
        try {
            String cmd = "sh " + script;

            CommandWaitForThread commandThread = new CommandWaitForThread(cmd);
            commandThread.start();

            while (!commandThread.isFinish()) {
                Thread.sleep(10000);
            }

            if(commandThread.getExitValue() != 0){
                throw new Exception("shell " + script + "执行失败,exitValue = " + commandThread.getExitValue());
            }
        }
        catch (Exception e){
            throw new Exception("执行脚本发生异常,脚本路径" + script, e);
        }
    }

    /**
     * 脚本函数执行线程
     */
    public class CommandWaitForThread extends Thread {

        private String cmd;
        private boolean finish = false;
        private int exitValue = -1;

        public CommandWaitForThread(String cmd) {
            this.cmd = cmd;
        }

        public void run(){
            try {
                Process process = Runtime.getRuntime().exec(cmd);

                BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = "";
                while ((line = infoInput.readLine()) != null) {
                	System.out.println(line);
                }
                while ((line = errorInput.readLine()) != null) {
                	System.out.println(line);
                }
                infoInput.close();
                errorInput.close();

                this.exitValue = process.waitFor();
            } catch (Throwable e) {
                exitValue = 110;
            } finally {
                finish = true;
            }
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public int getExitValue() {
            return exitValue;
        }
    }
}