package com.jimtough.mmm.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

import com.jimtough.mmm.hello.HelloFactory;
import com.jimtough.mmm.world.WorldFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final HelloFactory helloFactory;
	private final WorldFactory worldFactory;

	// Support sometimes used 'http://myhostname/index' path to home page.
	@GetMapping("index")
	public String redirectToIndexView() {
		return "redirect:/";
	}

	// Support several typical home page path variants
	@GetMapping({"","/"})
	public String getIndexView(Model model) {
		model.addAttribute("helloString", helloFactory.getHello());
		model.addAttribute("worldString", worldFactory.getWorld());
		model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return "index";
	}

}
