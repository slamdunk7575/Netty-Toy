package me.toy.netty.telnet.handler;

import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class TelnetServerHandlerTest {

    private EmbeddedChannel embeddedChannel;

    @BeforeEach
    void setUp() {
        embeddedChannel = new EmbeddedChannel(new TelnetServerHandler());
    }

    @AfterEach
    void tearDown() {
        embeddedChannel.finish();
    }

    @DisplayName("텔넷 서버에 접속하고 메시지를 입력했을때 핸들러가 응답을 처리한다.")
    @Test
    void connect_test() {
        // given
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("환영합니다. ")
                    .append(InetAddress.getLocalHost().getHostName())
                    .append("에 접속하셨습니다!\r\n")
                    .append("현재 시간은 ")
                    .append(new Date().toString()).append(" 입니다.\r\n");
        } catch (UnknownHostException e) {
            fail();
            e.printStackTrace();
        }

        /**
         * 클라이언트가 접속할 때마다 환영 메시지를 전송한다.
         * 인바운드 이벤트 핸들러의 channelActive() 이벤트 메서드는 이벤트 핸들러가 EmbeddedChannel에 등록될 때 호출된다.
         * 그러므로 다른 write 이벤트 메서드 호출없이 readOutbound() 메서드로 아웃바운드 데이터를 조회할 수 있다.
         */
        // when
        String connectedResponse = (String) embeddedChannel.readOutbound();

        // then
        assertThat(connectedResponse).isNotNull();
        assertThat(connectedResponse).isEqualTo(sb.toString());

        // given
        String request = "hello";
        String normalResponse = "입력하신 명령이 '" + request + "' 입니까?\r\n";

        /**
         * 메시지를 테스트하기 위해 writeInbound()로 EmbeddedChannel의 인바운드에 기록한다.
         */
        // when
        embeddedChannel.writeInbound(request);

        /**
         * readOutbound() 메서드를 사용하여 핸들러의 처리결과를 조회한다.
         */
        // then
        String response = (String) embeddedChannel.readOutbound();
        assertThat(response).isEqualTo(normalResponse);
    }

}
