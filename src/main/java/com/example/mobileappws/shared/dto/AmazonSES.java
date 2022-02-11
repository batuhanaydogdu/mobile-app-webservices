package com.example.mobileappws.shared.dto;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AmazonSES {
	//aws maili buraya yazılacak
	final String FROM = "temreakyildiz@gmail.com";
	final String SUBJECT = "One last step to complete your registration with PhotoApp";
	
	final String HTMLBODY = "<h1>Please verify your email address</h1>"
    +"<p>Thank you for registering with our mobile app. To complete registration process and be able to login,"
	+" click on the following link:"
    //aws den sonra bu link düzenlenecek
	+ "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>"
	+ "Final step to complete your registration" + "</a><br/><br/>"
	+ "Thank you! And we are waiting for you inside!";

	final String TEXTBODY = "Please verify your email address. "
			 + "Thank you for registering wi th our mobile app. To complete registration process and be able to log in,"
			 +"open then the following URL in your browser window: "
			 +"http://localhost:8080/verification-service/email-verification.html?token=$tokenValue"
			 +" Thank you! And we are waiting for you inside!";
	
	public void verifyEmail(UserDto userDto) {
		//REGİON aws nereden kullanılıyorsa orası yapılacak
	    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1)
	             .build();
	    String htmlBodyWithToken = HTMLBODY.replace("StokenValue", userDto.getEmailVerificationToken());
	    String textBodyWithToken = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
	    SendEmailRequest request = new SendEmailRequest()
	           .withDestination(new Destination().withToAddresses(userDto.getEmail()))
	             .withMessage(new Message()
	                    .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
	                              .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
	                    .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
	            .withSource(FROM);
	    client.sendEmail(request);
	    System.out.println("Email sent!");
 }
}
