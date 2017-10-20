package com.fungwen.example.nio;

public class TimeClient {

    public  static  void  main(String[] args){
        String host = "127.0.0.1";
        int port = 8088;
        if (args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {
                // dot nothing and use default port.
            }
        }

        TimeClientHandle clientHandle = new TimeClientHandle(host,port);
        new Thread(clientHandle, "TimeClient-001").start();

    }
}
