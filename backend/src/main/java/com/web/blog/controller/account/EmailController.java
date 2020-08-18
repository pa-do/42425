package com.web.blog.controller.account;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.blog.model.BasicResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/email")
public class EmailController {
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("/send/{userEmail}")
	@ApiOperation(value = "인증 메일 보내기")
	public Object send(@PathVariable String userEmail) throws MessagingException{
		int ran = new Random().nextInt(900000) + 100000;
		String text = System.getProperty("line.separator") //한줄씩 줄간격을 두기위해 작성
		            + System.getProperty("line.separator")
		            + System.getProperty("line.separator")
		            + System.getProperty("line.separator")
		            + " 인증번호는 " + ran + " 입니다. "
		            + System.getProperty("line.separator")
		            + System.getProperty("line.separator")
		            + "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		messageHelper.setFrom("jiryutjoinfo@gmail.com");
		messageHelper.setSubject("[지렸조]인증 코드 발급 안내 입니다.");
		messageHelper.setTo(new InternetAddress(userEmail));
		messageHelper.setText(text);
		sender.send(message);
		
		final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = String.valueOf(ran);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
  @PostMapping("/sendme")
  @ApiOperation(value = "나에게 메일 보내기", notes = "Contact me 컴포넌트에서 상대방이 나에게 메일을 보낼 수 있다.")
  public Object sendme(@ApiParam(value = "상대방 이름 (your name)") @RequestParam String name, 
                  @ApiParam(value = "상대방 이메일 (your email)") @RequestParam String email, 
                  @ApiParam(value = "상대방 제목 (subject)") @RequestParam String subject, 
                  @ApiParam(value = "상대방 메세지 (message)") @RequestParam String message, 
                  @ApiParam(value = "내 이메일") @RequestParam String myEmail
                  ) throws MessagingException{
     
        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        
        messageHelper.setFrom(new InternetAddress(email));
        messageHelper.setSubject(subject);
        messageHelper.setTo(new InternetAddress(myEmail));
        messageHelper.setText(name + "(" + email + ") \n" + message);
        sender.send(msg);
        return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
