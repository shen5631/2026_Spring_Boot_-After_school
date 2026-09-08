package com.mirim.board1;


public class SmsNotifier implements Notifier {
    @Override
    public void send (String message) {
        System.out.println("[문자 발송]" + message);
    }
}
