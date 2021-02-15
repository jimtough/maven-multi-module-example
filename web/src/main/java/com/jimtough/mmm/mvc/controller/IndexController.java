package com.jimtough.mmm.mvc.controller;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.SiteVisit;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import com.jimtough.mmm.data.repository.SiteVisitRepository;
import com.jimtough.mmm.data.repository.SiteVisitorRepository;
import com.jimtough.mmm.hello.HelloFactory;
import com.jimtough.mmm.mvc.command.SubmitVisitorNameCommand;
import com.jimtough.mmm.mvc.session.SiteVisitorSessionStuff;
import com.jimtough.mmm.world.WorldFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@SessionAttributes({"siteVisitorSessionStuff"})
public class IndexController {

	private final HelloFactory helloFactory;
	private final WorldFactory worldFactory;
	private final SiteVisitorRepository siteVisitorRepository;
	private final SiteVisitRepository siteVisitRepository;

	// Support sometimes used 'http://myhostname/index' path to home page.
	@GetMapping("index")
	public String redirectToIndexView() {
		return "redirect:/";
	}

	// Support several typical home page path variants
	@GetMapping({"","/"})
	public String getIndexView(Model model, SiteVisitorSessionStuff siteVisitorSessionStuff) {
		Objects.requireNonNull(siteVisitorSessionStuff);
		model.addAttribute("helloString", helloFactory.getHello());
		if (siteVisitorSessionStuff.getNickname().isPresent()) {
			model.addAttribute("worldString", siteVisitorSessionStuff.getNickname().get());
		} else {
			model.addAttribute("worldString", worldFactory.getWorld());
			model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			model.addAttribute("submitVisitorNameCommand", new SubmitVisitorNameCommand());
		}
		return "index";
	}

	void handleSubmitVisitorName(SiteVisitorSessionStuff siteVisitorSessionStuff, String validNickname) {
		// Save the raw (but validated) nickname in the session object
		siteVisitorSessionStuff.setNickname(validNickname);
		// Retrieve or create a new SiteVisitor entity
		Optional<SiteVisitor> siteVisitorOptional =
				siteVisitorRepository.findOneByUppercaseNickname(validNickname.toUpperCase());
		final SiteVisitor siteVisitorEntity;
		if (siteVisitorOptional.isPresent()) {
			log.debug("Found matching entity in database for nickname '{}'", validNickname);
			siteVisitorEntity = siteVisitorOptional.get();
		} else {
			log.debug("Creating new entity for nickname '{}'", validNickname);
			siteVisitorEntity = siteVisitorRepository.save(SiteVisitor.builder()
					.nickname(validNickname)
                    .uppercaseNickname(validNickname.toUpperCase())
                    .build());
		}
		// Create a new SiteVisit entity for this visit
		siteVisitRepository.save(SiteVisit.builder().siteVisitor(siteVisitorEntity).build());
	}

	@PostMapping("/submit-visitor-name")
	public String submitVisitorName(
			Model model,
			SiteVisitorSessionStuff siteVisitorSessionStuff,
			@Valid SubmitVisitorNameCommand command,
			BindingResult bindingResult) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(siteVisitorSessionStuff);
		Objects.requireNonNull(command);
		Objects.requireNonNull(bindingResult);
		if (bindingResult.hasErrors()) {
			log.info("submitVisitorName() | {} has errors", BindingResult.class.getSimpleName());
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				log.info("submitVisitorName() | --> [{}] | [{}]", fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("helloString", helloFactory.getHello());
			model.addAttribute("worldString", worldFactory.getWorld());
			return "index";
		}
		handleSubmitVisitorName(siteVisitorSessionStuff, command.getNickname());
		return "redirect:/";
	}

}
