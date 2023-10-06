package com.gsoft.MyPgManagementApplication.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gsoft.MyPgManagementApplication.dao.UserDao;
import com.gsoft.MyPgManagementApplication.dto.User;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidEmailException;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidEmailOrPasswordException;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidOTPException;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidPhoneOrPasswordException;
import com.gsoft.MyPgManagementApplication.responsestructure.ResponseStructure;

@Service
public class UserService {

	@Autowired
	UserDao dao;

	@Autowired
	JavaMailSender javaMailSender;

	public ResponseEntity<ResponseStructure<User>> saveUser(User user) {

         user.setPassword(user.getEmail().substring(0, 4)+(user.getPhone()+"").substring(6,10));
		user = dao.saveUser(user);
		user.setOtp(generateOTP());
		user.setOtp(Integer.valueOf(user.getId() + "" + user.getOtp()));
		user = dao.updateUser(user);
		ResponseStructure<User> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("User Saved Successfully...");
		structure.setBody(user);
		sendSimpleEmail(user.getEmail(), "Greetiings... Mr." + user.getName(), "Welcome Mr." + user.getName()
				+ " Your Accout Created Successfully..      Your OTP is : " + user.getOtp()
				+ "    OTP Valid Till 2 minutes from Now WARNING : Don't Share Your otp to Any one   Stay safe & Be Happy...     Thanks & Regards   SwiftShoppApp...      Your Password : "+user.getPassword());
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

//	private String autoPasswordGenerator(User user) {
////		for (int i = 0; i < 4; i++) {
////			password += user.getEmail().charAt(i);
////		}
//		return ;
////		for (int i = 6; i <10; i++) {
////			password += phone.charAt(i);
////		}
//		
//	}

	public void sendSimpleEmail(String email, String subject, String body) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("swiftshoppapp@gmail.com");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(body);
		System.out.println("Sending Mail...");
		javaMailSender.send(simpleMailMessage);
		System.out.println("Mail Sent Successfully...");
	}

	public int generateOTP() {
		double num = Math.random() * 10000;
		int otp = (int) Math.round(num);
		String str = "" + otp;
		if (str.length() != 4)
			otp = generateOTP();
		return otp;
	}

	public ResponseEntity<ResponseStructure<String>> verifyOtp(int otp) {
		String s = "" + otp;
		char[] ch = s.toCharArray();
		String str = ch[0] + "" + ch[1];
		int id = Integer.parseInt(str);
		Optional<User> optional = dao.findUserById(id);
		if (optional.isEmpty())
			throw new InvalidOTPException();
		User user = optional.get();
		if (user.getOtp() != otp)
			throw new InvalidOTPException();
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("OTP Verified Successfully...");
		structure.setBody("EMail Verification Successfull...");
		System.out.println("Verification Mail sending...");
		sendSimpleEmail(user.getEmail(), "Verification Successfull", "Welcome Back   Mr." + user.getName()
				+ " Your Accout Verification Successfull...   Stay safe & Be Happy...     Thanks & Regards   SwiftShoppApp...");
		System.out.println("Verification Mail Sent Successfulyy...");
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<String>> forgotPassword(String email) {
		User user = dao.findUserByEmail(email);
		if (user == null)
			throw new InvalidEmailException();
		user.setOtp(Integer.valueOf(user.getId() + "" + generateOTP()));
		user = dao.updateUser(user);
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("OTP Sent To Your Email");
		structure.setBody("OTP Sent Successfully");
		sendSimpleEmail(email, "Forgot Password OTP ",
				"Your OTP is : " + user.getOtp() + "  Verify Your OTP To Reset Your Password...");
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<String>> reSetPassword(int otp, String password) {
		verifyOtp(otp);
		String s = "" + otp;
		char[] ch = s.toCharArray();
		String str = ch[0] + "" + ch[1];
		int id = Integer.parseInt(str);
		Optional<User> optional = dao.findUserById(id);
		if (optional.isEmpty())
			throw new InvalidOTPException();
		User user = optional.get();
		if (user.getOtp() != otp)
			throw new InvalidOTPException();
		user.setPassword(password);
		user = dao.updateUser(user);
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("Your Password Updated Successfully...");
		structure.setBody("Password Re-Set Successfull");
		sendSimpleEmail(user.getEmail(), "Password Reset Successfull",
				"Your Password is Updated / Reset Successfull now WARNING : Your Updated Password is : "
						+ user.getPassword());
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<User>> verifyUser(String username, String password) {
		if (Pattern.compile("[0-9]{10}").matcher(username).matches()) {
			return findUserByPhoneAndPassword(Long.valueOf(username), password);
		}
		return findUserByEmailAndPassword(username, password);
	}

	public ResponseEntity<ResponseStructure<User>> findUserByPhoneAndPassword(long phone, String password) {
		User user = dao.findUserByPhoneAndPassword(phone, password);
		if (user == null)
			throw new InvalidPhoneOrPasswordException();
		ResponseStructure<User> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("User Verification Successfull...");
		structure.setBody(user);
		sendSimpleEmail(user.getEmail(), "Login Noticed",
				"Login Noticed to Your SwiftShopp Account   If it's Not you please contact Helpline 7813040123 otherwise ignore the mail     Have a good Day... ");
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<User>> findUserByEmailAndPassword(String email, String password) {
		User user = dao.findUserByEmailAndPassword(email, password);
		if (user == null)
			throw new InvalidEmailOrPasswordException();
		ResponseStructure<User> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.OK.value());
		structure.setMessage("User Verification Successfull...");
		structure.setBody(user);
		sendSimpleEmail(user.getEmail(), "Login Noticed",
				"Login Noticed to Your SwiftShopp Account   If it's Not you please contact Helpline 7813040123 otherwise ignore the mail     Have a good Day... ");
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

}
