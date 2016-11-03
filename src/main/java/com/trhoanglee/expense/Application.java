package com.trhoanglee.expense;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * @author hoangtle
 */
@EntityScan(basePackageClasses = {Application.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class Application {
	public static void main(String... args) throws IOException {
		ApplicationContext appContext = SpringApplication.run(Application.class, args);
		MemberService memberService = appContext.getBean(MemberService.class);
		String membersFilePath = (args.length > 0)? args[0] : "etc/members.txt";
		memberService.loadMembersFromFile(membersFilePath);
	}
}
