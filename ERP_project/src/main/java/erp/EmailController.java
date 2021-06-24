package erp;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/email")
public class EmailController {

	@Autowired
	EmployeeService es;
	 @Autowired
	 private EmailSender emailSender;
	 @Autowired
	 private Email email;
	 
	 @GetMapping("/password")
	 public String password(Model model) {
		 
		 return "popup";
	 }
	 
	 @PostMapping("/password")
	    public String sendMail(Model model,int userId,String name,String birthDate) {
		 System.out.println(userId);
		 System.out.println(name);
		 System.out.println(birthDate);
		 System.out.println(es.select(userId,name,birthDate));
		 if(es.select(userId,name,birthDate)!=null) {
			 Employee emp=es.select(userId,name,birthDate); 
			 emp.setPassword(emp.getUserId()+"");
			 es.update(emp);
			 JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			 System.out.println("emp");
			 try {
				 email.setContent("비밀번호는 사원번호로 초기화되었습니다.");
				 email.setReceiver(emp.getEmail());
				 email.setSubject(emp.getName()+"님 비밀번호 찾기 메일입니다.");
				 emailSender.SendEmail(email);
			 } catch (Exception e) {
				 
				 e.printStackTrace();
			 }
			 model.addAttribute("result", true);
			 return "popup";
		 }else {
			model.addAttribute("result", false);
			return "popup";
		 }
		 
	     }
	  }


