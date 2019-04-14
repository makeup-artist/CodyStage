package com.cody.codystage.utils.twitter;


public class SnowflakeIdUtil {
    private static SnowflakeIdWorker snowflakeIdWorker;

    static {
        snowflakeIdWorker=new SnowflakeIdWorker(1,1);
    }

    public static Long nextId(){
        return snowflakeIdWorker.nextId();
    }

}
