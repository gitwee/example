package com.fungwen.example.nio;

public class TimeServer {

    public  static  void  main(String[] args){
        int port = 8088;
        if (args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e) {
                // dot nothing and use default port.
            }
        }

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();

    }
}
