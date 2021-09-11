package me.toy.netty.telnet.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ResponseGenerator {

    private static final String BYE = "bye";

    private String request;

    public ResponseGenerator(String request) {
        this.request = request;
    }

    public final String response() {
        StringBuilder sb = new StringBuilder();

        if (this.request.isEmpty()) {
            sb.append("명령을 입력해 주세요.\r\n");
        } else if (BYE.equals(this.request.toLowerCase())) {
            sb.append("좋은 하루 되세요!\r\n");
        } else {
            sb.append("입력하신 명령이 '" + request + "' 입니까?\r\n");
        }

        return sb.toString();
    }

    public static String hello() throws UnknownHostException {
        StringBuilder sb = new StringBuilder();

        sb.append("환영합니다. ")
                .append(InetAddress.getLocalHost().getHostName())
                .append("에 접속하셨습니다!\r\n")
                .append("현재 시간은 ").append(new Date().toString())
                .append(" 입니다.\r\n");

        return sb.toString();
    }

    public boolean isClose() {
        return BYE.equals(this.request);
    }
}
