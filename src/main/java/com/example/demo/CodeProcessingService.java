package com.example.demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
public class CodeProcessingService {

    String code;
    String state;
    String session;

    public void processCode(String code) {
        log.info("procces code {}",code);
        this.code = code;

    }

    public void processState(String state) {
        log.info("procces state {}",state);
        this.state = state;
    }

    public void processSession(String session) {
        log.info("procces session {}",session);
        this.session = session;
    }
}
