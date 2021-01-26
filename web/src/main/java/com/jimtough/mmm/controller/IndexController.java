package com.jimtough.mmm.controller;

import java.time.LocalDateTime;

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

	@GetMapping(name = "/")
	public String getIndex(Model model) {
		model.addAttribute("helloString", helloFactory.getHello());
		model.addAttribute("worldString", worldFactory.getWorld());
		model.addAttribute("currentTime", LocalDateTime.now());
		return "index";
	}

}
