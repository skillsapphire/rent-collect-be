package com.rentcompany.rentcollect.util;

//Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
 // Find your Account Sid and Auth Token at twilio.com/console
 public static final String ACCOUNT_SID =
         "AC0390776697607097e8392d24d0e99571";
 public static final String AUTH_TOKEN =
         "0056951f9ca0d53ff2044e46fe9cd14a";

 public static void main(String[] args) {
     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

     Message message = Message
             .creator(new PhoneNumber("+918790761586"), // to
                     new PhoneNumber("+18706863888"), // from
                     "Your payment is received")
             .create();

     System.out.println(message.getSid());
 }
}
