package me.toy.netty.telnet.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseGeneratorTest {

    @DisplayName("빈 문자열이 입력됐을 때 응답을 생성한다.")
    @Test
    void empty_request() {
        // given
        String request = "";

        // when
        ResponseGenerator generator = new ResponseGenerator(request);

        // then
        assertThat(generator).isNotNull();
        assertThat(generator.response()).isNotNull();
        assertThat(generator.response()).isEqualTo("명령을 입력해 주세요.\r\n");
        assertThat(generator.isClose()).isFalse();
    }

    @DisplayName("일반 문자열이 입력됐을 때 응답을 생성한다.")
    @Test
    void hi_request() {
        // given
        String request = "hi";

        // when
        ResponseGenerator generator = new ResponseGenerator(request);

        // then
        assertThat(generator).isNotNull();
        assertThat(generator.response()).isNotNull();
        assertThat(generator.response()).isEqualTo("입력하신 명령이 '" + request + "' 입니까?\r\n");
        assertThat(generator.isClose()).isFalse();
    }

    @DisplayName("bye 문자열이 입력됐을 때 응답을 생성한다.")
    @Test
    void bye_request() {
        // given
        String request = "bye";

        // when
        ResponseGenerator generator = new ResponseGenerator(request);

        // then
        assertThat(generator).isNotNull();
        assertThat(generator.response()).isNotNull();
        assertThat(generator.response()).isEqualTo("좋은 하루 되세요!\r\n", generator.response());
        assertThat(generator.isClose()).isTrue();
    }

}
